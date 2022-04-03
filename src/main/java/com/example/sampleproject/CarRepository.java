package com.example.sampleproject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/* A JpaRepository has pre-defined methods for executing SQL queries on the database
   You can add your own, like the one below. It comes with its own as well, like findAll().
   This is an INTERFACE, not a class.
   public <return type> findCarBy<insert attribute name here>(<attribute type> attribute) is the general format for a method.
*/
public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findCarByManufacturer(String manufacturer);

    /*
    do we want this query to only return cars that are available?(I would think yes)
    */
    @Query ("Select u from Car u Where u.capacity >= ?1 and u.availability = True")
    List<Car> findCarByCapacity(int capacity);

    @Query("Select u from Car u Where u.availability = True")
    List<Car> findCarByAvailabilty(boolean True);


}
