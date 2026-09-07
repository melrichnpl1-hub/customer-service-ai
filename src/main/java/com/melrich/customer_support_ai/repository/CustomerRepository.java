package com.melrich.customer_support_ai.repository;

import com.melrich.customer_support_ai.entity.Customer;
import com.melrich.customer_support_ai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUserUsername(String username);

    String user(User user);
}
