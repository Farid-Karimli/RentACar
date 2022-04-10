package com.example.sampleproject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class SampleprojectApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(SampleprojectApplication.class); // Used for logging anything
    public static void main(String[] args) {
        SpringApplication.run(SampleprojectApplication.class, args);
    }


    @Autowired
    JdbcTemplate jdbcTemplate; // For connecting to the database if need be

    /* This function creates an instance of Car, and populates its attributes with the provided values */
    private Car createCar(String manufacturer, String model, int year, double MPG) {
        Car newCar = new Car();
        newCar.setManufacturer(manufacturer);
        newCar.setModel(model);
        newCar.setMPG(MPG);
        newCar.setYear(year);
        return newCar;
    }


    /* Whatever is inside this method will be run when the app is run, before it is rendered */
    public void run(String... strings) throws Exception {

    }

}
