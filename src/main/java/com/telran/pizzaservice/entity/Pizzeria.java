package com.telran.pizzaservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pizzeria")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pizzeria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;
}
