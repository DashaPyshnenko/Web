package com.example.springwebapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Auto {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private Long id;
    private String namee;
    private Integer cost;
    private String descriptionn;
    private String category;
    private String img;
    private String city;

    public Auto(final String namee, final String descriptionn, final Integer cost, final String category, final String img, final String city, Long id) {
        this.namee = namee;
        this.cost = cost;
        this.descriptionn = descriptionn;
        this.category = category;
        this.img = img;
        this.city = city;
        this.id = id;
    }

    public Auto(){}

    public String getName() {
        return namee;
    }

    public void setName(String name) {
        this.namee = namee;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return descriptionn;
    }

    public void setDescription(String descriptionn) {
        this.descriptionn = descriptionn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
