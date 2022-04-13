package com.example.sampleproject;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;




@Entity
@Table(name = "Reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long userID;

    @Column(nullable = false)
    private long vehicleID;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

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
    private int driverAge;
    private double length;




    public double calculateBill(int salesTax){
        double insuranceTotal = calculateInsurance();
        double equipmentTotal = calculateEquipmentFees();

        double total = (1 + salesTax/100) * (length * dailyVehicleRate + insuranceTotal + equipmentTotal);

        return total;

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
        long lengthInHours = startDate.until(endDate, ChronoUnit.HOURS);
        this.length = lengthInHours / 24;
    }

    public void setDriverAge(int driverAge){
        this.driverAge = driverAge;
    }


//    public void markCancelled(){
//        NEED TO IMPLEMENT
//    }


    public long getId(){
        return id;
    }

    public long getVehicleID() { return vehicleID;}

    public long getUserID() { return userID;}

    public LocalDateTime getStartDate() { return startDate;}

    public LocalDateTime getEndDate() { return endDate;}

    public Boolean getRentalInsurance() { return rentalInsurance;}

    public Boolean getChildSeat() {
        return childSeat;
    }

    public Boolean getSkiRack() {
        return skiRack;
    }

    public void setStartDate(String date){
        this.startDate = LocalDateTime.parse(date);
    }

    public void setEndDate(String date){
        this.endDate = LocalDateTime.parse(date);
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





}