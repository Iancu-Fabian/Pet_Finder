package com.pfinderapp.pfinder.service;

import com.pfinderapp.pfinder.model.Pet;
import com.pfinderapp.pfinder.model.Swipe;
import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.repository.SwipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private SwipeRepository swipeRepository;

    public List<Map<String, String>> getUserNotifications(User user) {
        logger.info("Getting notifications for user ID: {}", user.getUserId());
        List<Map<String, String>> notifications = new ArrayList<>();
        
        try {
            // Get all right swipes on this user's pets
            List<Swipe> rightSwipes = swipeRepository.findByPetOwnerAndSwipeDirection(user, Swipe.SwipeDirection.RIGHT);
            logger.info("Found {} right swipes for user's pets", rightSwipes.size());

            for (Swipe swipe : rightSwipes) {
                Map<String, String> notification = new HashMap<>();
                Pet pet = swipe.getPet();
                User swiper = swipe.getUser();
                
                if (pet != null && swiper != null) {
                    notification.put("userName", swiper.getFirstname());
                    notification.put("petName", pet.getName());
                    notification.put("phoneNumber", swiper.getPhoneNumber());
                    notification.put("timestamp", swipe.getTimestamp().toString());
                    notifications.add(notification);
                    logger.info("Added notification for pet: {} from user: {}", pet.getName(), swiper.getEmail());
                } else {
                    logger.warn("Skipping notification - pet or swiper is null for swipe ID: {}", swipe.getSwipeId());
                }
            }
        } catch (Exception e) {
            logger.error("Error getting notifications", e);
        }
        
        return notifications;
    }
} 