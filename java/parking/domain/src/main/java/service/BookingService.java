package service;

import irepository.InterfaceCrud;
import service.interfaces.IBookingService;
import model.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import static model.BookingSchedule.dateFormat;

public class BookingService implements IBookingService {
    private InterfaceCrud<Booking> interfaceCrudBooking;
    private InterfaceCrud<User> interfaceCrudUser;
    private InterfaceCrud<BookingSchedule> interfaceCrudSchedule;
    private int id = 1;

    public BookingService(InterfaceCrud<Booking> interfaceCrudBooking, InterfaceCrud<User> interfaceCrudUser, InterfaceCrud<BookingSchedule> interfaceCrudSchedule) {
        this.interfaceCrudBooking = interfaceCrudBooking;
        this.interfaceCrudUser = interfaceCrudUser;
        this.interfaceCrudSchedule = interfaceCrudSchedule;
    }

    @Override
    public Booking getBookingByIdService(int bookingid) throws Exception {
        MapCheckerService.isObjectInTheMap(interfaceCrudBooking, bookingid);
        CurrentUser.doesUserHaveRightsManager(interfaceCrudBooking.getById(bookingid).getUser());
        return interfaceCrudBooking.getById(bookingid);
    }

    @Override
    public ConcurrentSkipListMap<Integer, Booking> getAllBookings() throws Exception {
        ConcurrentSkipListMap<Integer, Booking> allBookingsOfUser = new ConcurrentSkipListMap<>();

        MapCheckerService.isAnythingInTheMap(interfaceCrudBooking);

        //if the logged in user is manager or admin return all the available bookings
        if (CurrentUser.checkIfUserIsManagerOrAdmin()) {
            return interfaceCrudBooking.getAll();
        } else {
            //else find all the users bookings and them to the ConcurrentSkipListMap allBookingsOfUser
            findAllBookingsOfUser(allBookingsOfUser);
            //return if user has bookings
            ifUserHasBookings(allBookingsOfUser);
            return allBookingsOfUser;
        }
    }

    private void ifUserHasBookings(ConcurrentSkipListMap<Integer, Booking> allBookingsOfUser) throws Exception {
        if (allBookingsOfUser.isEmpty())
            throw new Exception("User doesn't have any bookings!");
    }

    private void findAllBookingsOfUser(ConcurrentSkipListMap<Integer, Booking> allBookingsOfUser) {
        Iterator<Integer> iterator = interfaceCrudBooking.getAll().keySet().iterator();
        while (iterator.hasNext()) {
            int id = iterator.next();
            if (interfaceCrudBooking.getById(id).getUser() == CurrentUser.loggedInAs) {
                allBookingsOfUser.put(interfaceCrudBooking.getById(id).getId(), interfaceCrudBooking.getById(id));
            }
        }
    }

    //gets all the bookings of the user's parkingspots
    @Override
    public ConcurrentSkipListMap<Integer, Booking> getAllBookingsOfOwnedSpots() throws Exception {
        ConcurrentSkipListMap<Integer, Booking> allBookingsOfUser = new ConcurrentSkipListMap<>();
        MapCheckerService.isAnythingInTheMap(interfaceCrudBooking);
        iterateToFindBookings(allBookingsOfUser);
        ifUserHasBookings(allBookingsOfUser);
        return allBookingsOfUser;
    }

    private void iterateToFindBookings(ConcurrentSkipListMap<Integer, Booking> allBookingsOfUser) {
        //iterate over all the bookings
        Iterator<Integer> iterator = interfaceCrudBooking.getAll().keySet().iterator();
        while (iterator.hasNext()) {
            int id = iterator.next();
            ifParkinglotOwnerIsTheUserLoggedIn(allBookingsOfUser, id);
        }
    }

    private void ifParkinglotOwnerIsTheUserLoggedIn(ConcurrentSkipListMap<Integer, Booking> allBookingsOfUser, int id) {
        //if the booking is made on a parkinglot owned by the user
        if (interfaceCrudBooking.getById(id).getParkinglot().getOwner() == CurrentUser.loggedInAs) {
            allBookingsOfUser.put(interfaceCrudBooking.getById(id).getId(), interfaceCrudBooking.getById(id));
        }
    }

    @Override
    public Booking Book(int spot, String bkdate, int hours, int userid, int parkinglotid) throws Exception {
        CurrentUser.doesUserHaveRightsManager(interfaceCrudUser.getById(userid));

        MapCheckerService.isObjectInTheMap(interfaceCrudSchedule, parkinglotid);

        //TreeMap with the bookingschedule of the parkinglot
        TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule = new TreeMap<>(interfaceCrudSchedule.getById(parkinglotid).getSchedule());

        //will count how many hours will be charged
        int hourlyPrice = 50;
        int hoursBooked = 0;

        //minimum one hour
        if (hours < 1)
            hours = 1;

        ArrayList<String> availableDates = new ArrayList<>();
        ArrayList<String> unavailableDates = new ArrayList<>();

        Date bookingdate;

        //find the spot the user wants to book
        Parkingspot spt = getParkingspotToBeBookedFromLot(spot, parkinglotid);

        //loop for as many times as the amount of hours wished to book
        for (int k = 0; k < hours; k++) {
            //adds k amount of hours to the current time
            bookingdate = DateCheckerService.addHoursToAnyTime(dateFormat.parse(bkdate), k);

            //uses method in DateCheckerService to make sure the date and time isn't too old or after the last date in the schedule
            DateCheckerService.dateIsInRange(bookingdate, interfaceCrudSchedule.getById(parkinglotid).getSchedule().firstEntry().getValue().lastKey());

            //format the date to a string
            String date = dateFormat.format(bookingdate);

            hoursBooked = bookIfAvailable(schedule, hoursBooked, availableDates, unavailableDates, spt, date);
        }
        someUnavail(schedule, availableDates, unavailableDates, spt);

        return getBooking(userid, parkinglotid, hourlyPrice, hoursBooked, availableDates, spt);
    }

    private Parkingspot getParkingspotToBeBookedFromLot(int spot, int parkinglotid) throws Exception {
        MapCheckerService.isSpotInLot(interfaceCrudSchedule.getById(parkinglotid).getParkinglot(), spot);

        Parkingspot spt = null;

        Iterator<Parkingspot> iterator = interfaceCrudSchedule.getById(parkinglotid).getSchedule().keySet().iterator();
        while (iterator.hasNext()){
            Parkingspot sp = iterator.next();
            if (sp.getSpotid() == spot) {
                spt = sp;
            }
        }
        return spt;
    }

    private int bookIfAvailable(TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule, int hoursBooked, ArrayList<String> availableDates, ArrayList<String> unavailableDates, Parkingspot spt, String date) {
        //if the date and time of the schedule for the parkingspot matches the booking request date
        if (schedule.get(spt).containsKey(date)) {
            //if the spot is available
            if (schedule.get(spt).get(date) == true) {
                //set it to unavaiable
                schedule.get(spt).put(date, false);
                //increase the hours booked counter
                hoursBooked++;
                //add the dates to the ArrayList with all the spotids of the booking
                availableDates.add(date);
            } else
                unavailableDates.add(date);
        }
        return hoursBooked;
    }

    private void someUnavail(TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule, ArrayList<String> availableDates, ArrayList<String> unavailableDates, Parkingspot spt) throws Exception {
        if (unavailableDates.isEmpty() == false) {
            if (availableDates.isEmpty() == false) {
                for (String dates : availableDates) {
                    schedule.get(spt).put(dates, true);
                }
            }
            throw new Exception("Spot unavaialble at " + unavailableDates);
        }
    }

    private Booking getBooking(int userid, int parkinglotid, int hourlyPrice, int hoursBooked, ArrayList<String> availableDates, Parkingspot spt) throws Exception {
        ifDatesFound(hoursBooked);

        Booking booking = new Booking(id, interfaceCrudUser.getById(userid), spt, availableDates, interfaceCrudSchedule.getById(parkinglotid).getParkinglot());
        //multiply the amount of hours with the hourly price of 50
        booking.setPrice((hoursBooked * hourlyPrice));
        //put the booking inside the ConcurrentSkipListMap bookings
        interfaceCrudBooking.create(booking);
        id++;
        return booking;
    }

    private void ifDatesFound(int hoursBooked) throws Exception {
        if (hoursBooked < 0)
            throw new Exception("Dates not found");
    }

    @Override
    public void writeBookingsToCsv(File out) throws Exception {
        CurrentUser.isAdmin();
        MapCheckerService.isAnythingInTheMap(interfaceCrudBooking);
        //will be used to store the total amount of money received in total for all the bookings
        int totalPrice = 0;
        //will be used to store the total amount of orders
        int amountOfBookings = 0;
        //will be used as to contain the string that will be written to the csv
        String joiner;
        try (FileWriter fileWriter = new FileWriter(out)) {
            //for loop over all the bookings
            for (Map.Entry<Integer, Booking> hmap : interfaceCrudBooking.getAll().entrySet()) {
                //add all those values to the string that will be written to the csv file
                joiner = hmap.getValue().getId() + "," + hmap.getValue().getUser().getId() + "," + hmap.getValue().getUser().getFirstname() + "," + hmap.getValue().getUser().getSurname() + "," + hmap.getValue().getSpot().getSpotid() + ",";
                //dateAndTime is an arraylist, so loop over and print all the elements with a comma between them
                joiner = writeDateAndTimeFromArrayList(joiner, hmap);
                //add the rest of the values of the booking to the string
                joiner += hmap.getValue().getParkinglot().getId() + "," + hmap.getValue().getParkinglot().getLocation().getCity() + "," + hmap.getValue().getParkinglot().getLocation().getAddress() + "," + hmap.getValue().getParkinglot().getLocation().getNumber() + "," + hmap.getValue().getParkinglot().getLocation().getNumber() + "," + hmap.getValue().getParkinglot().getLocation().getZipcode() + "," + hmap.getValue().getParkinglot().getLocation().getArea() + "," + hmap.getValue().getPrice() + "\n";
                //add the price of each booking to the total
                totalPrice += hmap.getValue().getPrice();
                //increase the amount of bookings by one
                amountOfBookings++;
                //write the string to the csv
                fileWriter.write(joiner);
            }
            totalBookings(totalPrice, amountOfBookings, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String writeDateAndTimeFromArrayList(String joiner, Map.Entry<Integer, Booking> hmap) {
        for (int j = 0; j < hmap.getValue().getDateAndTime().size(); j++) {
            joiner += hmap.getValue().getDateAndTime().get(j) + ",";
        }
        return joiner;
    }

    private void totalBookings(int totalPrice, int amountOfBookings, FileWriter fileWriter) throws IOException {
        String joiner;
        joiner = amountOfBookings + "," + totalPrice;
        fileWriter.write(joiner);
    }
}
