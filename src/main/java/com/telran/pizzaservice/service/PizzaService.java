package com.telran.pizzaservice.service;

import com.telran.pizzaservice.entity.Pizza;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PizzaService {
    List<Pizza> getAllPizzas(Pageable pageable);

    Pizza get(Long id);

    void update(Long id, Pizza newPizza);

    void delete(Long id);

    Long createIfNotExists(Pizza pizza);

    Pizza findByName(String pizzaName);
}
