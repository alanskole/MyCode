package model;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class ObservationRepository implements InterfaceOberservationRepo {
    private ConcurrentSkipListMap<Integer, Observation> MapOfLife = new ConcurrentSkipListMap<>();
    private ArrayList<Observation> observations = new ArrayList<>();
    private File in;
    private File out;
    private int id = 1;

    public ObservationRepository() {}

    public ObservationRepository(File in, File out) {
        this.in = in;
        this.out = out;
        readFromCsv(in);
    }

    public File getIn() {
        return in;
    }

    public File getOut() {
        return out;
    }

    public void setIn(File in) {
        this.in = in;
    }

    public void setOut(File out) {
        this.out = out;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public ArrayList<Observation> getObservations() throws Exception {
        checkIfMapIsEmptyContains();

        checkIfMapAndArrayListEqual();
        return observations;
    }

    private void checkIfMapIsEmptyContains() throws Exception {
        if (MapOfLife.isEmpty())
            throw new Exception("No observations found inside the map!");
    }

    private void checkIfMapAndArrayListEqual() {
        if (MapOfLife.size() != observations.size()) {
            addObservationToArrayListIfNotAlreadyPresent();
        }
    }

    private void addObservationToArrayListIfNotAlreadyPresent() {
        for (Map.Entry<Integer, Observation> hmap : MapOfLife.entrySet()) {
            if (!observations.contains(hmap.getValue())) {
                observations.add(hmap.getValue());
            }
        }
    }

    @Override
    public Observation getObservation(int observationId) throws Exception {
        checkIfMapContains(observationId);

        return MapOfLife.get(observationId);
    }

    @Override
    public Animalia getAnimal(int observationId) throws Exception {
        checkIfMapContains(observationId);

        return MapOfLife.get(observationId).getAnimal();
    }

    @Override
    public void createObservation(String name, Animalia animal, Location location, String timeAndDate, int amount, String picturePath, String comment) {
        Observation observation = new Observation(id, name, animal, location, timeAndDate, amount, picturePath, comment);
        MapOfLife.put(observation.getId(), observation);
        observations.add(observation);
        writeToCSV(MapOfLife, out);
        id++;
    }

    @Override
    public void deleteObservation(int observationId) throws Exception {
        checkIfMapContains(observationId);

        for (Observation obs : observations) {
            if (obs.getId() == observationId) {
                observations.remove(obs);
                break;
            }
        }
        MapOfLife.remove(observationId);
        writeToCSV(MapOfLife, out);
    }

    @Override
    public void updateObservation(int observationId, String name, Animalia animal, Location location, String timeAndDate, int amount, String picturePath, String comment) throws Exception {
        checkIfMapContains(observationId);

        Observation updatedObservation =  new Observation(observationId, name, animal, location, timeAndDate, amount, picturePath, comment);

        MapOfLife.replace(observationId, updatedObservation);

        for (int i = 0; i < observations.size(); i++) {
            if (observations.get(i).getId() == observationId) {
                observations.get(i).setName(name);
                observations.get(i).setAnimal(animal);
                observations.get(i).setLocation(location);
                observations.get(i).setTimeAndDate(LocalDateTime.parse(timeAndDate, TimeAndDateFormatter.formatter));
                observations.get(i).setAmount(amount);
                observations.get(i).setPicturePath(picturePath);
                observations.get(i).setComment(comment);
                break;
            }
        }
        writeToCSV(MapOfLife, out);
    }

    private void checkIfMapContains(int observationId) throws Exception {
        if (!MapOfLife.containsKey(observationId))
            throw new Exception("Not found!");
    }

    public void readFromCsv(File in) {
        if (in == null)
            in = new File("observations.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(in))) {
            String linje = null;
            String dateAndTime = "";
            int amount = 0;
            String picpath = "";
            String comment = "";

            ArrayList<Biome> biome = new ArrayList<>();

            while ((linje = reader.readLine()) != null) {
                String[] deler = linje.split(",(?! )");
                String name = deler[0];

                if (deler[4].equalsIgnoreCase("aves")) {

                    Aves anim = new Aves(deler[5], deler[6], deler[7], deler[8], deler[9], Boolean.parseBoolean(deler[10]));

                    for (int i = 14; i < deler.length; i++) {
                        if (Character.isDigit(deler[i].charAt(0))) {
                            dateAndTime = deler[i];
                            amount = Integer.parseInt(deler[i + 1]);
                            picpath = deler[i + 2];
                            comment = deler[i + 3];
                            break;
                        } else {
                            biome.add(new Biome(deler[i]));
                        }
                    }

                    Location loc = new Location(new Planet(deler[11]), Double.parseDouble(deler[12]), Double.parseDouble(deler[13]), biome);

                    createObservation(name, anim, loc, dateAndTime, amount, picpath, comment);

                } else if (deler[4].equalsIgnoreCase("amphibia")) {
                    Amphibia anim = new Amphibia(deler[5], deler[6], deler[7], deler[8], deler[9], Boolean.parseBoolean(deler[10]));

                    for (int i = 14; i < deler.length; i++) {
                        if (Character.isDigit(deler[i].charAt(0))) {
                            dateAndTime = deler[i];
                            amount = Integer.parseInt(deler[i + 1]);
                            picpath = deler[i + 2];
                            comment = deler[i + 3];
                            break;
                        } else {
                            biome.add(new Biome(deler[i]));
                        }
                    }

                    Location loc = new Location(new Planet(deler[11]), Double.parseDouble(deler[12]), Double.parseDouble(deler[13]), biome);

                    createObservation(name, anim, loc, dateAndTime, amount, picpath, comment);

                } else if (deler[4].equalsIgnoreCase("reptilia")) {
                    Reptilia anim = new Reptilia(deler[5], deler[6], deler[7], deler[8], deler[9], Boolean.parseBoolean(deler[10]), Integer.parseInt(deler[11]));

                    for (int i = 15; i < deler.length; i++) {
                        if (Character.isDigit(deler[i].charAt(0))) {
                            dateAndTime = deler[i];
                            amount = Integer.parseInt(deler[i + 1]);
                            picpath = deler[i + 2];
                            comment = deler[i + 3];
                            break;
                        } else {
                            biome.add(new Biome(deler[i]));
                        }
                    }

                    Location loc = new Location(new Planet(deler[12]), Double.parseDouble(deler[13]), Double.parseDouble(deler[14]), biome);

                    createObservation(name, anim, loc, dateAndTime, amount, picpath, comment);
                }
            }
        } catch(IOException e){
                e.printStackTrace();
        }
    }

    public void writeToCSV(ConcurrentSkipListMap<Integer, Observation> MapOfLife, File out) {
        if (out == null)
            out = new File("output.csv");

        File finalOut = out;
        Thread thread = new Thread(() -> {
            try (FileWriter fileWriter = new FileWriter(finalOut)) {
                writeOutput(MapOfLife, fileWriter);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void writeOutput(ConcurrentSkipListMap<Integer, Observation> MapOfLife, FileWriter fileWriter) throws IOException {
        for (Map.Entry<Integer, Observation> hmap : MapOfLife.entrySet()) {
            String info = null;

            if (hmap.getValue().getAnimal() instanceof Aves) {
                info = Boolean.toString(((Aves) hmap.getValue().getAnimal()).isCanFly());
            }

            else if (hmap.getValue().getAnimal() instanceof Amphibia) {
                info = Boolean.toString(((Amphibia) hmap.getValue().getAnimal()).isLimbless());
            }

            else if (hmap.getValue().getAnimal() instanceof Reptilia) {
                info = Boolean.toString(((Reptilia) hmap.getValue().getAnimal()).isVenomous());
                info += "," + ((Reptilia) hmap.getValue().getAnimal()).getLimbs();
            }

            writeString(fileWriter, hmap, info);
        }
    }

    private void writeString(FileWriter fileWriter, Map.Entry<Integer, Observation> hmap, String info) throws IOException {
        String joiner = hmap.getValue().getId() + "," + hmap.getValue().getName() + "," + hmap.getValue().getAnimal().getDomain() + "," + hmap.getValue().getAnimal().getKingdom() + "," + hmap.getValue().getAnimal().getPhylum() + "," + hmap.getValue().getAnimal().getAnimalClass() + "," + hmap.getValue().getAnimal().getOrder() + "," + hmap.getValue().getAnimal().getFamily() + "," + hmap.getValue().getAnimal().getGenus() + "," + hmap.getValue().getAnimal().getSpecies() + "," + hmap.getValue().getAnimal().getEnglishName() + "," + info + "," + hmap.getValue().getLocation().getPlanet().getName() + "," + hmap.getValue().getLocation().getLatitude() + "," + hmap.getValue().getLocation().getLongitude() + ",";
        joiner = loopOverBiomes(hmap, joiner);
        joiner += hmap.getValue().getTimeAndDate() + "," + hmap.getValue().getAmount() + "," + hmap.getValue().getPicturePath() + "," + hmap.getValue().getComment() + "\n";
        fileWriter.write(joiner);
    }

    private String loopOverBiomes(Map.Entry<Integer, Observation> hmap, String joiner) {
        for (Biome biome : hmap.getValue().getLocation().getBiome()) {
            joiner += biome.getName() + ",";
        }
        return joiner;
    }
}