package service;

import irepository.InterfaceCrud;
import service.interfaces.IBookingScheduleService;
import model.*;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import static model.BookingSchedule.dateFormat;


public class BookingScheduleService implements IBookingScheduleService {
    private InterfaceCrud<BookingSchedule> interfaceCrudSchedule;
    private BookingSchedule onlyAvailable;

    public BookingScheduleService(InterfaceCrud<BookingSchedule> interfaceCrudSchedule) {
        this.interfaceCrudSchedule = interfaceCrudSchedule;
    }

    @Override
    public BookingSchedule getOnlyAvailable() {
        return onlyAvailable;
    }

    @Override
    public BookingSchedule getBookingScheduleByIdService(int bookingscheduleid) throws Exception {
        MapCheckerService.isObjectInTheMap(interfaceCrudSchedule, bookingscheduleid);
        return interfaceCrudSchedule.getById(bookingscheduleid);
    }

    @Override
    public ConcurrentSkipListMap<Integer, BookingSchedule> getAllBookingSchedules() throws Exception {
        MapCheckerService.isAnythingInTheMap(interfaceCrudSchedule);
        return interfaceCrudSchedule.getAll();
    }

    @Override
    public void deleteBookingScheduleService(int bookingscheduleid) throws Exception {
        CurrentUser.doesUserHaveRightsManager(interfaceCrudSchedule.getById(bookingscheduleid).getParkinglot().getOwner());
        MapCheckerService.isObjectInTheMap(interfaceCrudSchedule, bookingscheduleid);
        interfaceCrudSchedule.delete(bookingscheduleid);
    }

    @Override
    public BookingSchedule createForXDays(Parkinglot parkinglot, int days) throws Exception {
        ifValid(parkinglot);

        CurrentUser.doesUserHaveRightsManager(parkinglot.getOwner());

        //standard value of days is 1 if the value is below one or null
        if (days < 1)
            days = 1;

        //if there is already a schedule for this parkinglot add to the existing one
        if (interfaceCrudSchedule.getAll().containsKey(parkinglot.getId())) {
            return addToExistingSchedule(parkinglot, days);
        } else
            return newSchedule(parkinglot, days);
    }

    private BookingSchedule addToExistingSchedule(Parkinglot parkinglot, int days) throws Exception {
        Date lastDateAndHourOfTheSchedule = dateFormat.parse(getBookingScheduleByIdService(parkinglot.getId()).getSchedule().firstEntry().getValue().lastKey());

        //this is to start adding the new values after the last available date in the schedule
        lastDateAndHourOfTheSchedule = DateCheckerService.addHoursToAnyTime(lastDateAndHourOfTheSchedule, 1);

        //iterate over the parkingspots while there are more spots
        Iterator<Parkingspot> iterator = getBookingScheduleByIdService(parkinglot.getId()).getSchedule().keySet().iterator();
        while(iterator.hasNext()){
            //spot is set to the current parkingspot being iterated over
            Parkingspot spot = iterator.next();
            //loop for 24Xdays based on how many days the schedule should be for
            for (int hours = 0; hours < 24 * days; hours++) {
                //add as many hours as the value of the variable hours to current time by using the addHoursToCurrentTime method
                Date dt = DateCheckerService.addHoursToAnyTime(lastDateAndHourOfTheSchedule, hours);
                //add the date and time and the boolean value true meaning the spot is available the the existing schedule
                getBookingScheduleByIdService(parkinglot.getId()).getSchedule().get(spot).put(dateFormat.format(dt), true);
            }
        }
        return getBookingScheduleByIdService(parkinglot.getId());
    }

    private BookingSchedule newSchedule(Parkinglot parkinglot, int days) {
        //using a TreeMap instead of a ConcurrentSkipListMap because the latter won't differentiate
        //between the different parkingspots even with comparable implemented inside the Parkingspot class
        TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule = new TreeMap<>();

        Date now = new Date();
        //loop over each parkingspot inside the parkinglot
        for (Parkingspot spot : parkinglot.getSpots()) {
            ConcurrentSkipListMap<String, Boolean> dateAndStatus = new ConcurrentSkipListMap<>();
            //loop for 24Xdays based on how many days the schedule should be for
            for (int hours = 0; hours < 24 * days; hours++) {
                //add as many hours as the value of the variable hours to current time by using the addHoursToCurrentTime method
                Date c = DateCheckerService.addHoursToAnyTime(now, hours);
                //add the date and time and the boolean value true since the spot is available
                dateAndStatus.put(dateFormat.format(c), true);
                //put the parkingspot and the ConcurrentSkipListMap dateAndStatus inside the TreeMap schedule
                schedule.put(spot, dateAndStatus);
            }
        }
        //create a new BookingSchedule sch with the values of the parkinglot and the schedule created above
        BookingSchedule sch = new BookingSchedule(parkinglot, schedule);
        //put values inside the map with all the bookingschedules
        interfaceCrudSchedule.create(sch);
        return sch;
    }


    private void ifValid(Parkinglot parkinglot) throws Exception {
        if (parkinglot.getSpots() == null)
            throw new Exception("Parkinglot is not a valid lot!");
    }

    @Override
    public BookingSchedule getOnlyAvailableMap(int parkinglotid, String dateAndTime, int hours, TYPE type) throws Exception {
        MapCheckerService.isObjectInTheMap(interfaceCrudSchedule, parkinglotid);

        TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule = new TreeMap<>();

        ArrayList<String> listOfDates = new ArrayList<>();

        getListOfDates(parkinglotid, dateAndTime, hours, listOfDates);

        ConcurrentSkipListMap<String, Boolean> availableDateAndTime = new ConcurrentSkipListMap<>();

        typeChecker(type, parkinglotid, schedule, listOfDates, availableDateAndTime);

        onlyAvailable = new BookingSchedule();
        //set the parkinglot of bookingschedule schd to the current parkinglot
        onlyAvailable.setParkinglot(interfaceCrudSchedule.getById(parkinglotid).getParkinglot());
        //set the schedule of bookingschedule schd to the correct one schedule
        onlyAvailable.setSchedule(schedule);
        return onlyAvailable;
    }

    private void getListOfDates(int parkinglotid, String dateAndTime, int hours, ArrayList<String> listOfDates) throws Exception {
        Date searchDates;
        //loop for as many hours as necessary
        for (int i = 0; i < hours; i++) {
            searchDates = getSearchDates(dateAndTime, i);

            //uses method in DateCheckerService to make sure the date and time aren't too old or after the last date in the schedule
            DateCheckerService.dateIsInRange(searchDates, getBookingScheduleByIdService(parkinglotid).getSchedule().firstEntry().getValue().lastKey());

            //create a formatted string from the date
            String date = dateFormat.format(searchDates);
            //add it to the list with dates and times to get available spots for
            listOfDates.add(date);
        }
    }

    private Date getSearchDates(String dateAndTime, int i) throws Exception {
        if (i == 0) {
            //no need to add any hours on the first iteration
            return dateFormat.parse(dateAndTime);
        } else {
            //uses method in DateCheckerService to return the original time plus the amount of hours added to it
            return DateCheckerService.addHoursToAnyTime(dateFormat.parse(dateAndTime), i);
        }
    }

    private void typeChecker(TYPE type, int parkinglotid, TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule, ArrayList<String> listOfDates, ConcurrentSkipListMap<String, Boolean> availableDateAndTime) throws Exception {
        if (type == null)
            getOnlyAvailAllTypes(parkinglotid, schedule, listOfDates, availableDateAndTime);
        else
            getOnlyAvailOneType(type, parkinglotid, schedule, listOfDates, availableDateAndTime);
    }

    private void getOnlyAvailAllTypes(int parkinglotid, TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule, ArrayList<String> listOfDates, ConcurrentSkipListMap<String, Boolean> availableDateAndTime) throws Exception {
        Iterator<Parkingspot> iterator = getBookingScheduleByIdService(parkinglotid).getSchedule().keySet().iterator();
        while (iterator.hasNext()) {
            //spot is set to be the current parkingspot
            Parkingspot spot = iterator.next();
            findAvailable(parkinglotid, schedule, listOfDates, availableDateAndTime, spot);
        }
    }

    private void getOnlyAvailOneType(TYPE type, int parkinglotid, TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule, ArrayList<String> listOfDates, ConcurrentSkipListMap<String, Boolean> availableDateAndTime) throws Exception {
        Iterator<Parkingspot> iterator = getBookingScheduleByIdService(parkinglotid).getSchedule().keySet().iterator();
        while (iterator.hasNext()) {
            //spot is set to be the current parkingspot
            Parkingspot spot = iterator.next();
            if (spot.getType() == type) {
                findAvailable(parkinglotid, schedule, listOfDates, availableDateAndTime, spot);
                break;
            }
        }
    }

    public void findAvailable(int parkinglotid, TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule, ArrayList<String> listOfDates, ConcurrentSkipListMap<String, Boolean> availableDateAndTime, Parkingspot spot) {
        //loop over all the dates and times to get available spots for
        for (int i = 0; i < listOfDates.size(); i++) {
            findDateInTheSchedule(parkinglotid, schedule, listOfDates, availableDateAndTime, spot, i);
        }
    }

    private void findDateInTheSchedule(int parkinglotid, TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule, ArrayList<String> listOfDates, ConcurrentSkipListMap<String, Boolean> availableDateAndTime, Parkingspot spot, int i) {
        //find the dates inside the schedule
        if (interfaceCrudSchedule.getById(parkinglotid).getSchedule().get(spot).containsKey(listOfDates.get(i))) {
            addSpotToMapIfAvailable(parkinglotid, schedule, listOfDates, availableDateAndTime, spot, i);
        }
    }

    private void addSpotToMapIfAvailable(int parkinglotid, TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule, ArrayList<String> listOfDates, ConcurrentSkipListMap<String, Boolean> availableDateAndTime, Parkingspot spot, int i) {
        //if the spot is available
        if (interfaceCrudSchedule.getById(parkinglotid).getSchedule().get(spot).get(listOfDates.get(i))) {
            //put the date and time and the boolean value true in the ConcurrentSkipListMap availableDateAndTime
            availableDateAndTime.put(listOfDates.get(i), true);
            //put the parkingspot and the available date and time inside the TreeMap schedule
            schedule.put(spot, availableDateAndTime);
        }
    }
}
