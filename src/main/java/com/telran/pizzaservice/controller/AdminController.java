package com.telran.pizzaservice.controller;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.entity.Pizzeria;
import com.telran.pizzaservice.exception.PizzaNotFoundException;
import com.telran.pizzaservice.exception.PizzeriaNotFoundException;
import com.telran.pizzaservice.service.PizzaService;
import com.telran.pizzaservice.service.PizzeriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * REST controller for an admin to make CRUD operations with pizzas and pizzerias
 *
 * @author Elena Ivanishcheva
 */
@Slf4j
@RestController
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private PizzeriaService pizzeriaService;


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

        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(pizzaService.getAllPizzas(pageable), HttpStatus.OK);

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

        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(pizzeriaService.getAllPizzerias(pageable), HttpStatus.OK);
    }


    /**
     * Creates a new pizza.
     *
     * @param pizza the pizza to be created
     * @return a ResponseEntity containing the ID of the newly created pizza and an HTTP status code of 201 (Created)
     * @throws MethodArgumentNotValidException if the input Pizza resource is not valid
     */
    @Operation(summary = "Create a new pizza", description = "Creates a new pizza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The pizza was created successfully"),
            @ApiResponse(responseCode = "422", description = "The request body was invalid")
    })
    @PostMapping("/pizzas")
    public ResponseEntity<Long> createPizza(@Valid @RequestBody Pizza pizza) {
        Long newId = pizzaService.createIfNotExists(pizza);
        log.info("New pizza created");

        return new ResponseEntity<>(newId, HttpStatus.CREATED);
    }

    /**
     * Retrieves a pizza by its ID.
     *
     * @param id the ID of the pizza to retrieve
     * @return a ResponseEntity containing the retrieved pizza and a HTTP OK status
     * @throws PizzaNotFoundException if the pizza with the given ID does not exist
     */
    @Operation(summary = "Retrieve a pizza by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the pizza",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pizza.class))),
            @ApiResponse(responseCode = "404", description = "Pizza not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/pizzas/{id}")
    public ResponseEntity<Pizza> getPizza(@PathVariable Long id) {
        return new ResponseEntity<>(pizzaService.get(id), HttpStatus.OK);

    }

    /**
     * Update a pizza with the given id.
     *
     * @param id       the id of the pizza to update
     * @param newPizza the updated pizza object
     * @return ResponseEntity with HTTP status code and response body
     * @throws PizzaNotFoundException          if the pizza with the given ID does not exist
     * @throws MethodArgumentNotValidException if the input Pizza resource is not valid
     */
    @Operation(summary = "Update a pizza by ID", description = "Update an existing pizza by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The pizza was successfully updated."),
            @ApiResponse(responseCode = "404", description = "The pizza with the given ID does not exist."),
            @ApiResponse(responseCode = "422", description = "The request is invalid, e.g., missing required fields or invalid input data.")
    })
    @PutMapping("/pizzas/{id}")
    public ResponseEntity<Void> updatePizza(@PathVariable Long id, @Valid @RequestBody Pizza newPizza) {
        pizzaService.update(id, newPizza);
        log.info("Pizza Updated");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * Deletes a pizza by ID.
     *
     * @param id the ID of the pizza to delete
     * @return an empty response with status NO_CONTENT if the deletion was successful
     * @throws PizzaNotFoundException if the pizza with the given ID does not exist
     */
    @Operation(summary = "Delete a pizza by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "The pizza was deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pizza not found")
    })
    @DeleteMapping("/pizzas/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable Long id) {

        pizzaService.delete(id);
        log.info("Pizza deleted");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

    }

    /**
     * Creates a new pizzeria.
     *
     * @param pizzeria The {@link Pizzeria} object representing the new pizzeria to be created.
     * @return A {@link ResponseEntity} containing the ID of the new pizzeria and a status code of 201 Created.
     * @throws MethodArgumentNotValidException if the input Pizzeria is not valid
     */
    @Operation(summary = "Create a new pizzeria")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pizzeria created"),
            @ApiResponse(responseCode = "422", description = "Invalid input")
    })
    @PostMapping("/pizzerias")
    public ResponseEntity<Long> createPizzeria(@Valid @RequestBody Pizzeria pizzeria) {
        Long newId = pizzeriaService.create(pizzeria);
        log.info("New pizzeria created");
        return new ResponseEntity<>(newId, HttpStatus.CREATED);
    }

    /**
     * Retrieve a specific pizzeria by its ID.
     *
     * @param pizzeria_id the ID of the pizzeria to retrieve
     * @return a ResponseEntity containing the retrieved pizzeria and an HTTP status code
     * @throws PizzeriaNotFoundException if the specified pizzeria does not exist
     */
    @Operation(summary = "Retrieve a specific pizzeria by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizzeria found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pizzeria.class))}),
            @ApiResponse(responseCode = "404", description = "Pizzeria not found",
                    content = @Content)
    })
    @GetMapping("/pizzerias/{pizzeria_id}")
    public ResponseEntity<Pizzeria> getPizzeriaById(@PathVariable Long pizzeria_id) {
        return new ResponseEntity<>(pizzeriaService.getPizzeriaById(pizzeria_id), HttpStatus.OK);
    }

    /**
     * Retrieve all pizzas available in the given pizzeria.
     *
     * @param pizzeria_id The ID of the pizzeria to retrieve pizzas from.
     * @return ResponseEntity containing a Set of Pizza objects and an HTTP status code.
     * @throws PizzeriaNotFoundException if the specified pizzeria does not exist
     */
    @Operation(summary = "Retrieve all pizzas available in the given pizzeria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved pizzas from pizzeria"),
            @ApiResponse(responseCode = "404", description = "Pizzeria not found")})
    @GetMapping("/pizzerias/{pizzeria_id}/pizzas/")
    public ResponseEntity<Set<Pizza>> getAllPizzasInPizzeria(@PathVariable Long pizzeria_id) {
        return new ResponseEntity<>(
                pizzeriaService.getPizzeriaById(pizzeria_id).getPizzas(), HttpStatus.OK);
    }

    /**
     * Adds the provided set of pizzas to the existing set of pizzas in the specified pizzeria.
     *
     * @param pizzeria_id the ID of the pizzeria
     * @param pizzas      the set of pizzas to add
     * @return a ResponseEntity with no content and a status of OK if the operation was successful
     * @throws PizzeriaNotFoundException    if the specified pizzeria does not exist
     * @throws ConstraintViolationException if set of pizza is unprocessable
     */
    @Operation(summary = "Adds the provided set of pizzas to the existing set of pizzas in the specified pizzeria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizzas added to pizzeria"),
            @ApiResponse(responseCode = "400", description = "Set of pizzas is unprocessable"),
            @ApiResponse(responseCode = "404", description = "Pizzeria not found")
    })
    @PostMapping("/pizzerias/{pizzeria_id}/pizzas")
    public ResponseEntity<Void> addPizzasToPizzeria(@PathVariable Long pizzeria_id, @Valid @RequestBody Set<Pizza> pizzas) {

        pizzeriaService.addPizzas(pizzeria_id, pizzas);
        log.info("Pizzas added to Pizzeria");
        return new ResponseEntity<>(null, HttpStatus.OK);

    }

    /**
     * Removes the specified pizza from the specified pizzeria.
     *
     * @param pizzeria_id the ID of the pizzeria from which to remove the pizza
     * @param pizza_id    the ID of the pizza to remove
     * @return a ResponseEntity with a null body and a status of NO_CONTENT if the pizza was successfully removed,
     * or a status of NOT_FOUND if the pizzeria or pizza was not found
     */
    @Operation(summary = "Removes the specified pizza from the specified pizzeria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza successfully removed from the pizzeria"),
            @ApiResponse(responseCode = "404", description = "Either the pizzeria or the pizza was not found")
    })
    @DeleteMapping("/pizzerias/{pizzeria_id}/pizzas/{pizza_id}")
    public ResponseEntity<Void> deletePizzaFrommPizzeria(
            @PathVariable Long pizza_id,
            @PathVariable Long pizzeria_id) {

        pizzeriaService.deletePizza(pizzeria_id, pizza_id);
        log.info("Pizza deleted from pizzeria");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }


}
