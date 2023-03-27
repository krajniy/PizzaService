package com.telran.pizzaservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pizza")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name must be no more than 50 characters long")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    @Min(value = 0, message = "Price must be greater than zero")
    private Double price;

    @Column(name = "image_url")
    private String imgUrl;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, mappedBy = "pizzas")
    @JsonIgnore
    private Set<Pizzeria> pizzerias = new HashSet<>();
}
