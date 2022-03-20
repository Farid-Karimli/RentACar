package com.example.sampleproject;

public class Car {
    private long id;
    private String manufacturer,model;
    private int year;
    private double MPG;

    public Car(long id, String maker,String model, int year, double mpg) {
        this.id = id;
        this.manufacturer = maker;
        this.model = model;
        this.year = year;
        this.MPG = mpg;
    }

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

    @Override
    public String toString() {
        return String.format(
                "Car[id=%d, maker=%s, model=%s, year=%d, MPG=%f]",
                id,manufacturer,model,year,MPG
        );
    }
}
