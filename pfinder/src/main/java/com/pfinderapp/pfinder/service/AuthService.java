package com.pfinderapp.pfinder.service;

import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.repository.UserRepository;
import com.pfinderapp.pfinder.config.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataInitializer dataInitializer;
    
    public User registerUser(User user) {
        logger.debug("Starting user registration process");
        logger.debug("User details - Email: {}, First Name: {}, Last Name: {}", 
            user.getEmail(), user.getFirstname(), user.getLastname());
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            logger.error("Email is null or empty");
            throw new RuntimeException("Email is required");
        }
        
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.debug("Registration failed: Email already exists: {}", user.getEmail());
            throw new RuntimeException("Email already exists");
        }
        
        try {
            logger.debug("Encoding password for user: {}", user.getEmail());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            
            logger.debug("Generating verification token for user: {}", user.getEmail());
            user.setVerificationToken(UUID.randomUUID().toString());
            
            logger.debug("Setting user as enabled: {}", user.getEmail());
            user.setEnabled(true);
            
            logger.debug("Attempting to save user to database: {}", user.getEmail());
            User savedUser = userRepository.save(user);
            logger.debug("User registered successfully: {}", savedUser.getEmail());
            
            return savedUser;
        } catch (Exception e) {
            logger.error("Error during user registration: ", e);
            throw new RuntimeException("Error during registration: " + e.getMessage());
        }
    }
    
    public User verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid verification token"));
        
        user.setEnabled(true);
        user.setVerificationToken(null);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
} 