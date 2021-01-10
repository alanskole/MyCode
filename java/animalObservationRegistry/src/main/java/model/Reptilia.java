package model;

public class Reptilia extends Animalia {
    private boolean venomous;
    private int limbs;

    public Reptilia(String order, String family, String genus, String species, String englishName, boolean venomous, int limbs) {
        super(order, family, genus, species, englishName);
        this.setAnimalClass("Reptilia");
        this.venomous = venomous;
        this.limbs = limbs;
    }

    public boolean isVenomous() {
        return venomous;
    }

    public void setVenomous(boolean venomous) {
        this.venomous = venomous;
    }

    public int getLimbs() {
        return limbs;
    }

    public void setLimbs(int limbs) {
        this.limbs = limbs;
    }

    @Override
    public String toString() {
        return "Domain: " + this.getDomain() + "\n" +
            "Kingdom: " + this.getKingdom() + "\n" +
            "Phylum: " + this.getPhylum() + "\n" +
            "Class: " + this.getAnimalClass() + "\n" +
            "Order: " + this.getOrder() + "\n" +
            "Family: " + this.getFamily() + "\n" +
            "Genus: " + this.getGenus() + "\n" +
            "Species: " + this.getSpecies() + "\n" +
            "English name: " + this.getEnglishName() + "\n" +
            "Venomous: " + venomous + "\n" +
            "Limbs: " + limbs;
    }
}
