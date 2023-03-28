package com.telran.pizzaservice.controller;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.entity.Pizzeria;
import com.telran.pizzaservice.service.PizzaService;
import com.telran.pizzaservice.service.PizzeriaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private PizzeriaService pizzeriaService;


    @GetMapping("/pizzas")
    public ResponseEntity<List<Pizza>> getAllPizzas(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(pizzaService.getAllPizzas(pageable), HttpStatus.OK);

    }

    @GetMapping("/pizzerias")
    public ResponseEntity<List<Pizzeria>> getAllPizzerias(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(pizzeriaService.getAllPizzerias(pageable), HttpStatus.OK);
    }

    @PostMapping("/pizzas")
    public ResponseEntity<Long> createPizza(@Valid @RequestBody Pizza pizza) {
        Long newId = pizzaService.createIfNotExists(pizza);
        log.info("New pizza created");

        return new ResponseEntity<>(newId, HttpStatus.CREATED);

    }

    @GetMapping("/pizzas/{id}")
    public ResponseEntity<Pizza> getPizza(@PathVariable Long id) {

        return new ResponseEntity<>(pizzaService.get(id), HttpStatus.OK);

    }

    @PutMapping("/pizzas/{id}")
    public ResponseEntity<Void> updatePizza(@PathVariable Long id, @Valid @RequestBody Pizza newPizza) {

        pizzaService.update(id, newPizza);
        log.info("Pizza Updated");
        return new ResponseEntity<>(null, HttpStatus.OK);

    }

    @DeleteMapping("/pizzas/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable Long id) {

        pizzaService.delete(id);
        log.info("Pizza deleted");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

    }

    @PostMapping("/pizzerias")
    public ResponseEntity<Long> createPizzeria(@Valid @RequestBody Pizzeria pizzeria) {

        Long newId = pizzeriaService.create(pizzeria);
        log.info("New pizzeria created");
        return new ResponseEntity<>(newId, HttpStatus.CREATED);

    }

    @GetMapping("/pizzerias/{pizzeria_id}")
    public ResponseEntity<Pizzeria> getPizzeriaById(@PathVariable Long pizzeria_id) {

        return new ResponseEntity<>(pizzeriaService.getPizzeriaById(pizzeria_id), HttpStatus.OK);

    }

    //TODO paging?
    @GetMapping("/pizzerias/{pizzeria_id}/pizzas/")
    public ResponseEntity<Set<Pizza>> getAllPizzasInPizzeria(@PathVariable Long pizzeria_id) {

        return new ResponseEntity<>(
                pizzeriaService.getPizzeriaById(pizzeria_id).getPizzas(), HttpStatus.OK);

    }

    @PostMapping("/pizzerias/{pizzeria_id}/pizzas")
    public ResponseEntity<Void> addPizzasToPizzeria(@PathVariable Long pizzeria_id, @Valid @RequestBody Set<Pizza> pizzas) {

        pizzeriaService.addPizzas(pizzeria_id, pizzas);
        log.info("Pizzas added to Pizzeria");
        return new ResponseEntity<>(null, HttpStatus.OK);

    }

    @DeleteMapping("/pizzerias/{pizzeria_id}/pizzas/{pizza_id}")
    public ResponseEntity<Void> deletePizzaFrommPizzeria(
            @PathVariable Long pizza_id,
            @PathVariable Long pizzeria_id) {

        pizzeriaService.deletePizza(pizzeria_id, pizza_id);
        log.info("Pizza deleted from pizzeria");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

    }


}
