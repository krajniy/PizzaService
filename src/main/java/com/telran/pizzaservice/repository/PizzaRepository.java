package com.telran.pizzaservice.repository;

import com.telran.pizzaservice.entity.Pizza;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long>, PagingAndSortingRepository<Pizza, Long> {


    Page<Pizza> findAll(Pageable pageable);

    Optional<Pizza> findByName(String name);
}
