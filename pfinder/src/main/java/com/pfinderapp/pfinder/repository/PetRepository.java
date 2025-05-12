package com.pfinderapp.pfinder.repository;

import com.pfinderapp.pfinder.model.Pet;
import com.pfinderapp.pfinder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwner(User owner);
    List<Pet> findByOwnerUserIdNot(Long userId);
} 