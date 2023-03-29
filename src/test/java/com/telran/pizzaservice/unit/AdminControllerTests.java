package com.telran.pizzaservice.unit;

import com.telran.pizzaservice.controller.AdminController;
import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.entity.Pizzeria;
import com.telran.pizzaservice.service.PizzaService;
import com.telran.pizzaservice.service.PizzeriaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTests {

    @Mock
    private PizzaService pizzaService;

    @Mock
    private PizzeriaService pizzeriaService;

    @InjectMocks
    private AdminController adminController;

    @Test
    public void testGetAllPizzas() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza());
        Mockito.when(pizzaService.getAllPizzas(PageRequest.of(0, 10))).thenReturn(pizzas);
        ResponseEntity<List<Pizza>> responseEntity = adminController.getAllPizzas(0, 10);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(pizzas, responseEntity.getBody());
    }

    @Test
    public void testGetAllPizzerias() {
        List<Pizzeria> pizzerias = new ArrayList<>();
        pizzerias.add(new Pizzeria());
        Mockito.when(pizzeriaService.getAllPizzerias(PageRequest.of(0, 10))).thenReturn(pizzerias);
        ResponseEntity<List<Pizzeria>> responseEntity = adminController.getAllPizzerias(0, 10);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(pizzerias, responseEntity.getBody());
    }

    @Test
    public void testCreatePizza() {
        Pizza pizza = new Pizza(1L, "name", "description", 10.0, "url",
                Set.of(new Pizzeria()));
        Mockito.when(pizzaService.createIfNotExists(pizza)).thenReturn(1L);

        ResponseEntity<Long> responseEntity = adminController.createPizza(pizza);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertEquals(Long.valueOf(1L), responseEntity.getBody());
    }

    @Test
    public void testGetPizza() {
        Pizza pizza = new Pizza(1L, "name", "description", 10.0, "url",
                Set.of(new Pizzeria()));

        Mockito.when(pizzaService.get(1L)).thenReturn(pizza);

        ResponseEntity<Pizza> responseEntity = adminController.getPizza(1L);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(pizza, responseEntity.getBody());

    }

    @Test
    public void testUpdatePizza() {
        Pizza pizza = new Pizza(1L, "name", "description", 10.0, "url",
                Set.of(new Pizzeria()));
        Mockito.doNothing().when(pizzaService).update(1L, pizza);

        ResponseEntity<Void> responseEntity = adminController.updatePizza(1L, pizza);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNull(responseEntity.getBody());

    }

    @Test
    public void testDeletePizza() {
        Mockito.doNothing().when(pizzaService).delete(1L);

        ResponseEntity<Void> responseEntity = adminController.deletePizza(1L);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        Assert.assertNull(responseEntity.getBody());
    }

    @Test
    public void testCreatePizzeria() {
        Pizzeria pizzeria = new Pizzeria(1L, "Pizzeria1", "Address1", Set.of());
        Mockito.when(pizzeriaService.create(pizzeria)).thenReturn(1L);

        ResponseEntity<Long> responseEntity = adminController.createPizzeria(pizzeria);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertEquals(Long.valueOf(1L), responseEntity.getBody());
    }

    @Test
    public void testGetPizzeriaById() {
        Pizzeria pizzeria = new Pizzeria(1L, "Pizzeria1", "Address1", Set.of());
        Mockito.when(pizzeriaService.getPizzeriaById(1L)).thenReturn(pizzeria);

        ResponseEntity<Pizzeria> responseEntity = adminController.getPizzeriaById(1L);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(pizzeria, responseEntity.getBody());

    }

    @Test
    public void testGetAllPizzasInPizzeria() {
        Set<Pizza> pizzas = Set.of(
                new Pizza(1L, "name", "description", 10.0, "url",
                        Set.of(new Pizzeria())),
                new Pizza(2L, "name1", "description1", 9.0, "url1",
                        Set.of(new Pizzeria())));
        Pizzeria pizzeria = new Pizzeria(1L, "Pizzeria1", "Address1", pizzas);

        Mockito.when(pizzeriaService.getPizzeriaById(1L)).thenReturn(pizzeria);

        ResponseEntity<Set<Pizza>> responseEntity = adminController.getAllPizzasInPizzeria(1L);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(pizzas, responseEntity.getBody());
    }

    @Test
    public void testAddPizzasToPizzeria() {
        Set<Pizza> pizzas = Set.of(
                new Pizza(1L, "name", "description", 10.0, "url",
                        Set.of(new Pizzeria())),
                new Pizza(2L, "name1", "description1", 9.0, "url1",
                        Set.of(new Pizzeria())));
        Pizzeria pizzeria = new Pizzeria(1L, "Pizzeria1", "Address1", Set.of());

        Mockito.doNothing().when(pizzeriaService).addPizzas(1L, pizzas);

        ResponseEntity<Void> responseEntity = adminController.addPizzasToPizzeria(1L, pizzas);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNull(responseEntity.getBody());
        Mockito.verify(pizzeriaService, Mockito.times(1)).addPizzas(1L, pizzas);
    }

    @Test
    public void testDeletePizzaFrommPizzeria() {
        Mockito.doNothing().when(pizzeriaService).deletePizza(1L, 1L);

        ResponseEntity<Void> responseEntity = adminController.deletePizzaFrommPizzeria(1L, 1L);

        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        Assert.assertNull(responseEntity.getBody());
        Mockito.verify(pizzeriaService, Mockito.times(1)).deletePizza(1L, 1L);
    }

}
