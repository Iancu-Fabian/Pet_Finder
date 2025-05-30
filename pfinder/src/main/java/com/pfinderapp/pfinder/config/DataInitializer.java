package com.pfinderapp.pfinder.config;

import com.pfinderapp.pfinder.model.Pet;
import com.pfinderapp.pfinder.model.User;
import com.pfinderapp.pfinder.repository.PetRepository;
import com.pfinderapp.pfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (petRepository.count() == 0) {
            List<Pet> samplePets = Arrays.asList(
                createPet("Max", "Golden Retriever", 2, "Medium", 25.0, "MALE", "Golden", 
                    "Friendly, Playful, Intelligent", "A loving and energetic dog who loves to play fetch", 
                    "Tennis Ball", "Peanut Butter", "Playing in the park", true, null,
                    "https://images.unsplash.com/photo-1552053831-71594a27632d?w=800&auto=format&h=500&fit=crop&q=40"),
                
                createPet("Luna", "Siamese Cat", 1, "Small", 4.5, "FEMALE", "Cream", 
                    "Elegant, Affectionate, Vocal", "A graceful cat who loves attention", 
                    "Feather Toy", "Tuna", "Sunbathing", true, null,
                    "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=800&auto=format&h=500&fit=crop&q=40"),
                
                createPet("Rocky", "Parrot", 3, "Small", 0.5, "MALE", "Colorful", 
                    "Talkative, Playful, Smart", "A beautiful parrot that loves to sing", 
                    "Mirror", "Seeds", "Flying around", true, null,
                    "https://images.unsplash.com/photo-1552728089-57bdde30beb3?w=800&auto=format&h=500&fit=crop&q=40"),
                
                createPet("Bella", "Labrador", 3, "Large", 30.0, "FEMALE", "Black", 
                    "Friendly, Active, Loyal", "A sweet and energetic lab who loves swimming", 
                    "Frisbee", "Chicken Treats", "Swimming", true, null,
                    "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=800&auto=format&h=500&fit=crop&q=40"),
                
                createPet("Charlie", "Persian Cat", 2, "Small", 5.0, "MALE", "White", 
                    "Calm, Affectionate, Independent", "A beautiful Persian cat who loves to lounge", 
                    "Cat Tower", "Salmon", "Napping", true, null,
                    "https://images.unsplash.com/photo-1518791841217-8f162f1e1131?w=800&auto=format&h=500&fit=crop&q=40")
            );

            petRepository.saveAll(samplePets);
            logger.info("Created {} sample pets for swiping", samplePets.size());
        } else {
            logger.info("Found {} existing pets in the database", petRepository.count());
        }
    }

    private Pet createPet(String name, String breed, int age, String size, double weight,
                         String gender, String color, String personalityTraits, String description,
                         String favToy, String favTreat, String favActivity, boolean vaccinationStatus,
                         User owner, String imageUrl) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setBreed(breed);
        pet.setAge(age);
        pet.setSize(size);
        pet.setWeight(weight);
        pet.setGender(gender);
        pet.setColor(color);
        pet.setPersonalityTraits(personalityTraits);
        pet.setDescription(description);
        pet.setFavToy(favToy);
        pet.setFavTreat(favTreat);
        pet.setFavActivity(favActivity);
        pet.setVaccinationStatus(vaccinationStatus);
        pet.setOwner(owner);
        pet.setImageUrl(imageUrl);
        return pet;
    }
} 