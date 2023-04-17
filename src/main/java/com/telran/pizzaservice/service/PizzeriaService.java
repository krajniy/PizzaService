package com.telran.pizzaservice.service;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.entity.Pizzeria;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface PizzeriaService {
    List<Pizzeria> getAllPizzerias(Pageable pageable);

    Long create(Pizzeria pizzeria);

    Pizzeria getPizzeriaById(Long pizzeriaId);

    void addPizzas(Long pizzeriaId, Set<Pizza> pizzas);

    @Transactional
    void addPizzasByIds(Long pizzeriaId, Set<Long> pizzasIds);

    void deletePizza(Long pizzeriaId, Long pizzaId);
}
