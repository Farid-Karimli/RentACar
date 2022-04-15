package com.example.sampleproject;

import javax.persistence.*;

/* An @Entity class defines a class that will be turned into a table in the database
*  You use @Table to set up some configuration variables, including the table's name as you see below
*  You use @Column to define a column in the table, which is an attribute of the class. You also specify the column name,
*  whether it can be null, unique and the length if it's a string
*/
@Entity
@Table(name="Cars")
public class Car {

    /* This is the id column, it starts from 1 and gets incremented every time a Car is added to the table
    *  Equivalent of AUTO_INCREMENT in MySQL
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="manufacturer",nullable = false, length = 45)
    private String manufacturer;

    @Column(name="model",nullable = false, length = 45)
    private String model;

    @Column(name="release_year")
    private int year;

    @Column(name = "MPG", nullable = false)
    private double MPG;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    /*
   change to be an integer that represents the number of cars available
    */
    @Column(name = "availability",nullable = true)
    private int availability;

    @Column(name = "type",nullable = true)
    private String type;

    @Column(name = "daily_rate",nullable = true)
    private double dailyRate;



    public double getMPG() {
        return MPG;
    }

    public int getYear() {
        return year;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public int getCapacity() {return capacity;}

    public int getAvailability() {return availability;}

    public String getType() {return type;}

    public double getDailyRate() {return dailyRate;}

    public long getId() { return id; }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setMPG(double MPG) {
        this.MPG = MPG;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCapacity(int capacity) {this.capacity = capacity;}

    public void setAvailability(int availability) {this.availability = availability;}

    public void setDailyRate(double dailyRate) {this.dailyRate = dailyRate;}

    private void setType(String type) {this.type = type;}



    @Override
    public String toString() {
        return String.format(
                "Car[id=%d, maker=%s, model=%s, year=%d, MPG=%f, capacity=%i]",
                id,manufacturer,model,year,MPG,capacity
        );
    }
}
