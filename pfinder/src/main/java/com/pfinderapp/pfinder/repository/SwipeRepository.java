package com.pfinderapp.pfinder.repository;

import com.pfinderapp.pfinder.model.Swipe;
import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SwipeRepository extends JpaRepository<Swipe, Long> {
    List<Swipe> findByUser(User user);
    
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Swipe s WHERE s.user = :user AND s.pet = :pet")
    boolean existsByUserAndPet(@Param("user") User user, @Param("pet") Pet pet);

    @Query("SELECT s FROM Swipe s WHERE s.pet.owner = :owner AND s.swipeDirection = :direction")
    List<Swipe> findByPetOwnerAndSwipeDirection(@Param("owner") User owner, @Param("direction") Swipe.SwipeDirection direction);

    void deleteByUser(User user);
    
    void deleteByPet(Pet pet);
} 