package com.telran.pizzaservice.integration.fixtures;

import com.telran.pizzaservice.entity.Pizzeria;


public class PizzeriaFixture {


    public static Pizzeria getFixturePizzeria() {
        Pizzeria pizzeria = new Pizzeria();

        pizzeria.setName("TestPizzeria");
        pizzeria.setAddress("TestPizzeriaAddress");
        return pizzeria;
    }
}
