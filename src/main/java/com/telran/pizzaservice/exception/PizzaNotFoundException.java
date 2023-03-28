package com.telran.pizzaservice.exception;

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
