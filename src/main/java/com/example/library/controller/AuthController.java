package com.example.library.controller;

import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // To hash passwords

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // ✅ Add this method to handle form submissions
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        userRepository.save(user);
        return "redirect:/login"; // Redirect to login after successful registration
    }

    @GetMapping("/redirectBasedOnRole")
    public String redirectBasedOnRole(Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            switch (role) {
                case "ROLE_ADMIN":
                    return "redirect:/admin/dashboard";
                case "ROLE_LIBRARIAN":
                    return "redirect:/librarian/dashboard";
                case "ROLE_USER":
                    return "redirect:/user/dashboard";
            }
        }
        return "redirect:/login"; // Default redirect if no role is found
    }
}
