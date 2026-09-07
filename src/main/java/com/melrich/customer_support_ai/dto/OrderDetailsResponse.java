package com.melrich.customer_support_ai.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailsResponse(
        Long orderId,
        String orderStatus,
        LocalDateTime orderDate,
        BigDecimal totalAmount,

        DeliveryDetails delivery,

        List<OrderItemDetails> items


) {
}
