package com.pfinderapp.pfinder.service;

import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.model.Pet;
import com.pfinderapp.pfinder.repository.UserRepository;
import com.pfinderapp.pfinder.repository.SwipeRepository;
import com.pfinderapp.pfinder.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SwipeRepository swipeRepository;

    @Autowired
    private PetRepository petRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Transactional
    public void deleteUser(String email) {
        User user = findByEmail(email);
        if (user != null) {
            // First, get all pets owned by this user
            List<Pet> userPets = petRepository.findByOwner(user);
            
            // For each pet, delete all swipes associated with it
            for (Pet pet : userPets) {
                swipeRepository.deleteByPet(pet);
            }
            
            // Delete all swipes made by this user
            swipeRepository.deleteByUser(user);
            
            // Delete all pets owned by this user
            petRepository.deleteByOwner(user);
            
            // Finally, delete the user
            userRepository.delete(user);
        }
    }
} 