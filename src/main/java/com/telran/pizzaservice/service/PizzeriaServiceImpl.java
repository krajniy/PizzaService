package com.telran.pizzaservice.service;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.entity.Pizzeria;
import com.telran.pizzaservice.repository.PizzaRepository;
import com.telran.pizzaservice.repository.PizzeriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PizzeriaServiceImpl implements PizzeriaService{

    @Autowired
    PizzeriaRepository pizzeriaRepository;

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    PizzaService pizzaService;

    @Override
    @Transactional(readOnly = true)
    public List<Pizzeria> getAllPizzerias() {
        return pizzeriaRepository.findAll();
    }

    @Override
    public Long create(Pizzeria pizzeria) {
        Set<Pizza> pizzas = pizzeria.getPizzas();
        Set<Pizza> savedPizzas = new HashSet<>();

        for (Pizza pizza : pizzas) {
            Pizza newPizza = pizzaRepository.findById(pizzaService.createIfNotExists(pizza)).get();
            savedPizzas.add(newPizza);
        }

        pizzeria.setPizzas(savedPizzas);

        pizzeriaRepository.save(pizzeria);

        return pizzeria.getId();

    }
}
