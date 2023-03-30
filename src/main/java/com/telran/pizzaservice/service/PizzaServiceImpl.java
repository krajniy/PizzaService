package com.telran.pizzaservice.service;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.exception.PizzaNotFoundException;
import com.telran.pizzaservice.repository.PizzaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;


    @Override
    public List<Pizza> getAllPizzas(Pageable pageable) {
        Page<Pizza> pizzas = pizzaRepository.findAll(pageable);
        if (pizzas.isEmpty()) {
            throw new PizzaNotFoundException("No pizzas found on this page");
        }
        return pizzas.getContent();
    }

    @Override
    @Transactional()
    public Long createIfNotExists(@Valid Pizza pizza) {
        Optional<Pizza> existingPizza = pizzaRepository.findByName(pizza.getName());
        if (existingPizza.isPresent()) {
            return existingPizza.get().getId();
        } else {
            if (pizza.getBasePrice() == null) {
                pizza.setBasePrice(pizza.getPrice());
            }
            return pizzaRepository.save(pizza).getId();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Pizza findByName(String pizzaName) {
        return pizzaRepository.findByName(pizzaName).orElseThrow(PizzaNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Pizza get(Long id) {

        return pizzaRepository.findById(id).orElseThrow(PizzaNotFoundException::new);
    }

    @Override
    @Transactional()
    public void update(Long id, Pizza newPizza) {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(PizzaNotFoundException::new);
        pizza.setName(newPizza.getName());
        pizza.setDescription(newPizza.getDescription());
        pizza.setPrice(newPizza.getPrice());
        pizza.setPizzerias(newPizza.getPizzerias());
        pizza.setImgUrl(newPizza.getImgUrl());
        if (newPizza.getBasePrice() != null) {
            pizza.setBasePrice(newPizza.getBasePrice());
        } else {
            pizza.setBasePrice(newPizza.getPrice());
        }

    }

    @Override
    @Transactional()
    public void delete(Long id) {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(PizzaNotFoundException::new);
        pizzaRepository.delete(pizza);
    }
}
