package com.telran.pizzaservice.exception;

import jakarta.validation.ConstraintViolationException;
import org.jboss.logging.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PizzaNotFoundException.class)
    protected ResponseEntity<ProblemDetail> handlePizzaNotFound(PizzaNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Pizza not found");
        problemDetail.setType(URI.create("https://api.documents.com/errors/pizza_not_found"));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", MDC.get("traceId"));
        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PizzeriaNotFoundException.class)
    protected ResponseEntity<ProblemDetail> handlePizzeriaNotFound(PizzeriaNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Pizzeria not found");
        problemDetail.setType(URI.create("https://api.documents.com/errors/pizzeria_not_found"));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", MDC.get("traceId"));
        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        problemDetail.setTitle(e.getMessage());
        problemDetail.setDetail(Arrays.toString(errors.toArray()));
        problemDetail.setType(URI.create("https://api.documents.com/errors/unprocessable_entity"));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", org.slf4j.MDC.get("traceId"));
        return new ResponseEntity<>(problemDetail, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException e) {
        List<String> errors = e.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Validation error");
        problemDetail.setDetail(errors.toString());
        problemDetail.setType(URI.create("https://api.documents.com/errors/validation_error"));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", MDC.get("traceId"));
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }



}
