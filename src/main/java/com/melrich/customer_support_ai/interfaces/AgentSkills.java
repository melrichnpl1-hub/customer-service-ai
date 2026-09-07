package com.melrich.customer_support_ai.interfaces;

import com.melrich.customer_support_ai.dto.*;
import com.melrich.customer_support_ai.entity.Customer;

import java.util.List;

public interface AgentSkills {
    List<OrderListResponse> getOrders(Customer customer);
    OrderDetailsResponse getOrderDetails(Customer customer, Long orderId);
    DeliveryDetails getDelivery(Customer customer, Long orderId);
    CustomerDetailsResponse getCustomer(Customer customer);
    DeliveryDetails getDeliveryByTrackingNumber(Customer customer, String trackingNumber);
    CancelOrderResponse cancelProcessingOrder(Customer customer, Long orderId);
    CustomerDetailsResponse updateCustomer(Customer customer, UpdateCustomerRequest req);
}
