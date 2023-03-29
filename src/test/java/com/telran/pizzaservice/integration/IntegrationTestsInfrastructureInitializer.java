package com.telran.pizzaservice.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.pizzaservice.service.PizzaService;
import com.telran.pizzaservice.service.PizzeriaService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public abstract class IntegrationTestsInfrastructureInitializer {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected PizzaService pizzaService;

    @Autowired
    protected PizzeriaService pizzeriaService;

}
