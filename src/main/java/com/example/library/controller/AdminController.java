package com.example.library.controller;

import com.example.library.model.RegistrationForm;
import com.example.library.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRegistrationService registrationService;

    // Show registration form for Librarian or User
    @GetMapping("/register")
    public String showRegistrationForm(@RequestParam String role, Model model) {
        model.addAttribute("role", role);
        model.addAttribute("registrationForm", new RegistrationForm());
        return "admin/register-user";
    }

    // Handle user registration
    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegistrationForm form) {
        // Extract fields from the form and pass them to the service
        registrationService.registerUser(form.getUsername(), form.getPassword(), form.getRole());
        return "redirect:/admin/manage-users";
    }
}