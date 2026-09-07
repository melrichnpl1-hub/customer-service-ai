package com.melrich.customer_support_ai.dto;

import java.time.LocalDateTime;

public record DeliveryDetails(
        String status,
        String carrier,
        String trackingNumber,
        String currentLocation,
        LocalDateTime estimatedDelivery
){
}
