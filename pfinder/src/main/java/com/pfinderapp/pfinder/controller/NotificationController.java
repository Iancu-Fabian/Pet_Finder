package com.pfinderapp.pfinder.controller;

import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.service.NotificationService;
import com.pfinderapp.pfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class NotificationController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @GetMapping("/notifications")
    public ResponseEntity<?> getNotifications() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            logger.info("Fetching notifications for user: {}", email);
            
            User user = userService.findByEmail(email);
            if (user == null) {
                logger.error("User not found for email: {}", email);
                return ResponseEntity.badRequest().body("User not found");
            }

            List<Map<String, String>> notifications = notificationService.getUserNotifications(user);
            logger.info("Found {} notifications for user: {}", notifications.size(), email);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            logger.error("Error fetching notifications", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 