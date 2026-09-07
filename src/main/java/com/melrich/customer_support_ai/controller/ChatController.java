package com.melrich.customer_support_ai.controller;


import com.melrich.customer_support_ai.ai.GeminiService;
import com.melrich.customer_support_ai.dto.ChatMessageResponse;
import com.melrich.customer_support_ai.dto.ChatRequest;
import com.melrich.customer_support_ai.entity.Customer;
import com.melrich.customer_support_ai.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final GeminiService geminiService;
    private final CustomerRepository customerRepository;

    @PostMapping
    public ChatRequest chat(
            @RequestBody ChatRequest request,
            Authentication authentication
    ){
        String username = authentication.getName();

        Customer customer = customerRepository
                .findByUserUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer found for user: " + username));


        String currentTime = LocalTime.now()
                        .format(DateTimeFormatter.ofPattern("h:mm a"));

        System.out.println("Logged in user: " + username);
        System.out.println("Message: " + request.content());

        String aiMessage =
                geminiService.sendMessage(request.content(), customer);

        return new ChatRequest(
                request.id() + 1,
                "assistant",
                aiMessage,
                currentTime
        );
    }


    @GetMapping("/history")
    public List<ChatMessageResponse> getChatHistory(Authentication authentication){

        String username = authentication.getName();

        Customer customer = customerRepository
                .findByUserUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer found for user: " + username));

        return geminiService.getConversationHistory(customer);
    }



}

