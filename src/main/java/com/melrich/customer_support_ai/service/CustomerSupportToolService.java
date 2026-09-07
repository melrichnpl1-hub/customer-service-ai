package com.melrich.customer_support_ai.service;

import com.melrich.customer_support_ai.dto.*;
import com.melrich.customer_support_ai.entity.Customer;
import com.melrich.customer_support_ai.entity.Delivery;
import com.melrich.customer_support_ai.entity.Order;
import com.melrich.customer_support_ai.entity.OrderItem;
import com.melrich.customer_support_ai.interfaces.AgentSkills;
import com.melrich.customer_support_ai.repository.CustomerRepository;
import com.melrich.customer_support_ai.repository.DeliveryRepository;
import com.melrich.customer_support_ai.repository.OrderItemRepository;
import com.melrich.customer_support_ai.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerSupportToolService implements AgentSkills {

    private final OrdersService ordersService;
    private final DeliveryService deliveryService;
    private final CustomerDetailsService customerDetailsService;

    @Override
    public List<OrderListResponse> getOrders(Customer customer) {
        return ordersService.getOrders(customer);
    }

    @Override
    public OrderDetailsResponse getOrderDetails(Customer customer, Long orderId) {
        return ordersService.getOrderDetails(customer, orderId);
    }

    @Override
    public DeliveryDetails getDelivery(Customer customer, Long orderId) {
       return deliveryService.getDeliveryDetails(customer, orderId);
    }

    @Override
    public CustomerDetailsResponse getCustomer(Customer customer) {
        return customerDetailsService.getCustomerDetails(customer);
    }

    @Override
    public DeliveryDetails getDeliveryByTrackingNumber(Customer customer, String trackingNumber) {
       return deliveryService.getDeliveryDetailsByTrackingNumber(customer, trackingNumber);
    }

    @Override
    public CancelOrderResponse cancelProcessingOrder(Customer customer, Long orderId) {
        return ordersService.cancelProcessingOrder(customer, orderId);
    }


    @Override
    public CustomerDetailsResponse updateCustomer(Customer customer , UpdateCustomerRequest req) {
        return customerDetailsService.updateCustomer(customer, req);
    }
}

