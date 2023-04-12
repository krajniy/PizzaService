package com.telran.pizzaservice.service.exception;
/**
 * This exception is thrown when provided data is unprocessable.
 *
 * @author Elena Ivanishcheva
 */
public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException(String message) {
        super(message);
    }

    public UnprocessableEntityException() {
        super("Invalid data");
    }
}
