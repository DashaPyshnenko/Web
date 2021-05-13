package com.example.springwebapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_buy;
    private String city_user;
    private Date date;
    private Long id_Product;
    private Integer count;

    public Checkout(){}

    public Checkout(String city_user, Date date, Long id_Product, Integer count){
        this.id_buy = id_buy;
        this.city_user = city_user;
        this.date = date;
        this.id_Product = id_Product;
        this.count = count;
    }

    public Long getId_buy() {
        return id_buy;
    }

    public void setId_buy(Long id_buy) {
        this.id_buy = id_buy;
    }

    public String getCity_user() {
        return city_user;
    }

    public void setCity_user(String city_user) {
        this.city_user = city_user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId_Product() {
        return id_Product;
    }

    public void setId_Product(Long id_Product) {
        this.id_Product = id_Product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
