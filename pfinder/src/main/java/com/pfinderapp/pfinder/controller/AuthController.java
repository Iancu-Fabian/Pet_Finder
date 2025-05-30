package com.pfinderapp.pfinder.controller;

import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        logger.debug("Showing registration form");
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/api/auth/register-form")
    public String registerForm(@ModelAttribute User user, Model model) {
        logger.debug("Received registration form submission");
        logger.debug("User details - Email: {}, First Name: {}, Last Name: {}, Phone: {}", 
            user.getEmail(), user.getFirstname(), user.getLastname(), user.getPhoneNumber());
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            logger.error("Email is null or empty");
            model.addAttribute("error", "Email is required");
            return "auth/register";
        }
        
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            logger.error("Password is null or empty");
            model.addAttribute("error", "Password is required");
            return "auth/register";
        }
        
        try {
            logger.debug("Attempting to register user with email: {}", user.getEmail());
            User registeredUser = authService.registerUser(user);
            logger.debug("Registration successful for user: {}", registeredUser.getEmail());
            return "redirect:/login?registered=true";
        } catch (RuntimeException e) {
            logger.error("Registration failed: ", e);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            return "auth/register";
        }
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = authService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/auth/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        try {
            User verifiedUser = authService.verifyEmail(token);
            return ResponseEntity.ok(verifiedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 