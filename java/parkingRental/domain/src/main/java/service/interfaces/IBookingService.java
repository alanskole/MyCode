package service.interfaces;

import model.Booking;
import java.io.File;
import java.util.concurrent.ConcurrentSkipListMap;

public interface IBookingService {
    Booking getBookingByIdService(int bookingid) throws Exception;

    ConcurrentSkipListMap<Integer, Booking> getAllBookings() throws Exception;

    //gets all the bookings of the users parkingspots
    ConcurrentSkipListMap<Integer, Booking> getAllBookingsOfOwnedSpots() throws Exception;

    Booking Book(int spot, String bkdate, int hours, int userid, int parkinglotid) throws Exception;

    void writeBookingsToCsv(File out) throws Exception;
}
