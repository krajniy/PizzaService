package com.telran.pizzaservice.repository;

import com.telran.pizzaservice.entity.Pizzeria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzeriaRepository extends JpaRepository<Pizzeria, Long> {
}
