package com.example.sampleproject;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

/* Table for storing reservations with their associated data */
@Entity
@Table(name = "Reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK of the table

    @Column(nullable = false)
    private Long userID; // FK to the id attribute of the Users table

    @Column(nullable = false)
    private Long vehicleID; // FK to the id attribute of the Cars table

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // Start date of the reservation, should be present

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate; // End date of the reservation, should be present

    @Column(name = "rental_insurance", nullable = false)
    private Boolean rentalInsurance; // Whether the user chose to include insurance

    @Column(name = "child_seat", nullable = false)
    private Boolean childSeat; // Whether the user chose to include a childseat

    @Column(name = "ski_rack", nullable = false)
    private Boolean skiRack; // Whether the user chose to include a ski rack

    // equipment constants
    private double ChildSeatPerDiem = 20; // Price of child seat per child
    private double SkiRackPerDiem = 35; // Price of a ski rack per person


    private double dailyVehicleRate; // Defined in the Cars table
    private int driverAge = 21; // Age of the driver
    private double length; // Day length of the reservation

    @Column(name = "total_cost")
    private double totalCost = 0.0; // Cost of the reservation


    /* Calculates the total bill for the reservation according to the length and choices made */
    public double calculateBill(int salesTax){
        double insuranceTotal = calculateInsurance();
        double equipmentTotal = calculateEquipmentFees();

        return (1 + salesTax/100.0) * (length * dailyVehicleRate + insuranceTotal + equipmentTotal);

    }

    /* used in the calculation of the cost*/
    private double calculateEquipmentFees(){
        double dailyCost = 0;
        if (childSeat) {
            dailyCost += ChildSeatPerDiem;
        }
        if (skiRack) {
            dailyCost += SkiRackPerDiem;
        }

        return length * dailyCost;

    }

    private double calculateInsurance(){
        double dailyCost = 0;

        if (rentalInsurance) {
            double dailyBaseInsurance = dailyVehicleRate / 20;
            double ageScalar = 100 / (driverAge + 25); // this function is arbitrary
            dailyCost = ageScalar * dailyBaseInsurance;
            return length * dailyCost;
        }
        return 0;
    }

    /* Getters and setters */

    public void setLength() {
        long lengthInHours = startDate.until(endDate, ChronoUnit.DAYS);
        this.length = lengthInHours;
    }

    public void setDriverAge(int driverAge){
        this.driverAge = driverAge;
    }

    public Long getId(){
        return id;
    }

    public Long getVehicleID() { return vehicleID;}

    public Long getUserID() { return userID;}

    public LocalDate getStartDate() { return startDate;}

    public LocalDate getEndDate() { return endDate;}

    public Boolean getRentalInsurance() { return rentalInsurance;}

    public Boolean getChildSeat() {
        return childSeat;
    }

    public Boolean getSkiRack() {
        return skiRack;
    }

    public void setStartDate(String date){
        this.startDate = LocalDate.parse(date);
    }

    public void setEndDate(String date){
        this.endDate = LocalDate.parse(date);
    }

    public void setUserID(long ID){
        this.userID = ID;
    }

    public void setVehicleID(long VID){
        this.vehicleID = VID;
    }

    public void setDailyVehicleRate(double DVR){
        this.dailyVehicleRate = DVR;
    }

    public void setRentalInsurance(Boolean input){
        rentalInsurance = input;
    }

    public void setChildSeat(Boolean input){
        childSeat = input;
    }

    public void setSkiRack(Boolean input){
        skiRack = input;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double total_cost) {
        this.totalCost = total_cost;
    }
}