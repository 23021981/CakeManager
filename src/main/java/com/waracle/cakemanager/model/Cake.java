package com.waracle.cakemanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
* Author : Atul Kumar
* It holds the cake entity value*/

@Entity
@Table(name= "CAKE")
public class Cake {

    @Id
    @Column
    private String cakeId;
    @Column
    private String cakeName;
    @Column
    private String cakeDesc;
    @Column
    private String imageUrl;

    public String getCakeId() {
        return cakeId;
    }

    public void setCakeId(String cakeId) {
        this.cakeId = cakeId;
    }

    public String getCakeName() {
        return cakeName;
    }

    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }

    public String getCakeDesc() {
        return cakeDesc;
    }

    public void setCakeDesc(String cakeDesc) {
        this.cakeDesc = cakeDesc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
