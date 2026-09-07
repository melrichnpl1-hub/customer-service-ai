package com.melrich.customer_support_ai.repository;

import com.melrich.customer_support_ai.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
}
