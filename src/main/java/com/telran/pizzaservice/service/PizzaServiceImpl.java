package com.telran.pizzaservice.service;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.repository.PizzaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaServiceImpl implements PizzaService{

    @Autowired
    PizzaRepository pizzaRepository;


    @Override
    public List<Pizza> getAllPizzas(Pageable pageable) {
        return pizzaRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional()
    public Long createIfNotExists(@Valid Pizza pizza) {
        Optional<Pizza> existingPizza = pizzaRepository.findByName(pizza.getName());
        if (existingPizza.isPresent()) {
            return existingPizza.get().getId();
        } else {
            return pizzaRepository.save(pizza).getId();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Pizza findByName(String pizzaName) {
        return pizzaRepository.findByName(pizzaName).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Pizza get(Long id) {
        return pizzaRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    @Transactional()
    public void update(Long id, Pizza newPizza) {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        pizza.setName(newPizza.getName());
        pizza.setDescription(newPizza.getDescription());
        pizza.setPrice(newPizza.getPrice());
        pizza.setPizzerias(newPizza.getPizzerias());
        pizza.setImgUrl(newPizza.getImgUrl());

    }

    @Override
    @Transactional()
    public void delete(Long id) {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        pizzaRepository.delete(pizza);
    }
}
