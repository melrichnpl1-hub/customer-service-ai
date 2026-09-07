package com.melrich.customer_support_ai.dto;

public record UpdateCustomerRequest(
        String name,
        String email,
        String phone
) {
}
