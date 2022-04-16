package com.example.sampleproject;
import java.nio.file.attribute.UserPrincipal;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityManager;
import javax.transaction.Transaction;

import static java.rmi.server.LogStream.log;


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
    @Autowired
    private ReservationRepository reservationRepo;

    @Autowired
    EntityManager entityManager;

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

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/cars")
    public String cars(Model model) {
        List<Car> cars = carRepo.findAll();
        model.addAttribute("cars",cars);

        return "cars";
    }

    @GetMapping("/make_reservation")
    public String showReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation_form";
    }

    @PostMapping("/process_reservation")

    public String process_reservation(Reservation reservation) {
        Car car = carRepo.getById(reservation.getVehicleID());
        User user = userRepo.getById(getLoggedInUser().getId());

        reservation.setLength();
        reservation.setUserID(user.getId());
//        reservation.setVehicleID(car.getId()); currently this is useless, but if we implement a better car selection method it won't be
//        reservation.setDailyVehicleRate(car.getDailyRate());
//        reservation.setDriverAge(user.getAge());

        reservationRepo.save(reservation);
        return "reservation_success";
    }


    private static final Logger log = LoggerFactory.getLogger(SampleprojectApplication.class); // Used for logging anything
    public User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = null;
        String email;
        String firstname = "";
        String lastname= "";
        String phone= "";
        if (principal instanceof CustomUserDetails) {
            id = (((CustomUserDetails)principal).getId());
            email = ((CustomUserDetails)principal).getUsername();
            firstname = ((CustomUserDetails)principal).getFirstName();
            lastname = ((CustomUserDetails)principal).getLastName();
            phone = ((CustomUserDetails)principal).getPhone();
        } else {
            email = principal.toString();
        }
        User loggedInUser = new User();
        loggedInUser.setId(id);
        loggedInUser.setEmail(email);
        loggedInUser.setFirstName(firstname);
        loggedInUser.setLastName(lastname);
        loggedInUser.setPhone(phone);

        return loggedInUser;
    }
    @GetMapping("/account")
    public String getAccountInfo(Model model) {
        model.addAttribute("user",getLoggedInUser());
        return "account";
    }
    @GetMapping("/edit_account")
    public String editAccountView(Model model) {
        return "edit_account";
    }

//    @GetMapping("/car_search")
//    public String searchCars(Model model) {
//        List<Car> listCars = carRepo.findAll();
//        model.addAttribute("cars", listCars);
//        return "cars";
//    }

    @PostMapping("/car_search")
    public String searchCars(@RequestParam(name="value") String value, @RequestParam(name="filter") String filter, Model model) {
        if (Objects.equals(filter, "capacity")) {
            int capacity = Integer.parseInt(value);
            model.addAttribute("cars",carRepo.findAllByCapacity(capacity));
        } else if (filter.equals("manufacturer")){
            model.addAttribute("cars",carRepo.findAllByManufacturer(value));
        }  else if (filter.equals("model")) {
            model.addAttribute("cars", carRepo.findAllByModel(value));
        } else if (filter.equals("type")) {
            model.addAttribute("cars", carRepo.findAllByType(value));
        } else {
            model.addAttribute("cars", carRepo.findAll());
        }

        return "cars";
    }





    @GetMapping("/SearchForCars")
    public String searchForACar(Model model) {return "SearchForCars";}

    @PostMapping("/edit_account")
    @ResponseBody
    public RedirectView editAccountInfo(@RequestParam(name="firstname") String firstName,
                                  @RequestParam(name="lastname") String lastname,
                                  @RequestParam(name="email") String email,
                                  @RequestParam(name="phone") String phone) {


        Long loggedInUserId = getLoggedInUser().getId();

        User loggedInUser = getLoggedInUser();
        loggedInUser.setFirstName(firstName);
        loggedInUser.setLastName(lastname);
        loggedInUser.setEmail(email);
        loggedInUser.setPhone(phone);

        userRepo.updateUserInfo(loggedInUserId,firstName,lastname,phone,email);

        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        principal.setUser(loggedInUser);

        return new RedirectView("/account");
    }

//    @GetMapping("/create_reservation")

}
