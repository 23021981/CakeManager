package com.waracle.cakemanager.service;

import com.waracle.cakemanager.model.Cake;
import com.waracle.cakemanager.repository.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* Author : Atul Kumar
* */
@Service
public class CakeServiceImpl implements CakeService{

    @Autowired
    private CakeRepository cakeRepository;

    @Override
    public List<Cake> getAllCakes() {
        return cakeRepository.findAll();
    }

    @Override
    public Cake getCake(String cakeId) {
        return cakeRepository.findByCakeId(cakeId);
    }

    @Override
    public Cake addCake(Cake cake) {
        return cakeRepository.save(cake);
    }
}
