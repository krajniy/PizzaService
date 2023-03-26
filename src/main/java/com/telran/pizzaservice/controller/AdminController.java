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
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private PizzeriaService pizzeriaService;


    @GetMapping("/pizzas")
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
    public ResponseEntity<List<Pizzeria>> getAllPizzerias() {
        try {
            return new ResponseEntity<>(pizzeriaService.getAllPizzerias(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/pizzas")
    public ResponseEntity<Long> createPizza(@RequestBody Pizza pizza){
        try {
            return new ResponseEntity<>(pizzaService.createIfNotExists(pizza), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pizzas/{id}")
    public ResponseEntity<Pizza> getPizza(@PathVariable Long id){
        try {
            return new ResponseEntity<>(pizzaService.get(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/pizzas/{id}")
    public ResponseEntity<Void> updatePizza(@PathVariable Long id, @RequestBody Pizza newPizza){
        try {
            pizzaService.update(id, newPizza);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/pizzas/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable Long id){
        try {
            pizzaService.delete(id);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/pizzerias")
    public ResponseEntity<Long> createPizzeria(@RequestBody Pizzeria pizzeria){
        try {
            return new ResponseEntity<>(pizzeriaService.create(pizzeria), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
