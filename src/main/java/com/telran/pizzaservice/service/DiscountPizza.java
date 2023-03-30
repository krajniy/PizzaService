package com.telran.pizzaservice.service;

import com.telran.pizzaservice.entity.Pizza;
import com.telran.pizzaservice.repository.PizzaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DiscountPizza {

    @Autowired
    PizzaService pizzaService;


    @Scheduled(cron = "0 0 12-15 * * MON-FRI", zone = "Europe/Berlin")
//    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void discount() {
        List<Pizza> pizzas = pizzaService.getAllPizzas(PageRequest.of(0, 10));
        for (Pizza pizza : pizzas) {
            if (pizza.getBasePrice() == null) {
                pizza.setBasePrice(pizza.getPrice());
            }
            pizza.setPrice(pizza.getBasePrice() * 0.9); // скидка
            log.info("Price changed for Pizza" + pizza.getId());
            pizzaService.update(pizza.getId(), pizza);
        }
    }

    @Scheduled(cron = "0 0 0-11,16-23 * * MON-FRI", zone = "Europe/Berlin")
    public void resetPrice() {
        List<Pizza> pizzas = pizzaService.getAllPizzas(PageRequest.of(0, 10));
        for (Pizza pizza : pizzas) {
            if (pizza.getBasePrice() == null) {
                pizza.setBasePrice(pizza.getPrice());
            }
            pizza.setPrice(pizza.getBasePrice()); // возвращаем изначальную цену
            log.info("Price reset for Pizza" + pizza.getId());
            pizzaService.update(pizza.getId(), pizza);
        }
    }
}
