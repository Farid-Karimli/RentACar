package com.example.sampleproject;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;




@Entity
@Table(name = "Reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userID;

    @Column(nullable = false)
    private Long vehicleID;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "rental_insurance", nullable = false)
    private Boolean rentalInsurance;

    @Column(name = "child_seat", nullable = false)
    private Boolean childSeat;

    @Column(name = "ski_rack", nullable = false)
    private Boolean skiRack;

    // equipment constants
    private double ChildSeatPerDiem = 20;
    private double SkiRackPerDiem = 35;


    private double dailyVehicleRate;
    private int driverAge = 21;
    private double length;

    @Column(name = "total_cost")
    private double totalCost = 0.0;


    public double calculateBill(int salesTax){
        double insuranceTotal = calculateInsurance();
        double equipmentTotal = calculateEquipmentFees();

        return (1 + salesTax/100.0) * (length * dailyVehicleRate + insuranceTotal + equipmentTotal);

    }


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

    public void setLength() {
        long lengthInHours = startDate.until(endDate, ChronoUnit.DAYS);
        this.length = lengthInHours;
    }

    public void setDriverAge(int driverAge){
        this.driverAge = driverAge;
    }


//    public void markCancelled(){
//        NEED TO IMPLEMENT
//    }


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