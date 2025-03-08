package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.service.BookService;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String userDashboard(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "user/dashboard";
    }

    @PostMapping("/borrow-book")
    public String borrowBook(@RequestParam String bookId) {
        Book book = bookService.findById(bookId);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            bookService.save(book); // Save the updated book to MongoDB
        }
        return "redirect:/user/dashboard";
    }

    @PostMapping("/return-book")
    public String returnBook(@RequestParam String bookId) {
        Book book = bookService.findById(bookId);
        if (book != null) {
            book.setAvailable(true);
            bookService.save(book); // Save the updated book to MongoDB
        }
        return "redirect:/user/dashboard";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Thymeleaf template for registration form
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role) {
        // Call the UserService to register the user
        userService.registerUser(username, password, role);
        return "redirect:/user/dashboard"; // Redirect to dashboard after registration
    }
}
