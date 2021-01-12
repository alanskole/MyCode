package service;

import irepository.InterfaceCrud;
import model.BookingSchedule;
import model.Parkingspot;
import model.TYPE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class BookingScheduleServiceAllTypes extends BookingScheduleService {

    public BookingScheduleServiceAllTypes(InterfaceCrud<BookingSchedule> interfaceCrudSchedule) {
        super(interfaceCrudSchedule);
    }

    @Override
    public void getOnlyAvailBasedOnType(TYPE type, int parkinglotid, TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule, ArrayList<String> listOfDates, ConcurrentSkipListMap<String, Boolean> availableDateAndTime) throws Exception {
        Iterator<Parkingspot> iterator = getBookingScheduleByIdService(parkinglotid).getSchedule().keySet().iterator();
        while (iterator.hasNext()) {
            //spot is set to be the current parkingspot
            Parkingspot spot = iterator.next();
            findAvailable(parkinglotid, schedule, listOfDates, availableDateAndTime, spot);
        }
    }
}
