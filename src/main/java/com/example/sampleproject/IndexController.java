package com.example.sampleproject;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@Controller
public class IndexController {
    @Autowired
    JdbcTemplate jdbcTemplate; // For connecting to the database

    /* This is the route for when the user first enters the app */
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }


    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CarRepository carRepo;

    /* Route for the registration, the user is greeted with a form for their login info */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }

    /* After user registers and submit, this function handles the creation of the user and the assignment of information */
    @PostMapping("/process_register")
    public String processRegister(User user) {
        /*BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());*/
        user.setPassword(user.getPassword());
        userRepo.save(user); // saves the user to the database

        return "register_success";
    }

    // Shows the list of users, was part of the tutorial I used
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);

        List<Car> cars = carRepo.findAll();
        model.addAttribute("cars",cars);

        return "users";
    }
}
