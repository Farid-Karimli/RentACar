package com.example.sampleproject;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

/* Table that stores the Reviews created along with their information */
@Entity
@Table(name="reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK of the table

    @Column(name="userID",nullable = false)
    private Long userID; // The ID of the user that posted the review

    @Column(name="vehicleID",nullable = false)
    private Long vehicleID; // The ID of the vehicle for which the review was posted

    @Column(name="rating",nullable = false)
    private Integer rating; // Rating the user gave for the vehicle

    @Column(name="description",nullable = false, length = 200)
    private String desc; // Supporting text for the rating

    @Column(name="user_name", nullable = true)
    private String name; // Name of the user, could be anonymous

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
