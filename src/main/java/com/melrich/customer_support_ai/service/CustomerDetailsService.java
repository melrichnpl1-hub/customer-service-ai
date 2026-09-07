package com.melrich.customer_support_ai.service;

import com.melrich.customer_support_ai.dto.CustomerDetailsResponse;
import com.melrich.customer_support_ai.dto.UpdateCustomerRequest;
import com.melrich.customer_support_ai.entity.Customer;
import com.melrich.customer_support_ai.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomerDetailsService {

    private final CustomerRepository customerRepository;


    public CustomerDetailsResponse getCustomerDetails(Customer customer){
        return new CustomerDetailsResponse(
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }

    public CustomerDetailsResponse updateCustomer(Customer customer, UpdateCustomerRequest req){

        // NAME
        if (req.name() != null) {

            if (req.name().isBlank()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Name cannot be blank"
                );
            }

            customer.setName(req.name());
        }


        // EMAIL
        if (req.email() != null) {

            if (req.email().isBlank()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Email cannot be blank"
                );
            }

            customer.setEmail(req.email());
        }


        // PHONE
        if (req.phone() != null) {

            if (req.phone().isBlank()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Phone number cannot be blank"
                );
            }

            customer.setPhone(req.phone());
        }


        customerRepository.save(customer);

        return getCustomerDetails(customer);
    }

}
