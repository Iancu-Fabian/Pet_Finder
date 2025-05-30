package com.pfinderapp.pfinder.service;

import com.pfinderapp.pfinder.model.Pet;
import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.repository.PetRepository;
import com.pfinderapp.pfinder.repository.SwipeRepository;
import com.pfinderapp.pfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PetService {

    private static final Logger logger = LoggerFactory.getLogger(PetService.class);

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private SwipeRepository swipeRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Pet> getPetsForSwipe(Long userId) {
        logger.debug("Getting pets for swipe for user: {}", userId);
        
        // Get all pets that either have no owner or are not owned by the user
        List<Pet> availablePets = petRepository.findByOwnerIsNullOrOwnerUserIdNot(userId);
        logger.debug("Found {} available pets", availablePets.size());
        
        if (availablePets.isEmpty()) {
            logger.warn("No available pets found in the database!");
            return availablePets;
        }
        
        // Get the user object for filtering swipes
        User user = userRepository.findById(userId)
            .orElseThrow(() -> {
                logger.error("User not found with ID: {}", userId);
                return new RuntimeException("User not found");
            });
        
        // Filter out pets that the user has already swiped on
        List<Pet> filteredPets = availablePets.stream()
            .filter(pet -> !swipeRepository.existsByUserAndPet(user, pet))
            .collect(Collectors.toList());
        
        logger.debug("After filtering swipes, {} pets remain", filteredPets.size());
        
        if (filteredPets.isEmpty()) {
            logger.warn("No pets remain after filtering swipes!");
        } else {
            logger.debug("First pet after filtering - Name: {}, Breed: {}, Owner: {}", 
                filteredPets.get(0).getName(), 
                filteredPets.get(0).getBreed(),
                filteredPets.get(0).getOwner() != null ? filteredPets.get(0).getOwner().getEmail() : "null");
        }
        
        return filteredPets;
    }

    public Pet getPetById(Long petId) {
        return petRepository.findById(petId)
            .orElseThrow(() -> new RuntimeException("Pet not found"));
    }

    public Pet savePet(Pet pet, String imageUrl) {
        pet.setImageUrl(imageUrl);
        return petRepository.save(pet);
    }

    public List<Pet> getPetsByOwner(User owner) {
        return petRepository.findByOwner(owner);
    }

    public void deletePet(Long petId) {
        petRepository.deleteById(petId);
    }
} 