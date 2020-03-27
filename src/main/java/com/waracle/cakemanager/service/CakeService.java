package com.waracle.cakemanager.service;

import com.waracle.cakemanager.model.Cake;

import java.util.List;
/*
* Author : Atul Kumar
* */
public interface CakeService {

    public List<Cake> getAllCakes();
    public Cake getCake(String cakeId);
    public Cake addCake(Cake cake);
}
