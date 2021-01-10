package service;

import irepository.InterfaceCrud;
import service.interfaces.IParkinspotService;
import model.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListMap;

public class ParkingspotService implements IParkinspotService {
    private InterfaceCrud<Parkinglot> interfaceCrudLot;
    private InterfaceCrud<BookingSchedule> interfaceCrudSched;

    public ParkingspotService(InterfaceCrud<Parkinglot> interfaceCrudLot, InterfaceCrud<BookingSchedule> interfaceCrudSched) {
        this.interfaceCrudLot = interfaceCrudLot;
        this.interfaceCrudSched = interfaceCrudSched;
    }

    @Override
    public Parkingspot createParkingspot(int parkinglotid, TYPE type) throws Exception {
        MapCheckerService.isObjectInTheMap(interfaceCrudLot, parkinglotid);

        CurrentUser.doesUserHaveRightsManager(interfaceCrudLot.getById(parkinglotid).getOwner());

        Parkingspot spot = addSpotToLot(parkinglotid, type);

        //if we already have a bookingschedule of the parkinglot that the new spot will reside in
        if (interfaceCrudSched.getAll().containsKey(parkinglotid)) {
            addSpotToBookingSchedule(parkinglotid, spot);
        }

        return spot;
    }

    private Parkingspot addSpotToLot(int parkinglotid, TYPE type) {
        Parkingspot spot;
        //if we don't have any parkingspots in our parkinglot, create a new spot with id 1
        if (interfaceCrudLot.getById(parkinglotid).getSpots() == null || interfaceCrudLot.getById(parkinglotid).getSpots().size() == 0) {
            //create a new parkingspot from the parameter values
            spot = firstSpotofLot(parkinglotid, type);
        } else {
            spot = addAnotherSpotToLot(parkinglotid, type);
        }
        return spot;
    }

    private Parkingspot firstSpotofLot(int parkinglotid, TYPE type) {
        Parkingspot spot;
        spot = new Parkingspot(1, type);
        //add the newly created parkingspot to the arraylist of parkingspots
        interfaceCrudLot.getById(parkinglotid).setSpots(new ArrayList<>(Arrays.asList(spot)));
        return spot;
    }

    private Parkingspot addAnotherSpotToLot(int parkinglotid, TYPE type) {
        Parkingspot spot;
        int idOfLastSpot = interfaceCrudLot.getById(parkinglotid).getSpots().get(interfaceCrudLot.getById(parkinglotid).getSpots().size()-1).getSpotid();
        //create a new parkingspot from the parameter values
        spot = new Parkingspot((idOfLastSpot + 1), type);
        //add the newly created parkingspot to the arraylist of parkingspots for the parkinglot
        interfaceCrudLot.getById(parkinglotid).getSpots().add(spot);
        return spot;
    }

    private void addSpotToBookingSchedule(int parkinglotid, Parkingspot spot) {
        ConcurrentSkipListMap<String, Boolean> schedule = new ConcurrentSkipListMap<>();
        //loop over the dates and times of the current parkingspot
        addAllTheDatesToScheduleOfSpot(parkinglotid, schedule);
        //put the newly created spot and its schedule inside the schedule
        interfaceCrudSched.getById(parkinglotid).getSchedule().put(spot, schedule);
    }

    private void addAllTheDatesToScheduleOfSpot(int parkinglotid, ConcurrentSkipListMap<String, Boolean> schedule) {
        Iterator<String> iterator2 = interfaceCrudSched.getById(parkinglotid).getSchedule().firstEntry().getValue().keySet().iterator();
        while (iterator2.hasNext()) {
            //put the dates and times and the boolean value true, meaning the spot is available, inside the TreeMap schedule
            schedule.put(iterator2.next(), true);
        }
    }

    @Override
    public Parkingspot getParkingspotFromParkingLot(int parkinglotid, int parkingspotid) throws Exception {
        MapCheckerService.isObjectInTheMap(interfaceCrudLot, parkinglotid);

        MapCheckerService.isSpotInLot(interfaceCrudLot.getById(parkinglotid), parkingspotid);

        Parkingspot spot = null;

        for (Parkingspot pspot : interfaceCrudLot.getById(parkinglotid).getSpots()) {
            if (pspot.getSpotid() == parkingspotid) {
                spot = pspot;
            }
        }
        return spot;
    }

    @Override
    public Parkingspot updateParkingspot(int parkinglotid, int spotid, TYPE type) throws Exception {
        MapCheckerService.isObjectInTheMap(interfaceCrudLot, parkinglotid);

        MapCheckerService.isSpotInLot(interfaceCrudLot.getById(parkinglotid), spotid);

        CurrentUser.doesUserHaveRightsManager(interfaceCrudLot.getById(parkinglotid).getOwner());

        getParkingspotFromParkingLot(parkinglotid, spotid).setType(type);

        return getParkingspotFromParkingLot(parkinglotid, spotid);
    }
}
