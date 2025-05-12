package com.pfinderapp.pfinder.repository;

import com.pfinderapp.pfinder.model.Swipe;
import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SwipeRepository extends JpaRepository<Swipe, Long> {
    List<Swipe> findByUser(User user);
    boolean existsByUserAndPet(User user, Pet pet);
} 