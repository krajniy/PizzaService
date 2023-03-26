package com.telran.pizzaservice.service;

import com.telran.pizzaservice.entity.Pizza;

import java.util.List;


public interface PizzaService {
    List<Pizza> getAllPizzas();

    Pizza get(Long id);

    void update(Long id, Pizza newPizza);

    void delete(Long id);

    Long createIfNotExists(Pizza pizza);
}
