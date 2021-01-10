package model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;

public class Observation implements Comparable<Observation> {
    private int id;
    private String name;
    private Animalia animal;
    private Location location;
    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDateTime timeAndDate;
    private int amount;
    private String picturePath;
    private String comment;

    public Observation(int id, String name, Animalia animal, Location location, String timeAndDate, int amount, String picturePath, String comment) {
        this.id = id;
        this.name = name;
        this.animal = animal;
        this.location = location;
        this.timeAndDate = LocalDateTime.parse(timeAndDate, TimeAndDateFormatter.formatter);
        this.amount = amount;
        this.picturePath = picturePath;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Animalia getAnimal() {
        return animal;
    }

    public void setAnimal(Animalia animal) {
        this.animal = animal;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getTimeAndDate() {
        return timeAndDate;
    }

    public void setTimeAndDate(LocalDateTime timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Observation {" + "\n" +
            "ID: " + id + "\n\n" +
            "Name: " + name + "\n\n" +
            "Taxonomic rank: " + "\n" +
            this.getAnimal().toString() + "\n\n" +
            "Location: " + "\n" +
            this.getLocation().toString() + "\n\n" +
            "Time and date: " + timeAndDate + "\n\n" +
            "Amount: " + amount + "\n\n" +
            "Picture: " + picturePath + "\n\n" +
            "Comment: " + comment + "\n" +
            "}";
    }

    @Override
    public int compareTo(Observation o) {
        return this.getId() - (o.getId());
    }
}
