package controller;

import model.*;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Controller {
    private ObservationRepository observationRepository;

    public Controller(ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    public ObservationRepository getObservationRepository() {
        return observationRepository;
    }

    public void getObservations(Context context) {
        try {
            String sortBy = context.queryParam("sort_by");

            if (sortBy != null) {
                switch (sortBy) {
                    case "time":
                        Collections.sort(observationRepository.getObservations(), Comparator.comparing(Observation::getTimeAndDate));
                        break;
                    case "name":
                        Collections.sort(observationRepository.getObservations(), Comparator.comparing(Observation::getName));
                        break;
                    case "id":
                        Collections.sort(observationRepository.getObservations());
                        break;
                }
            }
            context.json(observationRepository.getObservations());
        } catch (Exception e) {
            context.status(404);
        }

    }

    public void getObservation(Context context) {
        try {
            Observation observation = observationRepository.getObservation(Integer.parseInt(context.pathParam("id")));

            context.json(observation);
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void getAnimal(Context context) {
        try {
            Animalia animal = observationRepository.getAnimal(Integer.parseInt(context.pathParam("id")));

            context.json(animal);
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void createObservation(Context context) {
        try {
            ArrayList<Biome> biome = new ArrayList<>();

            String name = (context.formParam("name"));
            String animalClass = (context.formParam("animalClass"));
            String order = (context.formParam("order"));
            String family = (context.formParam("family"));
            String genus = (context.formParam("genus"));
            String species = (context.formParam("species"));
            String englishName = (context.formParam("englishName"));
            Planet planet = new Planet((context.formParam("planetName")));
            double latitude = Double.parseDouble(context.formParam("latitude"));
            double longitude = Double.parseDouble(context.formParam("longitude"));

            biomeChecker(context, biome);

            String timeAndDate = context.formParam("timeAndDate");
            int amount = Integer.parseInt(context.formParam("amount"));
            String picturePath = (context.formParam("picturePath"));
            String comment = (context.formParam("comment"));

            if (animalClass.equalsIgnoreCase("bird") || animalClass.equalsIgnoreCase("aves")) {
                createBird(context, biome, name, order, family, genus, species, englishName, planet, latitude, longitude, timeAndDate, amount, picturePath, comment);
            } else if (animalClass.equalsIgnoreCase("reptile") || animalClass.equalsIgnoreCase("reptilia")) {
                createReptile(context, biome, name, order, family, genus, species, englishName, planet, latitude, longitude, timeAndDate, amount, picturePath, comment);
            } else if (animalClass.equalsIgnoreCase("amphibian") || animalClass.equalsIgnoreCase("amphibia")) {
                createAmphibian(context, biome, name, order, family, genus, species, englishName, planet, latitude, longitude, timeAndDate, amount, picturePath, comment);
            }

            context.redirect("/observations/"+(observationRepository.getId()-1));

        } catch (Exception e) {
            context.status(404);
        }

    }

    private void createAmphibian(Context context, ArrayList<Biome> biome, String name, String order, String family, String genus, String species, String englishName, Planet planet, double latitude, double longitude, String timeAndDate, int amount, String picturePath, String comment) {
        Boolean limbless = false;

        if (context.formParam("limbless").equalsIgnoreCase("yes")) {
            limbless = true;
        }

        observationRepository.createObservation(
            name,
            new Amphibia(order, family, genus, species, englishName, limbless),
            new Location(planet, latitude, longitude, biome),
            timeAndDate,
            amount,
            picturePath,
            comment
        );
    }

    private void createReptile(Context context, ArrayList<Biome> biome, String name, String order, String family, String genus, String species, String englishName, Planet planet, double latitude, double longitude, String timeAndDate, int amount, String picturePath, String comment) {
        Boolean venomous = false;

        if (context.formParam("venomous").equalsIgnoreCase("yes")) {
            venomous = true;
        }

        observationRepository.createObservation(
            name,
            new Reptilia(order, family, genus, species, englishName, venomous, Integer.parseInt(context.formParam("limbs"))),
            new Location(planet, latitude, longitude, biome),
            timeAndDate,
            amount,
            picturePath,
            comment
        );
    }

    private void createBird(Context context, ArrayList<Biome> biome, String name, String order, String family, String genus, String species, String englishName, Planet planet, double latitude, double longitude, String timeAndDate, int amount, String picturePath, String comment) {
        Boolean canFly = false;
        if (context.formParam("canFly").equalsIgnoreCase("yes")) {
            canFly = true;
        }

        observationRepository.createObservation(
            name,
            new Aves(order, family, genus, species, englishName, canFly),
            new Location(planet, latitude, longitude, biome),
            timeAndDate,
            amount,
            picturePath,
            comment
        );
    }

    private void biomeChecker(Context context, ArrayList<Biome> biome) {
        if (context.formParam("biome").contains(",")) {
            addSeveralBiomes(context, biome);
        } else
            biome.add(new Biome(context.formParam("biome")));
    }

    public void deleteOrUpdateObservation(Context context) throws Exception {
        try {
            int obsId = Integer.parseInt(context.pathParam("id"));

            if (context.path().contains("delete")) {
                delete(context, obsId);
                return;
            } else if (context.path().contains("update")){
                update(context, obsId);
            }
            context.redirect("/observations/"+obsId);
        } catch (Exception e) {
            context.status(404);
        }

    }

    private void update(Context context, int obsId) throws Exception {
        ArrayList<Biome> biome = new ArrayList<>();

        String name = (context.formParam("name"));
        String animalClass = (context.formParam("animalClass"));
        String order = (context.formParam("order"));
        String family = (context.formParam("family"));
        String genus = (context.formParam("genus"));
        String species = (context.formParam("species"));
        String englishName = (context.formParam("englishName"));
        Planet planet = new Planet((context.formParam("planetName")));
        double latitude = Double.parseDouble(context.formParam("latitude"));
        double longitude = Double.parseDouble(context.formParam("longitude"));

        biomeChecker(context, biome);

        String timeAndDate = context.formParam("timeAndDate");
        int amount = Integer.parseInt(context.formParam("amount"));
        String picturePath = (context.formParam("picturePath"));
        String comment = (context.formParam("comment"));

        if (animalClass.equalsIgnoreCase("aves") || animalClass.equalsIgnoreCase("bird")) {
            bird(context, obsId, biome, name, order, family, genus, species, englishName, planet, latitude, longitude, timeAndDate, amount, picturePath, comment);
        } else if (animalClass.equalsIgnoreCase("Reptilia") || animalClass.equalsIgnoreCase("reptile")) {
            reptile(context, obsId, biome, name, order, family, genus, species, englishName, planet, latitude, longitude, timeAndDate, amount, picturePath, comment);
        } else if (animalClass.equalsIgnoreCase("Amphibia") || animalClass.equalsIgnoreCase("amphibian")) {
            amphibia(context, obsId, biome, name, order, family, genus, species, englishName, planet, latitude, longitude, timeAndDate, amount, picturePath, comment);
        }
    }

    private void amphibia(Context context, int obsId, ArrayList<Biome> biome, String name, String order, String family, String genus, String species, String englishName, Planet planet, double latitude, double longitude, String timeAndDate, int amount, String picturePath, String comment) throws Exception {
        Boolean limbless = false;

        if (context.formParam("limbless").equalsIgnoreCase("true")) {
            limbless = true;
        }

        observationRepository.updateObservation(obsId,
            name,
            new Amphibia(order, family, genus, species, englishName, limbless),
            new Location(planet, latitude, longitude, biome),
            timeAndDate,
            amount,
            picturePath,
            comment
        );
    }

    private void reptile(Context context, int obsId, ArrayList<Biome> biome, String name, String order, String family, String genus, String species, String englishName, Planet planet, double latitude, double longitude, String timeAndDate, int amount, String picturePath, String comment) throws Exception {
        Boolean venomous = false;

        if (context.formParam("venomous").equalsIgnoreCase("true")) {
            venomous = true;
        }

        observationRepository.updateObservation(obsId,
            name,
            new Reptilia(order, family, genus, species, englishName, venomous, Integer.parseInt(context.formParam("limbs"))),
            new Location(planet, latitude, longitude, biome),
            timeAndDate,
            amount,
            picturePath,
            comment
        );
    }

    private void bird(Context context, int obsId, ArrayList<Biome> biome, String name, String order, String family, String genus, String species, String englishName, Planet planet, double latitude, double longitude, String timeAndDate, int amount, String picturePath, String comment) throws Exception {
        boolean canFly = false;
        if (context.formParam("canFly").equalsIgnoreCase("yes")) {
            canFly = true;
        }

        observationRepository.updateObservation(obsId,
            name,
            new Aves(order, family, genus, species, englishName, canFly),
            new Location(planet, latitude, longitude, biome),
            timeAndDate,
            amount,
            picturePath,
            comment
        );
    }

    private void addSeveralBiomes(Context context, ArrayList<Biome> biome) {
        String[] values = context.formParam("biome").split(",");
        for (String vl : values) {
            biome.add(new Biome(vl));
        }
    }

    private void delete(Context context, int obsId) throws Exception {
        observationRepository.deleteObservation(obsId);
        context.redirect("/observations/");
    }
}
