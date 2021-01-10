package service;

import irepository.InterfaceCrud;
import service.interfaces.IParkinglotService;
import model.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class ParkinglotService implements IParkinglotService {
    private InterfaceCrud<Parkinglot> interfaceCrudLot;
    private InterfaceCrud<BookingSchedule> interfaceCrudSched;
    private int id = 1;

    public ParkinglotService(InterfaceCrud<Parkinglot> interfaceCrudLot, InterfaceCrud<BookingSchedule> interfaceCrudSched) {
        this.interfaceCrudLot = interfaceCrudLot;
        this.interfaceCrudSched = interfaceCrudSched;
    }

    @Override
    public Parkinglot createParkingLotService(Location location, User user) throws Exception {
        Parkinglot lot = new Parkinglot(id, location, user);
        interfaceCrudLot.create(lot);
        id++;
        return lot;
    }

    @Override
    public Parkinglot getParkinglotByIdService(int parkinglotid) throws Exception {
        MapCheckerService.isObjectInTheMap(interfaceCrudLot, parkinglotid);
        return interfaceCrudLot.getById(parkinglotid);
    }

    @Override
    public ConcurrentSkipListMap<Integer, Parkinglot> getAllParkinglots() throws Exception {
        MapCheckerService.isAnythingInTheMap(interfaceCrudLot);
        return interfaceCrudLot.getAll();
    }

    @Override
    public Parkinglot updateParkinglotService(int parkinglotid, String city, String address, int number, int zipcode, String area, User user) throws Exception {
        MapCheckerService.isObjectInTheMap(interfaceCrudLot, parkinglotid);

        CurrentUser.doesUserHaveRightsManager(interfaceCrudLot.getById(parkinglotid).getOwner());

        interfaceCrudLot.getById(parkinglotid).getLocation().setCity(city);
        interfaceCrudLot.getById(parkinglotid).getLocation().setAddress(address);
        interfaceCrudLot.getById(parkinglotid).getLocation().setNumber(number);
        interfaceCrudLot.getById(parkinglotid).getLocation().setZipcode(zipcode);
        interfaceCrudLot.getById(parkinglotid).getLocation().setArea(area);

        updateOwnerIfAdminOrManager(parkinglotid, user);

        return interfaceCrudLot.getById(parkinglotid);
    }

    private void updateOwnerIfAdminOrManager(int parkinglotid, User user) throws Exception {
        if (CurrentUser.checkIfUserIsManagerOrAdmin()) {
            interfaceCrudLot.getById(parkinglotid).setOwner(user);
        }
    }
}
