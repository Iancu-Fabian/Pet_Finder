package com.pfinderapp.pfinder.controller;

import com.pfinderapp.pfinder.model.Pet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model) {
        // This is a mock pet for demonstration
        Pet pet = new Pet();
        pet.setName("Max");
        pet.setBreed("Golden Retriever");
        pet.setAge(2);
        pet.setDescription("Friendly and playful dog looking for a loving home");
        pet.setImg1("https://images.unsplash.com/photo-1552053831-71594a27632d?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        
        model.addAttribute("pet", pet);
        return "home";
    }
} 