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
    private static final Logger log = LoggerFactory.getLogger(SampleprojectApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(SampleprojectApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void run(String... strings) throws Exception {
        /*jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE Cars(" +
                "id SERIAL, manufacturer VARCHAR(255), model VARCHAR(255)," +
                "year int, MPG float)");

        *//* Insert new car into the table *//*
        jdbcTemplate.update("INSERT INTO Cars(manufacturer,model,year,MPG) VALUES ('Toyota', 'Camry',2022,28)");

        jdbcTemplate.query(
                "SELECT id, manufacturer, model, year, MPG FROM Cars WHERE manufacturer = ?", new Object[] { "Toyota" },
                (rs, rowNum) -> new Car(rs.getLong("id"), rs.getString("manufacturer"), rs.getString("model"),
                                        rs.getInt("year"), rs.getDouble("MPG")))
                .forEach(car -> log.info(car.toString()));*/
    }
}
