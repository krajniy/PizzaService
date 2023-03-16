package com.telran.pizzaservice.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "pizzeria_pizza")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PizzeriaPizza {
    @EmbeddedId
    private PizzeriaPizzaId id;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pizzeriaId")
    private Pizzeria pizzeria;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pizzaId")
    private Pizza pizza;


}
