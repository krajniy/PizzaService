package com.telran.pizzaservice.service;

import com.telran.pizzaservice.entity.Pizza;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PizzaService {
    List<Pizza> getAllPizzas();
}
