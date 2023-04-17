package com.telran.pizzaservice.integration.fixtures;

import com.telran.pizzaservice.entity.Pizza;


public class PizzaFixture {


    public static Pizza getFixturePizza() {
        Pizza pizza = new Pizza();
        pizza.setName("test_pizza");
        pizza.setPrice(10.0);
        pizza.setImgUrl("test_url");
        pizza.setDescription("test_description");
        return pizza;
    }
}
