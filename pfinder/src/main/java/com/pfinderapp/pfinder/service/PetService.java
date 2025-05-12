package com.pfinderapp.pfinder.service;

import com.pfinderapp.pfinder.model.Pet;
import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public Pet addPet(Pet pet, User owner) {
        pet.setOwner(owner);
        return petRepository.save(pet);
    }

    public List<Pet> getPetsByOwner(User owner) {
        return petRepository.findByOwner(owner);
    }

    public List<Pet> getPetsForSwipe(Long userId) {
        return petRepository.findByOwnerUserIdNot(userId);
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pet not found"));
    }
} 