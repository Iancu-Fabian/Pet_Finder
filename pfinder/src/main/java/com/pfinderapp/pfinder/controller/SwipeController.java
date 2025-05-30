package com.pfinderapp.pfinder.controller;

import com.pfinderapp.pfinder.model.Swipe;
import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.model.Pet;
import com.pfinderapp.pfinder.service.SwipeService;
import com.pfinderapp.pfinder.service.PetService;
import com.pfinderapp.pfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/swipes")
public class SwipeController {

    @Autowired
    private SwipeService swipeService;

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @PostMapping("/{petId}")
    public ResponseEntity<?> swipePet(
            @PathVariable Long petId,
            @RequestParam Swipe.SwipeDirection direction) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User user = userService.findByEmail(email);
            
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found");
            }

            Pet pet = petService.getPetById(petId);
            if (pet == null) {
                return ResponseEntity.badRequest().body("Pet not found");
            }

            // Check if user is trying to swipe on their own pet
            if (pet.getOwner() != null && pet.getOwner().getUserId().equals(user.getUserId())) {
                return ResponseEntity.badRequest().body("Cannot swipe on your own pet");
            }

            Swipe swipe = swipeService.createSwipe(user, pet, direction);
            return ResponseEntity.ok(swipe);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my-swipes")
    public ResponseEntity<List<Swipe>> getMySwipes(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(swipeService.getUserSwipes(user));
    }
} 