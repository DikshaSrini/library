package com.example.library.service;

import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password, String role) {
        // Hash the password
        String hashedPassword = passwordEncoder.encode(password);

        // Create a new user
        User newUser = new User(username, hashedPassword, role);

        // Save to MongoDB
        userRepository.save(newUser);
    }
}