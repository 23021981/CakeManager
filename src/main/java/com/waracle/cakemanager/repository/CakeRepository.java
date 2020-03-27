package com.waracle.cakemanager.repository;

import com.waracle.cakemanager.model.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
* Author : Atul Kumar*/
@Repository
public interface CakeRepository extends JpaRepository<Cake, Long> {

    public Cake findByCakeId(String cakeId);
}
