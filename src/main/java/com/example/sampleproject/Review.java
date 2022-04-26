package com.example.sampleproject;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name="reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="userID",nullable = false)
    private Long userID;

    @Column(name="vehicleID",nullable = false)
    private Long vehicleID;

    @Column(name="rating",nullable = false)
    private Integer rating;

    @Column(name="description",nullable = false, length = 200)
    private String desc;

    @Column(name="user_name", nullable = true)
    private String name;

    public Long getId() {
        return id;
    }

    public Integer getRating() {
        return rating;
    }

    public String getDesc() {
        return desc;
    }

    public Long getUserID() {
        return userID;
    }

    public Long getVehicleID() {
        return vehicleID;
    }

    public String getName() {
        return name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setVehicleID(Long vehicleID) {
        this.vehicleID = vehicleID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
