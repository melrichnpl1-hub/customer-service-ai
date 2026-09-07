package com.melrich.customer_support_ai.controller;

import com.melrich.customer_support_ai.dto.*;
import com.melrich.customer_support_ai.entity.Customer;
import com.melrich.customer_support_ai.entity.Delivery;
import com.melrich.customer_support_ai.entity.Order;
import com.melrich.customer_support_ai.entity.OrderItem;
import com.melrich.customer_support_ai.repository.*;
import com.melrich.customer_support_ai.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersRepository ordersRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrdersService ordersService;

//    @GetMapping("/my-orders")
//    public List<OrderResponse> getMyOrdders(Authentication authentication){
//
//        List<Order> orders = ordersRepository.findByCustomerId(
//                customerRepository.findByUserUsername(authentication.getName())
//                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer found for user: ")).getId()
//        );
//
//        for(Order order : orders){
//            System.out.println(order.getTotalAmount());
//        }
//
//        return orders.stream()
//                .map( order -> new OrderResponse(
//                        order.getId(),
//                        order.getStatus(),
//                        order.getTotalAmount(),
//                        order.getOrderDate()
//                ))
//                .toList();
//    }

    @GetMapping("/my-orders")
    public List<OrderListResponse> getMyOrderLists(Authentication authentication){

        System.out.println(authentication.getName());

        Customer customer = customerRepository
                .findByUserUsername(authentication.getName())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Customer not found"
                        )
                );

        return ordersService.getOrders(customer);
    }

    @GetMapping("/{orderId}")
    public OrderDetailsResponse getOrderDetails(
            @PathVariable Long orderId,
            Authentication authentication
    ){

        OrderDetailsResponse orderDetailsResponse;

        String username = authentication.getName();

        Customer customer = customerRepository
                .findByUserUsername(username)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Customer not found"
                        )
                );


        return ordersService.getOrderDetails(customer, orderId);
    }


    @GetMapping("/partial")
    public List<OrderResponse> getOrderResponse(Authentication authentication){
        System.out.println(authentication.getName());

        Customer customer = customerRepository
                .findByUserUsername(authentication.getName())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Customer not found"
                        )
                );

        return ordersService.getOrderResponse(customer);
    }
}
