package com.melrich.customer_support_ai.dto;

public record ChatRequest(
        Long id,
        String role,
        String content,
        String time
){
}
