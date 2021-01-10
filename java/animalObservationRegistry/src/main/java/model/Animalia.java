package model;

public abstract class Animalia {
    private final String domain;
    private final String kingdom;
    private final String phylum;
    private String animalClass;
    private String order;
    private String family;
    private String genus;
    private String species;
    private String englishName;

    public Animalia(String animalClass, String order, String family, String genus, String species, String englishName) {
        this.domain = "Eukarya";
        this.kingdom = "Anmialia";
        this.phylum = "Chordata";
        this.animalClass = animalClass;
        this.order = order;
        this.family = family;
        this.genus = genus;
        this.species = species;
        this.englishName = englishName;
    }

    public Animalia(String order, String family, String genus, String species, String englishName) {
        this.domain = "Eukarya";
        this.kingdom = "Anmialia";
        this.phylum = "Chordata";
        this.animalClass = animalClass;
        this.order = order;
        this.family = family;
        this.genus = genus;
        this.species = species;
        this.englishName = englishName;
    }

    public String getDomain() {
        return domain;
    }

    public String getKingdom() {
        return kingdom;
    }

    public String getPhylum() {
        return phylum;
    }

    public String getAnimalClass() {
        return animalClass;
    }

    public void setAnimalClass(String animalClass) {
        this.animalClass = animalClass;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
}
