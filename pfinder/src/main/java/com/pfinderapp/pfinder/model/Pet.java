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
    private Long petId;

    @NotBlank
    private String name;
    
    private String breed;
    private Integer age;
    private String size;
    private Double weight;
    private String gender;
    private String color;
    private String personalityTraits;
    private String description;
    private String favToy;
    private String favTreat;
    private String favActivity;
    private Boolean vaccinationStatus;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String img1;
    private String img2;
    private String img3;
} 