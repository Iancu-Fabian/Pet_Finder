package com.pfinderapp.pfinder.controller;

import com.pfinderapp.pfinder.model.Swipe;
import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.model.Pet;
import com.pfinderapp.pfinder.service.SwipeService;
import com.pfinderapp.pfinder.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/swipes")
public class SwipeController {

    @Autowired
    private SwipeService swipeService;

    @Autowired
    private PetService petService;

    @PostMapping("/{petId}")
    public ResponseEntity<?> swipePet(
            @PathVariable Long petId,
            @RequestParam Swipe.SwipeDirection direction,
            @AuthenticationPrincipal User user) {
        try {
            Pet pet = petService.getPetById(petId);
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