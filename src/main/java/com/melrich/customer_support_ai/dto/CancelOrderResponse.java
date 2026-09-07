package com.melrich.customer_support_ai.dto;

public record CancelOrderResponse(
  Long orderId,
  String status,
  String message
)
{ }
