package com.telran.pizzaservice.exception;

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
