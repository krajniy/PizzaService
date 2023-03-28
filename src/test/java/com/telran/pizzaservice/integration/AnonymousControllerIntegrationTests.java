package com.telran.pizzaservice.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
public class AnonymousControllerIntegrationTests extends IntegrationTestsInfrastructureInitializer {


    @Test
    @DisplayName("Получение списка всех пицц")
    void testGetAllPizzas() throws Exception {
        mockMvc.perform(get("/guest/pizzas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @DisplayName("Получение списка всех пиццерий")
    void testGetAllPizzerias() throws Exception {
        mockMvc.perform(get("/guest/pizzerias")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @DisplayName("Получение пиццы по имени")
    void testGetPizzaByName() throws Exception {
        mockMvc.perform(get("/guest/pizzas/{name}", "Margherita")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Margherita"));
    }

    @Test
    @DisplayName("Получение списка пицц в пиццерии")
    void testGetAllPizzasInPizzeria() throws Exception {
        mockMvc.perform(get("/guest/pizzerias/{id}/pizzas/", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
