package com.telran.pizzaservice.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "image_url")
    private String imgUrl;
}
