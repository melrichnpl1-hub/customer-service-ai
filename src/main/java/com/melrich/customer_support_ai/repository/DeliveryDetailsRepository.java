package com.melrich.customer_support_ai.repository;

import com.melrich.customer_support_ai.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryDetailsRepository extends JpaRepository<Delivery, Long> {
    Delivery findByOrderId(Long orderId);
}
