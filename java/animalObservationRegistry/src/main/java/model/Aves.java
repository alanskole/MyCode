package model;

public class Aves extends Animalia {
    private boolean canFly;

    public Aves(String order, String family, String genus, String species, String englishName, boolean canFly) {
        super(order, family, genus, species, englishName);
        this.setAnimalClass("Aves");
        this.canFly = canFly;
    }

    public boolean isCanFly() {
        return canFly;
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
            "Able to fly: " + canFly;
    }
}
