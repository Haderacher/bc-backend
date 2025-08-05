package com.haderacher.bcbackend.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Map;

@RestController
public class ChatController {

    private final DeepSeekChatModel chatModel;

    private final VectorStore vectorStore;

    @Autowired
    public ChatController(DeepSeekChatModel chatModel, VectorStore vectorStore) {
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;
    }

    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatModel.call(message));
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        var prompt = new Prompt(new UserMessage(message));
        return chatModel.stream(prompt);
    }

    @GetMapping("/ai/generateStreamRag")
    public SseEmitter generateResponseRAG(@RequestParam(value = "message") String message) {
        var qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder().similarityThreshold(0.8d).topK(6).build())
                .build();
        Flux<String> content = ChatClient.builder(chatModel)
                .build().prompt("你是一个工作岗位推荐助手，请根据用户需求推荐岗位，并给出响应岗位链接")
                .advisors(qaAdvisor)
                .user(message)
                .stream()
                .content();

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        content.subscribe(
                // onNext: 接收到新数据时的回调
                token -> {
                    try {
                        // 必须使用 SseEmitter.event() 来构建和发送事件
                        emitter.send(SseEmitter.event().data(token));
                    } catch (IOException e) {
                        // 捕获IO异常，通常是客户端断开连接
                        System.err.println("Error sending SSE event: " + e.getMessage());
                        emitter.completeWithError(e);
                    }
                },
                // onError: 发生错误时的回调
                emitter::completeWithError,
                // onComplete: 数据流正常结束时的回调
                emitter::complete
        );
        // 4. 处理连接超时和断开的回调
        emitter.onTimeout(emitter::complete);
        emitter.onCompletion(() -> System.out.println("SseEmitter is completed"));
        emitter.onError(e -> System.err.println("SseEmitter error: " + e.getMessage()));

        // 立即返回 emitter，请求处理线程被释放
        return emitter;
    }


}