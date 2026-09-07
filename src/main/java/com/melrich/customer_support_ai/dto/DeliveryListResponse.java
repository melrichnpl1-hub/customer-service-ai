package com.melrich.customer_support_ai.dto;

import java.time.LocalDateTime;

public record DeliveryListResponse(
        Long orderId,
        String carrier,
        String trackingNumber,
        String status,
        String currentLocation,
        LocalDateTime estimatedDeliver
) {
}
