package com.telran.pizzaservice.integration;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.entity.Pizzeria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;


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
    void testGetWithoutAuth() throws Exception {
        mockMvc.perform(get("/admin/pizzas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("CREATE new pizza")
    void testCreatePizza() throws Exception {
        String jsonRequest = "{\"name\": \"test\", \"description\": \"test\", \"price\": 14.99, \"imgUrl\": \"test\"}";
        mockMvc.perform(post("/admin/pizzas")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNumber())
                .andExpect(jsonPath("$").value(4));
    }

    @Test
    @DisplayName("GET pizza by id")
    void testGetPizzaById() throws Exception {
        Pizza pizza = new Pizza();
        pizza.setName("Vegan");
        pizza.setPrice(10.0);
        pizza.setImgUrl("url");
        pizza.setDescription("This is test pizza");

        Long pizzaId = pizzaService.createIfNotExists(pizza);

        mockMvc.perform(get("/admin/pizzas/{id}", pizzaId)
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Vegan"))
                .andExpect(jsonPath("$.description").value("This is test pizza"))
                .andExpect(jsonPath("$.imgUrl").value("url"))
                .andExpect(jsonPath("$.price").value(10.0));
    }

    @Test
    @DisplayName("UPDATE pizza by id")
    void testUpdatePizzaWithValidData() throws Exception {
        Pizza pizza = new Pizza();
        pizza.setName("Vegan");
        pizza.setPrice(10.0);
        pizza.setImgUrl("url");
        pizza.setDescription("This is test pizza");

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
    void testCreatePizzeria() throws Exception {
        String jsonRequest = "{\"name\": \"testWithourPizzas\", \"address\": \"testWithourPizzas\"}";
        mockMvc.perform(post("/admin/pizzerias")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNumber())
                .andExpect(jsonPath("$").value(4));
    }

    @Test
    @DisplayName("GET Pizzeria by id")
    void testGetPizzeriaById() throws Exception {
        Pizzeria pizzeria = new Pizzeria();
        pizzeria.setName("TestPizzeria");
        pizzeria.setAddress("TestPizzeriaAddress");

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
    void testGetAllPizzasInPizzeria() throws Exception {

        Pizzeria pizzeria = new Pizzeria();
        pizzeria.setName("TestPizzeriaWithPizzas");
        pizzeria.setAddress("TestPizzeriaAddressWithPizzas");

        Pizza pizza1 = new Pizza();
        pizza1.setName("Vegan1");
        pizza1.setPrice(10.0);
        pizza1.setImgUrl("url");
        pizza1.setDescription("This is test pizza");
        pizzaService.createIfNotExists(pizza1);

        Pizza pizza2 = new Pizza();
        pizza2.setName("Vegan2");
        pizza2.setPrice(10.0);
        pizza2.setImgUrl("url");
        pizza2.setDescription("This is test pizza");
        pizzaService.createIfNotExists(pizza2);

        pizzeria.setPizzas(Set.of(pizza1, pizza2));

        pizzeriaService.create(pizzeria);

        mockMvc.perform(get("/admin/pizzerias/" + pizzeria.getId() + "/pizzas/")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    @DisplayName("ADD pizzas to pizzeria")
    void testAddPizzasToPizzeria() throws Exception {
        Pizzeria pizzeria = new Pizzeria();
        pizzeria.setName("TestPizzeriaWithPizzas");
        pizzeria.setAddress("TestPizzeriaAddressWithPizzas");

        Pizza pizza1 = new Pizza();
        pizza1.setName("Vegan1");
        pizza1.setPrice(10.0);
        pizza1.setImgUrl("url");
        pizza1.setDescription("This is test pizza");
        pizzaService.createIfNotExists(pizza1);

        Pizza pizza2 = new Pizza();
        pizza2.setName("Vegan2");
        pizza2.setPrice(10.0);
        pizza2.setImgUrl("url");
        pizza2.setDescription("This is test pizza");
        pizzaService.createIfNotExists(pizza2);

        Set<Pizza> pizzaSet = Set.of(pizza1, pizza2);

        pizzeriaService.create(pizzeria);

        mockMvc.perform(post("/admin/pizzerias/" + pizzeria.getId() + "/pizzas")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pizzaSet)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("DELETE pizza from pizzeria")
    void deletePizzaFrommPizzeria() throws Exception {
        Pizzeria pizzeria = new Pizzeria();
        pizzeria.setName("TestPizzeriaWithPizzas");
        pizzeria.setAddress("TestPizzeriaAddressWithPizzas");

        Pizza pizza1 = new Pizza();
        pizza1.setName("Vegan1");
        pizza1.setPrice(10.0);
        pizza1.setImgUrl("url");
        pizza1.setDescription("This is test pizza");
        pizzaService.createIfNotExists(pizza1);

        Pizza pizza2 = new Pizza();
        pizza2.setName("Vegan2");
        pizza2.setPrice(10.0);
        pizza2.setImgUrl("url");
        pizza2.setDescription("This is test pizza");
        pizzaService.createIfNotExists(pizza2);

        Set<Pizza> pizzaSet = new HashSet<>();
        pizzaSet.add(pizza1);
        pizzaSet.add(pizza2);
        pizzeria.setPizzas(pizzaSet);

        pizzeriaService.create(pizzeria);

        mockMvc.perform(delete("/admin/pizzerias/" + pizzeria.getId() + "/pizzas/" + pizza1.getId())
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

}
