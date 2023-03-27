package com.telran.pizzaservice.repository;

import com.telran.pizzaservice.entity.Pizzeria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzeriaRepository extends JpaRepository<Pizzeria, Long>, PagingAndSortingRepository<Pizzeria, Long> {

    Page<Pizzeria> findAll(Pageable pageable);

}
