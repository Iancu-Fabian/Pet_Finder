package com.pfinderapp.pfinder.repository;

import com.pfinderapp.pfinder.model.Pet;
import com.pfinderapp.pfinder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwner(User owner);
    
    @Query("SELECT p FROM Pet p WHERE p.owner IS NULL OR (p.owner IS NOT NULL AND p.owner.userId != :userId)")
    List<Pet> findByOwnerIsNullOrOwnerUserIdNot(@Param("userId") Long userId);

    void deleteByOwner(User owner);
} 