package com.melrich.customer_support_ai.controller;


import com.melrich.customer_support_ai.dto.UserProfile;
import com.melrich.customer_support_ai.entity.Customer;
import com.melrich.customer_support_ai.dto.LoginRequest;
import com.melrich.customer_support_ai.dto.RegisterRequest;
import com.melrich.customer_support_ai.entity.User;
import com.melrich.customer_support_ai.repository.CustomerRepository;
import com.melrich.customer_support_ai.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.username(),
                                request.password()
                        )
                );

        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextRepository repository =
                new HttpSessionSecurityContextRepository();

        repository.saveContext(
                context,
                httpRequest,
                httpResponse
        );

        return "Login successful";
    }



    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request){

        // 1. Create User
        User user = new User();

        user.setUsername(request.username());
        user.setPassword(
                passwordEncoder.encode(request.password())
        );
        user.setRole("USER");

        // 2. Save User first
        User savedUser = userRepository.save(user);


        // 3. Create Customer
        Customer customer = new Customer();

        customer.setUser(savedUser);
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());

        // 4. Save Customer
        customerRepository.save(customer);


        return "User successfully registered";
    }

    @GetMapping("/me")
    public Customer getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        return customerRepository
                .findByUserUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer found for user: " + username));
    }

    @GetMapping("user-profile")
    public UserProfile fetchUserProfile(Authentication authentication){

        String username = authentication.getName();

        Customer customer = customerRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer found for user: " + username));

        return new UserProfile(
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }


//    @PostMapping("/register")
//    public String register(@RequestBody RegisterRequest request){
//
//        User user = new User();
//
//        user.setUsername(request.username());
//
//        user.setPassword(passwordEncoder.encode(request.password()));
//
//        user.setRole("USER");
//
//        userRepository.save(user);
//
//        return "User registered successfully";
//    }


}
