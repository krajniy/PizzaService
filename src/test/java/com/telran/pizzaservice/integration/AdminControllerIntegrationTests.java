package com.telran.pizzaservice.integration;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.entity.Pizzeria;
import com.telran.pizzaservice.integration.fixtures.PizzaFixture;
import com.telran.pizzaservice.integration.fixtures.PizzeriaFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
public class AdminControllerIntegrationTests extends IntegrationTestsInfrastructureInitializer {

    @Test
    @DisplayName("GET all pizzas")
    @Transactional(readOnly = true)
    void testGetAllPizzas() throws Exception {
        mockMvc.perform(get("/admin/pizzas")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @DisplayName("GET all pizzerias")
    @Transactional(readOnly = true)
    void testGetAllPizzerias() throws Exception {
        mockMvc.perform(get("/admin/pizzerias")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @DisplayName("GET all pizzas with pagination")
    @Transactional(readOnly = true)
    void testGetAllPizzasWithPagination() throws Exception {
        mockMvc.perform(get("/admin/pizzas?page=0&size=2")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET all pizzerias with pagination")
    @Transactional(readOnly = true)
    void testGetAllPizzeriasWithPagination() throws Exception {
        mockMvc.perform(get("/admin/pizzerias?page=0&size=2")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET without auth")
    @Transactional(readOnly = true)
    void testGetWithoutAuth() throws Exception {
        mockMvc.perform(get("/admin/pizzas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET pizza by id")
    @Transactional(readOnly = true)
    void testGetPizzaById() throws Exception {
        Pizza pizza = PizzaFixture.getFixturePizza();
        Long pizzaId = pizzaService.createIfNotExists(pizza);

        mockMvc.perform(get("/admin/pizzas/{id}", pizzaId)
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value("test_pizza"))
                .andExpect(jsonPath("$.description").value("test_description"))
                .andExpect(jsonPath("$.imgUrl").value("test_url"))
                .andExpect(jsonPath("$.price").value(10.0));
    }


    @Test
    @DisplayName("GET Pizzeria by id")
    @Transactional(readOnly = true)
    void testGetPizzeriaById() throws Exception {

        Pizzeria pizzeria = PizzeriaFixture.getFixturePizzeria();
        Long pizzeriaId = pizzeriaService.create(pizzeria);

        mockMvc.perform(get("/admin/pizzerias/{id}", pizzeriaId)
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value("TestPizzeria"))
                .andExpect(jsonPath("$.address").value("TestPizzeriaAddress"));
    }


    @Test
    @DisplayName("GET all pizzas in pizzeria by pizzeria_id")
    @Transactional(readOnly = true)
    void testGetAllPizzasInPizzeria() throws Exception {

        Pizzeria pizzeria = PizzeriaFixture.getFixturePizzeria();

        pizzeriaService.create(pizzeria);
        pizzeriaService.addPizzasByIds(pizzeria.getId(), Set.of(1L, 2L));


        mockMvc.perform(get("/admin/pizzerias/" + pizzeria.getId() + "/pizzas/")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("CREATE new pizza")
    @Transactional()
    @Rollback()
    void testCreatePizza() throws Exception {
        String jsonRequest = "{\"name\": \"test\", \"description\": \"test\", \"price\": 14.99, \"basePrice\": 14.99, \"imgUrl\": \"test\"}";
        mockMvc.perform(post("/admin/pizzas")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNumber());
    }


    @Test
    @DisplayName("UPDATE pizza by id")
    @Transactional()
    @Rollback()
    void testUpdatePizzaWithValidData() throws Exception {
        Pizza pizza = PizzaFixture.getFixturePizza();

        Long pizzaId = pizzaService.createIfNotExists(pizza);

        Pizza newPizza = new Pizza();
        newPizza.setName("Veganisch");
        newPizza.setPrice(10.0);
        newPizza.setImgUrl("url");
        newPizza.setDescription("This is test pizza");

        mockMvc.perform(put("/admin/pizzas/{id}", pizzaId)
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPizza)))
                .andDo(print())
                .andExpect(status().isOk());

        Pizza updated = pizzaService.get(pizzaId);

        Assertions.assertEquals("Veganisch", updated.getName());
        Assertions.assertEquals("url", updated.getImgUrl());
    }

    @Test
    @DisplayName("DELETE pizza by id")
    @Transactional()
    @Rollback()
    void testDeletePizza() throws Exception {
        Pizza pizza = new Pizza();
        pizza.setName("Vegan");
        pizza.setPrice(10.0);
        pizza.setImgUrl("url");
        pizza.setDescription("This is test pizza");

        Long pizzaId = pizzaService.createIfNotExists(pizza);

        mockMvc.perform(delete("/admin/pizzas/{id}", pizzaId)
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("CREATE new pizzeria")
    @Transactional()
    @Rollback()
    void testCreatePizzeria() throws Exception {

        String jsonRequest = "{\"name\": \"TestPizzeriaWithoutPizzas\", \"address\": \"TestPizzeriaAddressWithoutPizzas\"}";

        mockMvc.perform(post("/admin/pizzerias")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNumber());
    }


    @Test
    @DisplayName("ADD pizzas to pizzeria")
    @Transactional()
    @Rollback()
    void testAddPizzasToPizzeria() throws Exception {
        Pizzeria pizzeria = PizzeriaFixture.getFixturePizzeria();

        String jsonRequest = "[{\"name\": \"Vegan1\", \"description\": \"test pizza 1\", \"price\": \"10.0\"}," +
                                "{\"name\": \"Vegan2\", \"description\": \"test pizza 2\", \"price\": \"10.0\"}]";

        pizzeriaService.create(pizzeria);

        mockMvc.perform(post("/admin/pizzerias/" + pizzeria.getId() + "/pizzas")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("DELETE pizza from pizzeria")
    @Transactional()
    @Rollback()
    void deletePizzaFrommPizzeria() throws Exception {
        Pizzeria pizzeria = PizzeriaFixture.getFixturePizzeria() ;

        Pizza pizza1 = pizzaService.get(1L);
        Pizza pizza2 = pizzaService.get(2L);

        pizzeriaService.create(pizzeria);
        pizzeriaService.addPizzasByIds(pizzeria.getId(), Set.of(pizza1.getId(), pizza2.getId()));

        mockMvc.perform(delete("/admin/pizzerias/" + pizzeria.getId() + "/pizzas/" + pizza1.getId())
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

}
