package com.melrich.customer_support_ai.dto;

public record RegisterRequest(
        String username,
        String password,
        String name,
        String email,
        String phone
) {
}
