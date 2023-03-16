package com.telran.pizzaservice.service;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService{

    @Autowired
    PizzaRepository pizzaRepository;
    @Override
    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }
}
