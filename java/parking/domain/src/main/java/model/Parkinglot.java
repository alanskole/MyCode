package model;

import java.util.*;

public class Parkinglot {
    private int id;
    private Location location;
    private ArrayList<Parkingspot> spots;
    private User owner;

    public Parkinglot() {}

    public Parkinglot(int id, Location location, User owner) {
        this.id = id;
        this.location = location;
        this.owner = owner;
    }

    public Parkinglot(int id, Location location, User owner, ArrayList<Parkingspot> spots) {
        this.id = id;
        this.location = location;
        this.owner = owner;
        this.spots = spots;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<Parkingspot> getSpots() {
        return spots;
    }

    public void setSpots(ArrayList<Parkingspot> spots) {
        this.spots = spots;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        //to avoid nullpointerexception when the parkinglot doesn't have any parkingspots inside its ArrayList of parkingspots
        if (this.getSpots() == null)
            return String.format("Parkinglot ID: " + this.getId() + " " + this.getLocation().toString() + ", Owner: " + this.getOwner());

        return String.format("Parkinglot ID: " + this.getId() + " " + this.getLocation().toString() + " " + this.getSpots().toString() + ", Owner: " + this.getOwner());
    }
}