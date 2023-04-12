package com.telran.pizzaservice.service.exception;
/**
 * This exception is thrown when a requested pizzeria is not found.
 *
 * @author Elena Ivanishcheva
 */
public class PizzeriaNotFoundException extends RuntimeException {
    public PizzeriaNotFoundException(Long id) {
        super("Pizzeria with id " + id + " not found.");
    }

    public PizzeriaNotFoundException() {
        super("Pizzeria not found.");
    }

    public PizzeriaNotFoundException(String s) {
        super(s);
    }
}
