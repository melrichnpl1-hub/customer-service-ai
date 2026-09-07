package com.melrich.customer_support_ai.service;

import com.melrich.customer_support_ai.dto.*;
import com.melrich.customer_support_ai.entity.Customer;
import com.melrich.customer_support_ai.entity.Delivery;
import com.melrich.customer_support_ai.entity.Order;
import com.melrich.customer_support_ai.entity.OrderItem;
import com.melrich.customer_support_ai.repository.CustomerRepository;
import com.melrich.customer_support_ai.repository.DeliveryRepository;
import com.melrich.customer_support_ai.repository.OrderItemRepository;
import com.melrich.customer_support_ai.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrdersRepository ordersRepository;
    private final DeliveryRepository deliveryRepository;


    public List<OrderListResponse> getOrders(Customer customer) {

        return ordersRepository
                .findByCustomerId(customer.getId())
                .stream()
                .map(order -> {

                    Delivery delivery =
                            deliveryRepository.findByOrderId(order.getId());

                    return new OrderListResponse(
                            order.getId(),
                            order.getOrderDate(),
                            delivery != null ? delivery.getCurrentLocation() : null,
                            order.getTotalAmount(),
                            order.getStatus()
                    );


                })
                .toList();
    }

    public List<OrderResponse> getOrderResponse(Customer customer){
        return ordersRepository
                .findByCustomerId(customer.getId())
                .stream()
                .map( order -> {
                    return new OrderResponse(
                          order.getId(),
                          order.getStatus(),
                          order.getTotalAmount(),
                            order.getOrderDate()
                    );
                })
                .toList();
    }

    public OrderDetailsResponse getOrderDetails(Customer customer, Long orderId) {

        Order order = ordersRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Order not found"
                        )
                );

        if (!order.getCustomer().getId().equals(customer.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access this order"
            );
        }

        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

        List<OrderItemDetails> orderItems = items
                .stream()
                .map(item -> new OrderItemDetails(
                        item.getProduct().getId(),       // productId
                        item.getProduct().getName(),     // productName
                        item.getProduct().getPrice(),    // price
                        item.getQuantity(),              // quantity
                        item.getProduct().getPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity())) // subtotal
                ))
                .toList();


        Delivery delivery = deliveryRepository.findByOrderId(order.getId());



        return new OrderDetailsResponse(
                order.getId(),
                order.getStatus(),
                order.getOrderDate(),
                order.getTotalAmount(),

                new DeliveryDetails(
                        delivery.getStatus(),
                        delivery.getCarrier(),
                        delivery.getTrackingNumber(),
                        delivery.getCurrentLocation(),
                        delivery.getEstimatedDelivery()
                ),

                orderItems
        );

    }

    public CancelOrderResponse cancelProcessingOrder(Customer customer, Long orderId){

        Order order = ordersRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Order not found"
                        )
                );

        if (!order.getCustomer().getId().equals(customer.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access this order"
            );
        }

        if (!order.getStatus().equals("PROCESSING")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only processing orders can be cancelled"
            );
        }

        order.setStatus("CANCELLED");

        ordersRepository.save(order);

        return new CancelOrderResponse(
                order.getId(),
                order.getStatus(),
                "Order cancelled successfully"
        );
    }


}
