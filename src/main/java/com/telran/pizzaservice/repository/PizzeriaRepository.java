package com.telran.pizzaservice.repository;

import com.telran.pizzaservice.entity.Pizzeria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * A Spring Data repository interface for managing {@link Pizzeria} entities.
 * <p>
 * Extends {@link org.springframework.data.jpa.repository.JpaRepository} and {@link org.springframework.data.repository.PagingAndSortingRepository}.
 * <p>
 * Provides methods to perform CRUD operations on {@link Pizzeria} entities, as well as pagination and sorting support.
 *
 * @author Elena Ivanishcheva
 */
@Repository
public interface PizzeriaRepository extends JpaRepository<Pizzeria, Long>, PagingAndSortingRepository<Pizzeria, Long> {

    Page<Pizzeria> findAll(Pageable pageable);

}
