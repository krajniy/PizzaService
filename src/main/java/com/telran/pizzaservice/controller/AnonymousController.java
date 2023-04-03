package com.telran.pizzaservice.controller;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.entity.Pizzeria;
import com.telran.pizzaservice.exception.PizzaNotFoundException;
import com.telran.pizzaservice.exception.PizzeriaNotFoundException;
import com.telran.pizzaservice.service.PizzaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * REST controller for a guest to get pizzas and pizzerias
 *
 * @author Elena Ivanishcheva
 */

@Slf4j
@RestController
@RequestMapping("/guest")

public class AnonymousController {
    @Autowired
    private PizzaService pizzaService;

    @Autowired
    AdminController adminController;


    /**
     * Get all pizzas
     *
     * @param page Page number
     * @param size Page size
     * @return ResponseEntity with a list of pizzas and HTTP status code
     */

    @Operation(summary = "Get all pizzas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Pizzas not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping("/pizzas")
    public ResponseEntity<List<Pizza>> getAllPizzas(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return adminController.getAllPizzas(page, size);
    }

    /**
     * Get all pizzerias
     *
     * @param page Page number
     * @param size Page size
     * @return ResponseEntity with a list of pizzerias and HTTP status code
     */

    @Operation(summary = "Get all pizzerias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Pizzerias not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping("/pizzerias")
    public ResponseEntity<List<Pizzeria>> getAllPizzerias(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        return adminController.getAllPizzerias(page, size);
    }


    /**
     * Get a pizza by name
     *
     * @param pizza_name Name of the pizza
     * @return ResponseEntity with the pizza and HTTP status code
     * @throws PizzaNotFoundException if the pizza with the given name does not exist
     */
    @Operation(summary = "Get a pizza by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Pizza not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping("/pizzas/{pizza_name}")
    public ResponseEntity<Pizza> getPizzaByName(@PathVariable String pizza_name) {

        return new ResponseEntity<>(pizzaService.findByName(pizza_name), HttpStatus.OK);

    }

    /**
     * Get all pizzas in a pizzeria
     *
     * @param pizzeria_id ID of the pizzeria
     * @return ResponseEntity with a set of pizzas and HTTP status code
     * @throws PizzeriaNotFoundException if the specified pizzeria does not exist
     */
    @Operation(summary = "Get all pizzas in a pizzeria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Pizzeria not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping("/pizzerias/{pizzeria_id}/pizzas/")
    public ResponseEntity<Set<Pizza>> getAllPizzasInPizzeria(@PathVariable Long pizzeria_id) {
        return adminController.getAllPizzasInPizzeria(pizzeria_id);
    }

}
