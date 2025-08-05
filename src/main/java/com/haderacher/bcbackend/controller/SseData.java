package com.haderacher.bcbackend.controller;

public class SseData {
    private String content; // 本次发送的内容 token
    private boolean isLast;   // 是否是最后一条消息

    // 构造函数、Getters 和 Setters
    public SseData(String content, boolean isLast) {
        this.content = content;
        this.isLast = isLast;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public boolean isLast() { return isLast; }
    public void setLast(boolean last) { isLast = last; }
}