package com.metaphysical.metaphysical.controller;

import com.metaphysical.metaphysical.model.User;
import com.metaphysical.metaphysical.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/api/me")
    public String getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "User not authenticated";
        }

        // fetch the full User entity to get displayName
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);

        if (user == null) {
            return "User not found";
        }

        return "Hello, " + user.getDisplayName();
    }
}