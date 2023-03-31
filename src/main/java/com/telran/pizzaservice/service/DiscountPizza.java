package com.telran.pizzaservice.service;

import com.telran.pizzaservice.entity.Pizza;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The DiscountPizza class implements the scheduled task of discounting pizzas during lunch time
 * <p>
 * and resetting the prices back to their initial values afterwards.
 * <p>
 * This class uses the PizzaService class to access and update pizza prices.
 *
 * @author Elena Ivanishcheva
 */

@Slf4j
@Component
public class DiscountPizza {

    @Autowired
    PizzaService pizzaService;

    /**
     * This method is used to discount the prices of pizzas between 12-15 hours, Monday to Friday.
     * It gets the list of all pizzas from the PizzaService and updates the prices by reducing 10% from the base price.
     * If base price is null, it sets it to the current price of the pizza.
     * Finally, it logs the price changes for each pizza and updates the database via the PizzaService.
     */
    @Scheduled(cron = "0 0 12-15 * * MON-FRI", zone = "Europe/Berlin")
//    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void discount() {
        List<Pizza> pizzas = pizzaService.getAllPizzas(PageRequest.of(0, 10));
        for (Pizza pizza : pizzas) {
            if (pizza.getBasePrice() == null) {
                pizza.setBasePrice(pizza.getPrice());
            }
            pizza.setPrice(pizza.getBasePrice() * 0.9); // price reduce
            log.info("Price changed for Pizza" + pizza.getId());
            pizzaService.update(pizza.getId(), pizza);
        }
    }

    /**
     * This method is used to reset the prices of pizzas between 0-11 and 16-23 hours, Monday to Friday.
     * It gets the list of all pizzas from the PizzaService and sets the prices back to their initial values.
     * If base price is null, it sets it to the current price of the pizza.
     * Finally, it logs the price resets for each pizza and updates the database via the PizzaService.
     */
    @Scheduled(cron = "0 0 0-11,16-23 * * MON-FRI", zone = "Europe/Berlin")
    public void resetPrice() {
        List<Pizza> pizzas = pizzaService.getAllPizzas(PageRequest.of(0, 10));
        for (Pizza pizza : pizzas) {
            if (pizza.getBasePrice() == null) {
                pizza.setBasePrice(pizza.getPrice());
            }
            pizza.setPrice(pizza.getBasePrice()); // reset base price
            log.info("Price reset for Pizza" + pizza.getId());
            pizzaService.update(pizza.getId(), pizza);
        }
    }
}
