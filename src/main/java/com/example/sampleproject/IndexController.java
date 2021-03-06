package com.example.sampleproject;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.attribute.UserPrincipal;
import java.util.*;
import java.util.logging.XMLFormatter;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.servlet.view.RedirectView;
import org.w3c.dom.Document;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.transaction.Transaction;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static java.rmi.server.LogStream.log;


/* Over-arching controller of the app. All of the  routes and their respective handler functions are defined here */
@Controller
public class IndexController {

    /* This is the route for when the user first enters the app. Greeted with a choice of login or registration */
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @Autowired
    private UserRepository userRepo; // Access to the User table of the database

    @Autowired
    private CarRepository carRepo; // Access to the Vehicle table of the database

    @Autowired
    private ReservationRepository reservationRepo;  // Access to the Reservation table of the database

    @Autowired
    private ReviewRepository reviewRepo; // Access to the Review table of the database

    private static final Logger log = LoggerFactory.getLogger(SampleprojectApplication.class); // Used for logging messages

    /* Route for the registration, the user is greeted with a form for their login info */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }

    /* After user registers and submit, this function handles the creation of the user and the assignment of information */
    @PostMapping("/process_register")
    public String processRegister(User user) {
        user.setPassword(user.getPassword());
        userRepo.save(user); // Saves the user to the database

        return "register_success";
    }

    /* Home page */
    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    /* Route that shows the list of available along with their features */
    @GetMapping("/cars")
    public String cars(Model model) {
        List<Car> cars = carRepo.findAll();
        model.addAttribute("cars",cars);

        return "cars";
    }

    /* Render the form for the creation of a reservation */
    @GetMapping("/make_reservation")
    public String showReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation_form";
    }
    /* Handle the submitted reservation info and save to the database */
    @PostMapping("/process_reservation")
    public String process_reservation(@RequestParam(name="vehicleID") String vehicleID, Reservation reservation) {
        Long car_id = Long.parseLong(vehicleID);
        reservation.setVehicleID(car_id);

        Optional<Car> car_raw = carRepo.findById(reservation.getVehicleID());
        Car car = car_raw.get();

        User user = userRepo.getById(getLoggedInUser().getId());

        reservation.setLength();
        reservation.setUserID(user.getId());
        reservation.setDailyVehicleRate(car.getDailyRate());
        reservation.setTotalCost(reservation.calculateBill(5));
        reservationRepo.save(reservation);
        return "reservation_success";
    }

    /* Displays the user's reservations */
    @GetMapping("/view_reservations")
    public String user_reservations(Model model) {
        List<Reservation> user_reservations = reservationRepo.findReservationsByUserID(getLoggedInUser().getId());
        model.addAttribute("reservations",user_reservations);

        return "view_reservations";
    }


    /* Returns the User object of the currently logged-in user */
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

    /* Displays the user's information */
    @GetMapping("/account")
    public String getAccountInfo(Model model) {
        model.addAttribute("user",getLoggedInUser());
        return "account";
    }
    /* Renders a form for the user to change his account info */
    @GetMapping("/edit_account")
    public String editAccountView(Model model) {
        return "edit_account";
    }

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

    /* Handles the user's request to change account info. Updates the logged-in user's info in the SecurityContext */
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

    /* Used for the car images. Parses the XML response of the API */
    public String parseXMLResponseCar(String response) throws ParserConfigurationException, IOException, SAXException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                new InputSource(new StringReader(response.toString())));

        String result = document.getElementsByTagName("string").item(0).getTextContent();
        return result;
    }

    /* Displays information about a certain vehicle */
    @GetMapping("/car/{id}")
    public String getFooById(@PathVariable String id,Model model) throws ParserConfigurationException, IOException, SAXException {
        Optional<Car> car = carRepo.findById(Long.parseLong(id));
        Car car_object = car.get();

        String manufacturer = car_object.getManufacturer();
        String carModel = car_object.getModel();
        int year = car_object.getYear();

        String carImageURL = "http://www.carimagery.com/api.asmx/GetImageUrl?searchTerm=" + manufacturer + ' ' + carModel + ' ' + year;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(carImageURL, String.class);
        String parsed = parseXMLResponseCar(result);
        model.addAttribute("car",car_object);
        model.addAttribute("car_image", parsed);

        List<Review> reviews = reviewRepo.findAllByVehicleID(car_object.getId());
        model.addAttribute("reviews", reviews);
        return "car";
    }

    /* Displays the form for the creation of a reservation */
    @GetMapping("/make_reservation/{id}")
    public String reserveCar(@PathVariable String id, Model model) {
        Long car_id = Long.parseLong(id);
        Optional<Car> car = carRepo.findById(car_id);
        Car car_object = car.get();

        Reservation newReservation = new Reservation();
        model.addAttribute("reservation", newReservation);
        model.addAttribute("car",car_object);
        model.addAttribute("car_id", car_id);
        return "reservation_form";
    }

    /* Handles the user's request to post a review */
    @PostMapping("post_review")
    public RedirectView postReview(@RequestParam(name="car_id") String car_id,
                             @RequestParam(name="rating") String rating_raw,
                             @RequestParam(name="anonymous") Optional<Boolean> anonymous,
                             @RequestParam(name="desc") String desc,
                             Model model) {
        Long vehicleID = Long.parseLong(car_id);
        Long userID = getLoggedInUser().getId();
        Integer rating = Integer.parseInt(rating_raw);

        Review newReview = new Review();
        newReview.setDesc(desc);
        newReview.setRating(rating);

        newReview.setVehicleID(vehicleID);
        newReview.setUserID(userID);

        boolean anon = anonymous.isPresent();

        if (anon) {
            newReview.setName("Anonymous");
        } else {
            User user = (userRepo.findById(userID)).get();
            newReview.setName(user.getFirstName() + " " + user.getLastName());
        }
        reviewRepo.save(newReview);
        return new RedirectView("/car/" + vehicleID);

    }

    /* Handles the deletion of reservation with id 'id', redirects back to the list of reservations view*/
    @GetMapping("/delete_reservation/{id}")
    public RedirectView deleteRes(@PathVariable String id) {
        reservationRepo.deleteById(Long.parseLong(id));
        return new RedirectView("/view_reservations");
    }
}
