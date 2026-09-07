package com.melrich.customer_support_ai.service;

import com.melrich.customer_support_ai.entity.User;
import com.melrich.customer_support_ai.repository.UserRepository;
import com.melrich.customer_support_ai.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo
                .findByUsername(username)
                .orElseThrow(() -> (new UsernameNotFoundException("User not found")));

        return new UserPrincipal(user);


    }



}
