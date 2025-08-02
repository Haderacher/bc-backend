package com.haderacher.bcbackend.controller;

import com.haderacher.bcbackend.service.EmbeddingService;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    private final EmbeddingModel embeddingModel;

    public EmbeddingController(EmbeddingService embeddingService, EmbeddingModel embeddingModel) {
        this.embeddingService = embeddingService;
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("embed/embedAll")
    public ResponseEntity<String> embedAll() {
        embeddingService.embedAllJobs();
        return ResponseEntity.ok("success");
    }


    @GetMapping("/ai/embedding")
    public Map embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        EmbeddingResponse embeddingResponse = this.embeddingModel.embedForResponse(List.of(message));
        return Map.of("embedding", embeddingResponse);
    }
}
