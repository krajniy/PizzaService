package com.telran.pizzaservice.controller;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.entity.Pizzeria;
import com.telran.pizzaservice.service.PizzaService;
import com.telran.pizzaservice.service.PizzeriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guest")

public class AnonymousController{
    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private PizzeriaService pizzeriaService;

    @GetMapping("/pizzas")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Pizza>> getAllPizzas() {
        try {
            return new ResponseEntity<>(pizzaService.getAllPizzas(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pizzerias")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Pizzeria>> getAllPizzerias() {
        try {
            return new ResponseEntity<>(pizzeriaService.getAllPizzerias(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
