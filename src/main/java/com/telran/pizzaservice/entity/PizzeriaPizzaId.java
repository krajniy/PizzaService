package com.telran.pizzaservice.entity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PizzeriaPizzaId implements Serializable {
    @Column(name = "pizzeria_id")
    private Long pizzeriaId;

    @Column(name = "pizza_id")
    private Long pizzaId;
}
