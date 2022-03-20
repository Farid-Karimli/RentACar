package com.example.sampleproject;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    private List<Car> getAllCars() {
        return jdbcTemplate.query(
                "SELECT id, manufacturer, model, year, MPG FROM Cars",
                (rs, rowNum) -> new Car(rs.getLong("id"), rs.getString("manufacturer"), rs.getString("model"),
                        rs.getInt("year"), rs.getDouble("MPG")));
    }

    @GetMapping("/")
    public String index(Model model) {
        /* Get all the cars in the database */
        List<Car> cars = getAllCars();
        model.addAttribute("cars",cars);
        return "index";
    }

}
