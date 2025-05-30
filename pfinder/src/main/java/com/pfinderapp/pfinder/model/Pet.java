package com.pfinderapp.pfinder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "pets")
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long petId;

    @NotBlank
    private String name;
    
    private String breed;
    private Integer age;
    private String size;
    private Double weight;
    private String gender;
    private String color;
    
    @Column(name = "personality_traits")
    private String personalityTraits;

    private String description;
    
    @Column(name = "fav_toy")
    private String favToy;
    
    @Column(name = "fav_treat")
    private String favTreat;
    
    @Column(name = "fav_activity")
    private String favActivity;
    
    @Column(name = "vaccination_status")
    private Boolean vaccinationStatus;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    // Getters and Setters
    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPersonalityTraits() {
        return personalityTraits;
    }

    public void setPersonalityTraits(String personalityTraits) {
        this.personalityTraits = personalityTraits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFavToy() {
        return favToy;
    }

    public void setFavToy(String favToy) {
        this.favToy = favToy;
    }

    public String getFavTreat() {
        return favTreat;
    }

    public void setFavTreat(String favTreat) {
        this.favTreat = favTreat;
    }

    public String getFavActivity() {
        return favActivity;
    }

    public void setFavActivity(String favActivity) {
        this.favActivity = favActivity;
    }

    public Boolean getVaccinationStatus() {
        return vaccinationStatus;
    }

    public void setVaccinationStatus(Boolean vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
} 