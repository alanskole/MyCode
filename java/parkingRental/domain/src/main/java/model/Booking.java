package model;

import java.util.*;

public class Booking {
    private int id;
    private Parkingspot spot;
    private ArrayList<String> dateAndTime;
    private Parkinglot parkinglot;
    private User user;
    private int price = 0;

    public Booking() {}

    public Booking(int id, User user, Parkingspot spot, ArrayList<String> dateAndTime, Parkinglot parkinglot) {
        this.id = id;
        this.dateAndTime = dateAndTime;
        this.spot = spot;
        this.parkinglot = parkinglot;
        this.user = user;
    }

    public ArrayList<String> getDateAndTime() {
        return dateAndTime;
    }

    public Parkingspot getSpot() {
        return spot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Parkinglot getParkinglot() {
        return parkinglot;
    }

    public void setParkinglot(Parkinglot parkinglot) throws Exception {

        this.parkinglot = parkinglot;
    }

    public User getUser() {
        return user;
    }

    @Override
        public String toString () {
        return "Booking-ID: " + this.getId() + ", Date and time: " + this.getDateAndTime().toString() + ", Spot: " + this.getSpot().getSpotid() + ", Parkinglot-ID: " + this.getParkinglot().getId() + " " + this.getParkinglot().getLocation() + ", First name: " + this.getUser().getFirstname() + ", Last name: " + this.getUser().getSurname() + ", User-ID: " + this.getUser().getId() + ", Total price: " + this.getPrice();
    }
}

