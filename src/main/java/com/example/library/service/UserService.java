package com.example.library.service;

import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password, String role) {
        // Debug: Print the received parameters
        System.out.println("Registering user - Username: " + username + ", Role: " + role);

        // Hash the password
        String hashedPassword = passwordEncoder.encode(password);
        System.out.println("Hashed password: " + hashedPassword);

        // Create a new user
        User user = new User(username, hashedPassword, role);
        System.out.println("User object created: " + user);

        // Save the user to MongoDB
        userRepository.save(user);
        System.out.println("User saved to MongoDB");
    }
}
