package com.melrich.customer_support_ai.dto;

import java.math.BigDecimal;

public record OrderItemDetails(
        Long productId,
        String productName,
        BigDecimal price,
        Integer quantity,
        BigDecimal subtotal
) {
}
