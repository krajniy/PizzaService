package com.telran.pizzaservice.service;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.entity.Pizzeria;
import com.telran.pizzaservice.exception.PizzaNotFoundException;
import com.telran.pizzaservice.exception.PizzeriaNotFoundException;
import com.telran.pizzaservice.repository.PizzaRepository;
import com.telran.pizzaservice.repository.PizzeriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This class implements the {@link PizzeriaService} interface to provide
 * <p>
 * functionality to manage pizzerias and their pizzas.
 *
 * @author Elena Ivanishcheva
 */
@Service
public class PizzeriaServiceImpl implements PizzeriaService {

    @Autowired
    PizzeriaRepository pizzeriaRepository;

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    PizzaService pizzaService;


    /**
     * Returns a list of all pizzerias in the database with pagination.
     *
     * @param pageable the pagination information
     * @return a list of pizzerias
     * @throws PizzeriaNotFoundException if there are no pizzerias on the given page
     */
    @Override
    @Transactional(readOnly = true)
    public List<Pizzeria> getAllPizzerias(Pageable pageable) {

        Page<Pizzeria> pizzerias = pizzeriaRepository.findAll(pageable);

        if (pizzerias.isEmpty()) {
            throw new PizzeriaNotFoundException("No pizzerias on this page");
        }
        return pizzerias.getContent();
    }


    /**
     * Creates a new pizzeria with the given information, saving it to the database.
     * Any pizzas in the set of pizzas associated with the pizzeria will also be saved to the database
     * if they do not already exist in the database. If they do, they will be retrieved and added to the
     * set of pizzas for the pizzeria.
     *
     * @param pizzeria the pizzeria to create
     * @return the id of the newly created pizzeria
     */
    @Override
    @Transactional
    public Long create(Pizzeria pizzeria) {
        Set<Pizza> pizzas = pizzeria.getPizzas();
        Set<Pizza> savedPizzas = new HashSet<>();

        for (Pizza pizza : pizzas) {
            Pizza newPizza = pizzaRepository.findById(pizzaService.createIfNotExists(pizza)).get();
            savedPizzas.add(newPizza);
        }

        pizzeria.setPizzas(savedPizzas);
        pizzeriaRepository.save(pizzeria);
        return pizzeria.getId();
    }


    /**
     * Retrieves the pizzeria with the given id from the database.
     *
     * @param pizzeriaId the id of the pizzeria to retrieve
     * @return the pizzeria with the given id
     * @throws PizzeriaNotFoundException if no pizzeria with the given id exists in the database
     */
    @Override
    @Transactional(readOnly = true)
    public Pizzeria getPizzeriaById(Long pizzeriaId) {
        return pizzeriaRepository.findById(pizzeriaId).orElseThrow(PizzeriaNotFoundException::new);
    }


    /**
     * Adds the set of pizzas with the given ids to the pizzeria with the given id.
     * Any pizzas in the set that do not already exist in the database will be saved.
     *
     * @param pizzeriaId the id of the pizzeria to add pizzas to
     * @param pizzas     the set of pizzas to add to the pizzeria
     * @throws PizzeriaNotFoundException if no pizzeria with the given id exists in the database
     */
    @Override
    @Transactional
    public void addPizzas(Long pizzeriaId, Set<Pizza> pizzas) {
        Optional<Pizzeria> pizzeriaOptional = pizzeriaRepository.findById(pizzeriaId);
        if (pizzeriaOptional.isPresent()) {
            Pizzeria pizzeria = pizzeriaOptional.get();
            Set<Pizza> existingPizzas = pizzeria.getPizzas();

            pizzas.forEach(p -> {
                Optional<Pizza> pizzaOptional = pizzaRepository.findById(pizzaService.createIfNotExists(p));
                if (pizzaOptional.isPresent()) {
                    Pizza existingPizza = pizzaOptional.get();
                    if (!existingPizzas.contains(existingPizza)) {
                        existingPizzas.add(existingPizza);
                    }
                }
            });
            pizzeriaRepository.save(pizzeria);
        } else {
            throw new PizzeriaNotFoundException(pizzeriaId);
        }
    }


    /**
     * Removes the specified pizza from the pizzeria with the given ID.
     *
     * @param pizzeriaId The ID of the pizzeria to remove the pizza from.
     * @param pizzaId    The ID of the pizza to be removed.
     * @throws PizzeriaNotFoundException if the pizzeria with the given ID does not exist.
     * @throws PizzaNotFoundException    if the pizza with the given ID does not exist in the pizzeria.
     */
    @Override
    @Transactional
    public void deletePizza(Long pizzeriaId, Long pizzaId) {
        Pizzeria pizzeria = getPizzeriaById(pizzeriaId);
        Set<Pizza> pizzas = pizzeria.getPizzas();
        Optional<Pizza> pizzaToRemove = pizzas.stream().filter(pizza -> pizza.getId().equals(pizzaId)).findFirst();
        if (pizzaToRemove.isPresent()) {
            pizzas.remove(pizzaToRemove.get());
            pizzeria.setPizzas(pizzas);
            pizzeriaRepository.save(pizzeria);
        } else {
            throw new PizzaNotFoundException("Pizza not found in pizzeria.");
        }
    }
}
