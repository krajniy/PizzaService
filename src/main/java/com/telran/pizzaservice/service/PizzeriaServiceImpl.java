package com.telran.pizzaservice.service;

import com.telran.pizzaservice.entity.Pizzeria;
import com.telran.pizzaservice.repository.PizzeriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzeriaServiceImpl implements PizzeriaService{

    @Autowired
    PizzeriaRepository pizzeriaRepository;
    @Override
    public List<Pizzeria> getAllPizzerias() {
        return pizzeriaRepository.findAll();
    }
}
