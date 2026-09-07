package com.melrich.customer_support_ai.service;


import com.melrich.customer_support_ai.dto.DeliveryDetails;
import com.melrich.customer_support_ai.entity.Customer;
import com.melrich.customer_support_ai.entity.Delivery;
import com.melrich.customer_support_ai.entity.Order;
import com.melrich.customer_support_ai.repository.CustomerRepository;
import com.melrich.customer_support_ai.repository.DeliveryRepository;
import com.melrich.customer_support_ai.repository.OrderItemRepository;
import com.melrich.customer_support_ai.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final OrdersRepository ordersRepository;
    private final DeliveryRepository deliveryRepository;



    public DeliveryDetails getDeliveryDetails(Customer customer, Long orderId){

        Order order = ordersRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Order not found"
                        )
                );

        validateBasedOnCustomerId(order.getCustomer().getId(), customer.getId());


        Delivery delivery =
                deliveryRepository.findByOrderId(orderId);

        if (delivery == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Delivery not found"
            );
        }

        return new DeliveryDetails(
                delivery.getStatus(),
                delivery.getCarrier(),
                delivery.getTrackingNumber(),
                delivery.getCurrentLocation(),
                delivery.getEstimatedDelivery()
        );
    }

    public DeliveryDetails getDeliveryDetailsByTrackingNumber(Customer customer, String trackingNumber){

        Delivery delivery =
                deliveryRepository.findDeliveryByTrackingNumber(trackingNumber);


        validateBasedOnCustomerId(delivery.getOrder().getCustomer().getId(), customer.getId());


        if (delivery == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Delivery not found"
            );
        }

        return new DeliveryDetails(
                delivery.getStatus(),
                delivery.getCarrier(),
                delivery.getTrackingNumber(),
                delivery.getCurrentLocation(),
                delivery.getEstimatedDelivery()
        );
    }


    private void validateBasedOnCustomerId(Long systemCustomerId,Long customerId){
        if (!systemCustomerId.equals(customerId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access this order"
            );
        }
    }

}
