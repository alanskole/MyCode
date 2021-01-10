package model;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class BookingSchedule {
    private Parkinglot parkinglot;
    private TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00");

    public BookingSchedule() {}

    public BookingSchedule(Parkinglot parkinglot, TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule) {
        this.parkinglot = parkinglot;
        this.schedule = schedule;
    }

    public TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> getSchedule() {
        return schedule;
    }

    public void setSchedule(TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule) {
        this.schedule = schedule;
    }

    public Parkinglot getParkinglot() {
        return parkinglot;
    }

    public void setParkinglot(Parkinglot parkinglot) {
        this.parkinglot = parkinglot;
    }

    @Override
    public String toString(){
        return "Parkinglot ID: " + this.getParkinglot().getId() + ", " + this.getParkinglot().getLocation() + " " + this.getSchedule();
    }
}