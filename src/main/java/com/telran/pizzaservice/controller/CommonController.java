package com.telran.pizzaservice.controller;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.entity.Pizzeria;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommonController {
    ResponseEntity<List<Pizza>> getAllPizzas();
    ResponseEntity<List<Pizzeria>> getAllPizzerias();
}
