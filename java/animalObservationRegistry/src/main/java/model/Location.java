package model;

import java.util.ArrayList;

public class Location {
    private Planet planet;
    private double latitude;
    private double longitude;
    private ArrayList<Biome> biome;

    public Location(Planet planet, double latitude, double longitude, ArrayList<Biome> biome) {
        this.planet = planet;
        this.latitude = latitude;
        this.longitude = longitude;
        this.biome = biome;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLattitude(double lattitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<Biome> getBiome() {
        return biome;
    }

    public void setBiome(ArrayList<Biome> biome) {
        this.biome = biome;
    }

    @Override
    public String toString() {
        return this.getPlanet().toString() + "\n" +
            "Latitude: " + latitude + "\n" +
            "Longitude: " + longitude + "\n" +
            this.getBiome().toString();
    }
}
