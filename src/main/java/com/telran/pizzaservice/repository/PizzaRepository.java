package com.telran.pizzaservice.repository;

import com.telran.pizzaservice.entity.Pizza;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The PizzaRepository interface provides methods for accessing and manipulating pizza data from the database.
 * <p>
 * It extends the JpaRepository and PagingAndSortingRepository interfaces, providing methods for basic CRUD operations.
 *
 * @author Elena Ivanishcheva
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see org.springframework.data.repository.PagingAndSortingRepository
 */
@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long>, PagingAndSortingRepository<Pizza, Long> {


    Page<Pizza> findAll(Pageable pageable);

    Optional<Pizza> findByName(String name);
}
