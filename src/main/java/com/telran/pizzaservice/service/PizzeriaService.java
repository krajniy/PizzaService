package com.telran.pizzaservice.service;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.entity.Pizzeria;

import java.util.List;
import java.util.Set;

public interface PizzeriaService {
    List<Pizzeria> getAllPizzerias();

    Long create(Pizzeria pizzeria);

    Pizzeria getPizzeriaById(Long pizzeriaId);

    void addPizzas(Long pizzeriaId, Set<Pizza> pizzas);

    void deletePizza(Long pizzeriaId, Long pizzaId);
}
