package model;

public class User {
    private int id;
    private String firstname;
    private String surname;
    private Location location;
    private ROLE role;

    public User() {}

    public User(int id, String firstname, String surname, Location location) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.location = location;
        this.role = role.User;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    @Override
    public String toString(){
        return String.format("Name: " + firstname + " " + surname + "Address: " + location);
    }
}
