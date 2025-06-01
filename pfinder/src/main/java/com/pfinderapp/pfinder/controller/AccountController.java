package com.pfinderapp.pfinder.controller;

import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public String showAccountInfo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.findByEmail(email);
        
        model.addAttribute("user", user);
        return "account";
    }

    @GetMapping("/settings")
    public String showSettings() {
        return "account/settings";
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deleteAccount() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            logger.info("Deleting account for user: {}", email);
            
            userService.deleteUser(email);
            logger.info("Successfully deleted account for user: {}", email);
            
            return ResponseEntity.ok().body(Map.of("message", "Account deleted successfully"));
        } catch (Exception e) {
            logger.error("Error deleting account", e);
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 