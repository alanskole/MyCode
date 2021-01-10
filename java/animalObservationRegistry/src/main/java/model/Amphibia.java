package model;

public class Amphibia extends Animalia {
    private boolean limbless;

    public Amphibia(String order, String family, String genus, String species, String englishName, boolean limbless) {
        super(order, family, genus, species, englishName);
        this.setAnimalClass("Amphibia");
        this.limbless = limbless;
    }

    public boolean isLimbless() {
        return limbless;
    }

    public void setLimbless(boolean limbless) {
        this.limbless = limbless;
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
            "Limbless: " + limbless;
    }
}
