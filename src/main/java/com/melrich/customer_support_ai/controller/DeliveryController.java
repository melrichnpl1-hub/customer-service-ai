package com.melrich.customer_support_ai.controller;

import com.melrich.customer_support_ai.dto.DeliveryListResponse;
import com.melrich.customer_support_ai.entity.Delivery;
import com.melrich.customer_support_ai.entity.Order;
import com.melrich.customer_support_ai.repository.CustomerRepository;
import com.melrich.customer_support_ai.repository.DeliveryRepository;
import com.melrich.customer_support_ai.repository.OrderItemRepository;
import com.melrich.customer_support_ai.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final OrdersRepository ordersRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryRepository deliveryRepository;


    @GetMapping("my-deliveries")
    public List<DeliveryListResponse> getMyDeliveries(Authentication authentication){

        List<Order> orders = ordersRepository.findByCustomerId(
                customerRepository.findByUserUsername(authentication.getName())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer found for user: ")).getId());

        return orders.stream()
                        .map(order -> {
                            Delivery delivery =
                                    deliveryRepository.findByOrderId(order.getId());

                            return new DeliveryListResponse(
                                    delivery.getOrder().getId(),
                                    delivery.getCarrier(),
                                    delivery.getTrackingNumber(),
                                    delivery.getStatus(),
                                    delivery.getCurrentLocation(),
                                    delivery.getEstimatedDelivery()
                            );
                        })
                        .toList();
    }



}
