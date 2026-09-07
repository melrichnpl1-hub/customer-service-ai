package com.melrich.customer_support_ai.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderListResponse(
        Long orderId,
        LocalDateTime orderDate,
        String currentLocation,
        BigDecimal totalAmount,
        String status
) {

}