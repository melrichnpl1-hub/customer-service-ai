package com.melrich.customer_support_ai.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String status,
        BigDecimal totalAmount,
        LocalDateTime orderDate
) {
}
