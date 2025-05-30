package com.pfinderapp.pfinder.controller;

import com.pfinderapp.pfinder.model.Pet;
import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.service.PetService;
import com.pfinderapp.pfinder.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PetController {
    
    private static final Logger logger = LoggerFactory.getLogger(PetController.class);

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @GetMapping("/pets/add")
    public String showAddPetForm(Model model) {
        logger.debug("Accessing /pets/add endpoint");
        return "pets/add";
    }

    @PostMapping("/pets/add")
    public String addPet(
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("breed") String breed,
            @RequestParam("age") Integer age,
            @RequestParam("gender") String gender,
            @RequestParam("description") String description,
            @RequestParam("imageUrl") String imageUrl) {
        
        logger.debug("Processing pet addition for name: {}", name);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.findByEmail(email);

        Pet pet = new Pet();
        pet.setName(name);
        pet.setBreed(breed);
        pet.setAge(age);
        pet.setGender(gender);
        pet.setDescription(description);
        pet.setOwner(user);

        petService.savePet(pet, imageUrl);
        
        return "redirect:/pets/my-pets";
    }

    @GetMapping("/pets/my-pets")
    public String showMyPets(Model model) {
        logger.debug("Accessing /pets/my-pets endpoint");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.findByEmail(email);
        
        model.addAttribute("pets", petService.getPetsByOwner(user));
        return "pets/my-pets";
    }

    @DeleteMapping("/pets/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deletePet(@PathVariable Long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User user = userService.findByEmail(email);
            
            Pet pet = petService.getPetById(id);
            // Verify that the pet belongs to the current user
            if (!pet.getOwner().getEmail().equals(user.getEmail())) {
                return ResponseEntity.status(403).body("Not authorized to delete this pet");
            }
            
            petService.deletePet(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting pet", e);
            return ResponseEntity.status(500).body("Error deleting pet");
        }
    }

    @GetMapping("/pets/edit/{id}")
    public String showEditPetForm(@PathVariable Long id, Model model) {
        Pet pet = petService.getPetById(id);
        if (pet == null) {
            return "redirect:/pets/my-pets";
        }
        model.addAttribute("pet", pet);
        return "pets/edit-pet";
    }

    @PostMapping("/pets/edit/{id}")
    public String updatePet(@PathVariable Long id, @ModelAttribute Pet petDetails) {
        Pet pet = petService.getPetById(id);
        if (pet == null) {
            return "redirect:/pets/my-pets";
        }
        pet.setName(petDetails.getName());
        pet.setBreed(petDetails.getBreed());
        pet.setAge(petDetails.getAge());
        pet.setGender(petDetails.getGender());
        pet.setDescription(petDetails.getDescription());
        pet.setImageUrl(petDetails.getImageUrl());
        petService.savePet(pet, pet.getImageUrl());
        return "redirect:/pets/my-pets";
    }

    @GetMapping("/pets/swipe")
    public String showSwipeInterface(Model model) {
        logger.debug("Accessing /pets/swipe endpoint");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        logger.debug("User email: {}", email);
        
        User user = userService.findByEmail(email);
        logger.debug("Found user: {}", user.getUserId());
        
        // Get pets that the user hasn't swiped on yet
        List<Pet> pets = petService.getPetsForSwipe(user.getUserId());
        logger.debug("Found {} pets for swiping", pets.size());
        
        if (pets.isEmpty()) {
            logger.warn("No pets found for swiping!");
        } else {
            logger.debug("First pet details - Name: {}, Breed: {}, Owner: {}", 
                pets.get(0).getName(), 
                pets.get(0).getBreed(),
                pets.get(0).getOwner() != null ? pets.get(0).getOwner().getEmail() : "null");
        }
        
        model.addAttribute("pets", pets);
        return "pets/swipe";
    }
} 