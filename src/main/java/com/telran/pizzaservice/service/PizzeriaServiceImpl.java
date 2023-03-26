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
import java.util.Optional;
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
    @Transactional
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

    @Override
    @Transactional(readOnly = true)
    public Pizzeria getPizzeriaById(Long pizzeriaId) {
        //TODO PizzeriaNotFoundException
        return pizzeriaRepository.findById(pizzeriaId).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    @Transactional
    public void addPizzas(Long pizzeriaId, Set<Pizza> pizzas) {
        Optional<Pizzeria> pizzeriaOptional = pizzeriaRepository.findById(pizzeriaId);
        if (pizzeriaOptional.isPresent()){
            Pizzeria pizzeria = pizzeriaOptional.get();
            Set<Pizza> existingPizzas = pizzeria.getPizzas();

            pizzas.forEach(p -> {
                Optional<Pizza> pizzaOptional = pizzaRepository.findById(pizzaService.createIfNotExists(p));
                if (pizzaOptional.isPresent()){
                    Pizza existingPizza = pizzaOptional.get();
                    if (!existingPizzas.contains(existingPizza)) {
                        existingPizzas.add(existingPizza);
                    }
                }
            });
            pizzeriaRepository.save(pizzeria);

        } else {
            throw new IllegalArgumentException("Pizzeria not found with id: " + pizzeriaId);
        }


    }

    @Override
    @Transactional
    public void deletePizza(Long pizzeriaId, Long pizzaId) {
        Pizzeria pizzeria = getPizzeriaById(pizzeriaId);
        Set<Pizza> pizzas = pizzeria.getPizzas();
        Optional<Pizza> pizzaToRemove = pizzas.stream().filter(pizza -> pizza.getId().equals(pizzaId)).findFirst();
        if (pizzaToRemove.isPresent()) {
            pizzas.remove(pizzaToRemove.get());
            pizzeria.setPizzas(pizzas);
            pizzeriaRepository.save(pizzeria);
        } else {
            throw new IllegalArgumentException("Pizza not found in pizzeria.");
        }
    }
}
