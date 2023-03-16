package com.telran.pizzaservice.repository;

import com.telran.pizzaservice.entity.PizzeriaPizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzeriaPizzaRepository extends JpaRepository<PizzeriaPizza, Long> {
}
