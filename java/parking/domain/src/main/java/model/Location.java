package model;

public class Location {
    private String city;
    private String address;
    private int number;
    private int zipcode;
    private String area;

    public Location() {}

    public Location(String city, String address, int number, int zipcode, String area) {
        this.city = city;
        this.address = address;
        this.number = number;
        this.zipcode = zipcode;
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public int getNumber() {
        return number;
    }

    public int getZipcode() {
        return zipcode;
    }

    public String getArea() {
        return area;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return String.format("City: " + this.getCity() + ", Address: " + this.getAddress() + ", Number: " + this.getNumber() + ", Zipcode: " + this.getZipcode() + ", Area: " + this.getArea());
    }
}