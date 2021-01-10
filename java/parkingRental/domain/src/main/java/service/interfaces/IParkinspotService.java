package service.interfaces;

import model.Parkingspot;
import model.TYPE;

public interface IParkinspotService {
    Parkingspot createParkingspot(int parkinglotid, TYPE type) throws Exception;

    Parkingspot getParkingspotFromParkingLot(int parkinglotid, int parkingspotid) throws Exception;

    Parkingspot updateParkingspot(int parkinglotid, int spotid, TYPE type) throws Exception;
}
