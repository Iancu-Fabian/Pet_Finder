package com.pfinderapp.pfinder.controller;

import com.pfinderapp.pfinder.model.Pet;
import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<?> addPet(@RequestBody Pet pet, @AuthenticationPrincipal User user) {
        try {
            Pet savedPet = petService.addPet(pet, user);
            return ResponseEntity.ok(savedPet);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my-pets")
    public ResponseEntity<List<Pet>> getMyPets(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(petService.getPetsByOwner(user));
    }

    @GetMapping("/swipe")
    public ResponseEntity<List<Pet>> getPetsForSwipe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(petService.getPetsForSwipe(user.getUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPet(@PathVariable Long id) {
        try {
            Pet pet = petService.getPetById(id);
            return ResponseEntity.ok(pet);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 