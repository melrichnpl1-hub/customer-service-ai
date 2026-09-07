package com.melrich.customer_support_ai.dto;

public record ChatMessageResponse(
        String role,
        String content,
        String time
) {
}
