package service;

import irepository.InterfaceCrud;
import model.Parkinglot;
import model.Parkingspot;

public class MapCheckerService {
    public static void isAnythingInTheMap(InterfaceCrud interfaceCrud) throws Exception {
        if (interfaceCrud.getAll() == null || interfaceCrud.getAll().isEmpty())
            throw new Exception("Map is empty!");
    }

    public static void isObjectInTheMap(InterfaceCrud interfaceCrud, int id) throws Exception {
        isAnythingInTheMap(interfaceCrud);

        if (interfaceCrud.getAll().containsKey(id) == false) {
            throw new Exception("Not found inside the map!");
        }
    }

    public static void isSpotInLot(Parkinglot parkinglot, int spotid) throws Exception {
        doesLotHaveSpots(parkinglot);

        for (Parkingspot spot : parkinglot.getSpots()) {
            if (spot.getSpotid() == spotid)
                return;
        }

        throw new Exception("Parkingspot not found inside parkinglot!");
    }

    private static void doesLotHaveSpots(Parkinglot parkinglot) throws Exception {
        if (parkinglot.getSpots() == null || parkinglot.getSpots().isEmpty())
            throw new Exception("Parkinglot has no spots!");
    }
}
