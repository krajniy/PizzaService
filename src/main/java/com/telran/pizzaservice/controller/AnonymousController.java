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
import java.util.Set;

@RestController
@RequestMapping("/guest")

public class AnonymousController{
    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private PizzeriaService pizzeriaService;

    @Autowired
    AdminController adminController;

    @GetMapping("/pizzas")
    public ResponseEntity<List<Pizza>> getAllPizzas(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return adminController.getAllPizzas(page, size);
    }

    @GetMapping("/pizzerias")
    public ResponseEntity<List<Pizzeria>> getAllPizzerias(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        return adminController.getAllPizzerias(page, size);
    }


    @GetMapping("/pizzas/{pizza_name}")
    public ResponseEntity<Pizza> getPizzaByName(@PathVariable String pizza_name){
        try {
            return new ResponseEntity<>(pizzaService.findByName(pizza_name), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pizzerias/{pizzeria_id}/pizzas/")
    public ResponseEntity<Set<Pizza>> getAllPizzasInPizzeria(@PathVariable Long pizzeria_id){
        return adminController.getAllPizzasInPizzeria(pizzeria_id);
    }

}
