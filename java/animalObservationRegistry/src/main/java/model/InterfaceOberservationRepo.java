package model;

import java.util.ArrayList;

public interface InterfaceOberservationRepo {
    void createObservation(String name, Animalia animal, Location location, String timeAndDate, int amount, String picturePath, String comment);
    void updateObservation(int observationId, String name, Animalia animal, Location location, String timeAndDate, int amount, String picturePath, String comment) throws Exception;
    void deleteObservation(int observationId) throws Exception;
    Observation getObservation(int observationId) throws Exception;
    Animalia getAnimal(int observationId) throws Exception;
    ArrayList<Observation> getObservations() throws Exception;
}
