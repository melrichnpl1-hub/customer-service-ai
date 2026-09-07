package com.melrich.customer_support_ai.dto;

public record CustomerDetailsResponse(
        String name,
        String email,
        String phone
) {
}
