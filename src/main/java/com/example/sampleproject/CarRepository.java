package com.example.sampleproject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* A JpaRepository has pre-defined methods for executing SQL queries on the database
   You can add your own, like the one below. It comes with its own as well, like findAll().
   This is an INTERFACE, not a class.
   public <return type> findCarBy<insert attribute name here>(<attribute type> attribute) is the general format for a method.
*/
public interface CarRepository extends JpaRepository<Car,Long> {
    public List<Car> findCarByManufacturer(String manufacturer);
}
