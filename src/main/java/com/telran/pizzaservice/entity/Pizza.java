package com.telran.pizzaservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Pizza Entity
 *
 * @author Elena Ivanishcheva
 */
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

    @Column(name = "base_price")
    @Min(value = 0, message = "Base price must be greater than zero")
    private Double basePrice;

    @Column(name = "image_url")
    private String imgUrl;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            }, mappedBy = "pizzas")
//    @JsonIgnore
//    private Set<Pizzeria> pizzerias = new HashSet<>();

    @PostConstruct
    public void init() {
        if (basePrice == null) {
            this.basePrice = this.price;
        }
    }

    public Pizza(Long id, String name, String description, Double price, String imgUrl) {
//        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.basePrice = price;
        this.imgUrl = imgUrl;
//        this.pizzerias = pizzerias;
    }
}
