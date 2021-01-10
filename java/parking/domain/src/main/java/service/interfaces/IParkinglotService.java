package service.interfaces;

import model.Location;
import model.Parkinglot;
import model.User;
import java.util.concurrent.ConcurrentSkipListMap;

public interface IParkinglotService {
    Parkinglot createParkingLotService(Location location, User user) throws Exception;

    Parkinglot getParkinglotByIdService(int parkinglotid) throws Exception;

    ConcurrentSkipListMap<Integer, Parkinglot> getAllParkinglots() throws Exception;

    Parkinglot updateParkinglotService(int parkinglotid, String city, String address, int number, int zipcode, String area, User user) throws Exception;
}
