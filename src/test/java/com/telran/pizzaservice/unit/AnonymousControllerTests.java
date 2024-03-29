package com.telran.pizzaservice.unit;

import com.telran.pizzaservice.controller.AdminController;
import com.telran.pizzaservice.controller.AnonymousController;
import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.entity.Pizzeria;
import com.telran.pizzaservice.integration.fixtures.PizzaFixture;
import com.telran.pizzaservice.integration.fixtures.PizzeriaFixture;
import com.telran.pizzaservice.service.PizzaService;
import com.telran.pizzaservice.service.PizzeriaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class AnonymousControllerTests {

    @Mock
    private PizzaService pizzaService;


    @Mock
    private AdminController adminController;

    @InjectMocks
    private AnonymousController anonymousController;

    @Test
    public void testGetAllPizzas() {
        List<Pizza> pizzas = List.of(PizzaFixture.getFixturePizza());
        int page = 0;
        int size = 10;
        Mockito.when(adminController.getAllPizzas(page, size)).thenReturn(new ResponseEntity<>(pizzas, HttpStatus.OK));

        ResponseEntity<List<Pizza>> responseEntity = anonymousController.getAllPizzas(page, size);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(pizzas, responseEntity.getBody());
        Mockito.verify(adminController, Mockito.times(1)).getAllPizzas(page, size);
    }

    @Test
    public void testGetAllPizzerias() {
        List<Pizzeria> pizzerias = List.of(PizzeriaFixture.getFixturePizzeria());
        int page = 0;
        int size = 10;
        ResponseEntity<List<Pizzeria>> responseEntityMock = new ResponseEntity<>(pizzerias, HttpStatus.OK);
        Mockito.when(adminController.getAllPizzerias(page, size)).thenReturn(responseEntityMock);

        ResponseEntity<List<Pizzeria>> responseEntity = anonymousController.getAllPizzerias(page, size);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(pizzerias, responseEntity.getBody());
        Mockito.verify(adminController, Mockito.times(1)).getAllPizzerias(page, size);
    }

    @Test
    public void testGetPizzaByName() {
        String pizzaName = "test_pizza";
        Pizza pizza = PizzaFixture.getFixturePizza();
        Mockito.when(pizzaService.findByName(pizzaName)).thenReturn(pizza);

        ResponseEntity<Pizza> responseEntity = anonymousController.getPizzaByName(pizzaName);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(pizza, responseEntity.getBody());
        Mockito.verify(pizzaService, Mockito.times(1)).findByName(pizzaName);
    }

    @Test
    public void testGetAllPizzasInPizzeria() {
        Long pizzeriaId = 1L;
        Set<Pizza> pizzas = Set.of(PizzaFixture.getFixturePizza());

        Mockito.when(adminController.getAllPizzasInPizzeria(pizzeriaId)).thenReturn(new ResponseEntity<>(pizzas, HttpStatus.OK));

        ResponseEntity<Set<Pizza>> responseEntity = anonymousController.getAllPizzasInPizzeria(pizzeriaId);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(pizzas, responseEntity.getBody());
        Mockito.verify(adminController, Mockito.times(1)).getAllPizzasInPizzeria(pizzeriaId);
    }

}
