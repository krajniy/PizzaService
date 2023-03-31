package com.telran.pizzaservice.exception;

/**
 * This exception is thrown when a requested pizza is not found.
 *
 * @author Elena Ivanishcheva
 */
public class PizzaNotFoundException extends RuntimeException {
    public PizzaNotFoundException(Long id) {
        super("Pizza with id " + id + " not found.");
    }

    public PizzaNotFoundException() {
        super("Pizza not found.");
    }

    public PizzaNotFoundException(String s) {
        super(s);
    }
}
