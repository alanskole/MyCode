package model;

public class Biome implements Comparable<Biome> {
    private String name;

    public Biome(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Biome: " + name;
    }

    @Override
    public int compareTo(Biome o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }
}
