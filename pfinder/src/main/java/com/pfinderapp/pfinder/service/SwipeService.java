package com.pfinderapp.pfinder.service;

import com.pfinderapp.pfinder.model.Swipe;
import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.model.Pet;
import com.pfinderapp.pfinder.repository.SwipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SwipeService {

    @Autowired
    private SwipeRepository swipeRepository;

    public Swipe createSwipe(User user, Pet pet, Swipe.SwipeDirection direction) {
        if (swipeRepository.existsByUserAndPet(user, pet)) {
            throw new RuntimeException("User has already swiped on this pet");
        }

        Swipe swipe = new Swipe();
        swipe.setUser(user);
        swipe.setPet(pet);
        swipe.setSwipeDirection(direction);
        
        return swipeRepository.save(swipe);
    }

    public List<Swipe> getUserSwipes(User user) {
        return swipeRepository.findByUser(user);
    }
} 