package com.telran.pizzaservice.service;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.service.exception.PizzaNotFoundException;
import com.telran.pizzaservice.repository.PizzaRepository;
import com.telran.pizzaservice.service.exception.UnprocessableEntityException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link PizzaService} interface that uses the {@link PizzaRepository} to
 * <p>
 * perform CRUD operations on {@link Pizza} entities.
 *
 * @author Elena Ivanishcheva
 */
@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;

    /**
     * Retrieves all pizzas with paging from the database.
     *
     * @param pageable the paging information
     * @return a list of pizzas on the requested page or an empty list
     */
    @Override
    @Transactional(readOnly = true)
    public List<Pizza> getAllPizzas(Pageable pageable) {
//        Page<Pizza> pizzas = pizzaRepository.findAll(pageable);
//        if (pizzas.isEmpty()) {
//            throw new PizzaNotFoundException("No pizzas found on this page");
//        }
//        return pizzas.getContent();
        return pizzaRepository.findAll(pageable).getContent();
    }

    /**
     * Creates a new pizza if it does not already exist in the database.
     *
     * @param pizza the pizza to create
     * @return the id of the existing or newly created pizza
     * @throws UnprocessableEntityException if the pizza entity contains an id
     */
    @Override
    @Transactional()
    public Long createIfNotExists(@Valid Pizza pizza) {
        if (pizza.getId() != null) {
            throw new UnprocessableEntityException("Pizza id must not be specified for creation");
        }
        Optional<Pizza> existingPizza = pizzaRepository.findByName(pizza.getName());
        if (existingPizza.isPresent()) {
            return existingPizza.get().getId();
        } else {
            if (pizza.getBasePrice() == null) {
                pizza.setBasePrice(pizza.getPrice());
            }
            return pizzaRepository.save(pizza).getId();
        }
    }

    /**
     * Finds a pizza by its name.
     *
     * @param pizzaName the name of the pizza to find
     * @return the pizza with the given name
     * @throws PizzaNotFoundException if the pizza is not found
     */
    @Override
    @Transactional(readOnly = true)
    public Pizza findByName(String pizzaName) {
        return pizzaRepository.findByName(pizzaName).orElseThrow(PizzaNotFoundException::new);
    }

    /**
     * Retrieves a pizza by its id.
     *
     * @param id the id of the pizza to retrieve
     * @return the pizza with the given id
     * @throws PizzaNotFoundException if the pizza is not found
     */
    @Override
    @Transactional(readOnly = true)
    public Pizza get(Long id) {

        return pizzaRepository.findById(id).orElseThrow(PizzaNotFoundException::new);
    }

    /**
     * Updates an existing pizza in the database.
     *
     * @param id       the id of the pizza to update
     * @param newPizza the updated pizza object
     * @throws PizzaNotFoundException if the pizza is not found
     */
    @Override
    @Transactional()
    public void update(Long id, Pizza newPizza) {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(PizzaNotFoundException::new);
        if (newPizza.getId() == null) {

            pizza.setName(newPizza.getName());
            pizza.setDescription(newPizza.getDescription());
            pizza.setPrice(newPizza.getPrice());
//        pizza.setPizzerias(newPizza.getPizzerias());
            pizza.setImgUrl(newPizza.getImgUrl());
            if (newPizza.getBasePrice() != null) {
                pizza.setBasePrice(newPizza.getBasePrice());
            } else {
                pizza.setBasePrice(newPizza.getPrice());
            }
        } else {


            if (!id.equals(newPizza.getId())) {
                throw new IllegalArgumentException("Cannot update pizza with different id");
            }

            pizzaRepository.save(newPizza);
        }

    }

    /**
     * Deletes a pizza from the database.
     *
     * @param id the id of the pizza to delete
     * @throws PizzaNotFoundException if the pizza is not found
     */
    @Override
    @Transactional()
    public void delete(Long id) {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(PizzaNotFoundException::new);
        pizzaRepository.delete(pizza);
    }
}
