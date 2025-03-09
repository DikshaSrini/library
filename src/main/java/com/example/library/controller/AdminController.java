package com.example.library.controller;

import com.example.library.model.RegistrationForm;
import com.example.library.service.UserRegistrationService;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final BookService bookService;
    private final UserRegistrationService registrationService; // Inject this service

    // Constructor to inject both services
    public AdminController(BookService bookService, UserRegistrationService registrationService) {
        this.bookService = bookService;
        this.registrationService = registrationService;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "admin/dashboard";
    }

    @PostMapping("/add-book")
    public String addBook(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/remove-book")
    public String removeBook(@RequestParam String id) {
        bookService.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    // Show Manage Users page
    @GetMapping("/manage-users")
    public String manageUsers() {
        return "admin/manage-users";
    }

    // Show registration form for Librarian or User
    @GetMapping("/register")
    public String showRegistrationForm(@RequestParam String role, Model model) {
        model.addAttribute("role", role);
        model.addAttribute("registrationForm", new RegistrationForm());
        return "admin/register-user";
    }

    // Handle user registration
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        registrationService.registerUser(username, password, role);
        return "redirect:/admin/manage-users";
    }
}