package com.melrich.customer_support_ai.repository;

import com.melrich.customer_support_ai.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Delivery findByOrderId(Long id);
    Delivery findDeliveryByTrackingNumber(String trackingNumber);
}
