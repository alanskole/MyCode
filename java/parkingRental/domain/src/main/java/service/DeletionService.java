package service;

import irepository.InterfaceCrud;
import service.interfaces.IDeletionService;
import model.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class DeletionService implements IDeletionService {
    private InterfaceCrud<Booking> interfaceCrudBooking;
    private InterfaceCrud<BookingSchedule> interfaceCrudSchedule;
    private InterfaceCrud<User> interfaceCrudUser;
    private InterfaceCrud<Parkinglot> interfaceCrudParkinglot;

    public DeletionService(InterfaceCrud<Booking> interfaceCrudBooking, InterfaceCrud<BookingSchedule> interfaceCrudSchedule, InterfaceCrud<User> interfaceCrudUser, InterfaceCrud<Parkinglot> interfaceCrudParkinglot) {
        this.interfaceCrudBooking = interfaceCrudBooking;
        this.interfaceCrudSchedule = interfaceCrudSchedule;
        this.interfaceCrudUser = interfaceCrudUser;
        this.interfaceCrudParkinglot = interfaceCrudParkinglot;
    }

    @Override
    public void deleteUserFromMap(int userid) throws Exception {
        CurrentUser.doesUserHaveRightsAdmin(interfaceCrudUser.getById(userid));

        MapCheckerService.isObjectInTheMap(interfaceCrudUser, userid);

        deleteBookingsOfDeletedUser(userid);

        //method to delete the parkinglots the user owns
        deleteOwnedParkinglotsOfUserToBeDeleted(userid);

        //remove the user from the map of all users
        interfaceCrudUser.delete(userid);

        logoutAfterDeletingUser(userid);
    }

    private void deleteBookingsOfDeletedUser(int userid) throws Exception {
        Iterator<Integer> iterator = interfaceCrudBooking.getAll().keySet().iterator();
        while(iterator.hasNext()){
            //setting i to be the bookingid of the current booking
            int i = iterator.next();
            //find the bookings made by the user about to be deleted
            if (interfaceCrudBooking.getAll().get(i) != null && interfaceCrudBooking.getById(i).getUser().getId() == userid){
                //calling the method deleteBookingFromMap to delete the bookings made by the user
                deleteBookingFromMap(interfaceCrudBooking.getById(i).getId());
            }
        }
    }

    private void deleteOwnedParkinglotsOfUserToBeDeleted(int userid) throws Exception {
        ArrayList<Integer> lots = new ArrayList<>();
        findLotsOfUserToBeDeleted(userid, lots);

        //if the map is populated with anything, meaning the user owned some lots
        if (lots.size() > 0) {
            for (int j = 0; j < lots.size(); j++) {
                //delete the parkinglots using the deleteParkinglotFromMap method
                deleteParkinglotFromMap(lots.get(j));
            }
        }
    }

    private void findLotsOfUserToBeDeleted(int userid, ArrayList<Integer> lots) {
        //iterate over all the parkinglots in the ConcurrentSkipListMap with all the parkinglots
        Iterator<Integer> iterator = interfaceCrudParkinglot.getAll().keySet().iterator();
        while(iterator.hasNext()){
            //value of the int i will hold the value of the key of the map, meaning the parkinglotid in this case
            int i = iterator.next();
            //if the user to be deleted owns parkinglots
            if (interfaceCrudParkinglot.getById(i).getOwner().getId() == userid) {
                //add them to the arraylist of the parkinglots to be deleted
                lots.add(interfaceCrudParkinglot.getById(i).getId());
            }
        }
    }

    private void logoutAfterDeletingUser(int userid) {
        //logout if the deleted user is the logged in user
        if (CurrentUser.loggedInAs.getId() == userid)
            CurrentUser.loggedInAs = null;
    }

    @Override
    public void deleteParkingspot(int parkinglotid, int spotid) throws Exception {
        MapCheckerService.isObjectInTheMap(interfaceCrudParkinglot, parkinglotid);

        MapCheckerService.isSpotInLot(interfaceCrudParkinglot.getById(parkinglotid), spotid);

        Parkingspot ps = new Parkingspot();
        //find the parkingspot inside the parkinglot
        for (Parkingspot pspot : interfaceCrudParkinglot.getById(parkinglotid).getSpots()) {
            if (pspot.getSpotid() == spotid) {
                CurrentUser.doesUserHaveRightsManager(interfaceCrudParkinglot.getById(parkinglotid).getOwner());
                ps = pspot;
            }
        }

        //remove the spot from the parkinglot
        interfaceCrudParkinglot.getById(parkinglotid).getSpots().remove(ps);

        //method to delete the bookings related to the deleted parkingspot
        deleteBookingsRelatedToSpotsToBeDeleted(parkinglotid, spotid);

        deleteSpotFromLotInSchedule(parkinglotid, ps);
    }

    private void deleteBookingsRelatedToSpotsToBeDeleted(int parkinglotid, int spotid) throws Exception {
        ArrayList<Integer> bookingids = new ArrayList<>();
        findBookingIdOfDeletedSpot(parkinglotid, spotid, bookingids);
        //if that arraylist isn't empty it means that some bookings must be deleted
        if (!bookingids.isEmpty()) {
            //loop over all the bookingids and call the method deleteBookingFromMap on them
            for (int i = 0; i < bookingids.size(); i++) {
                deleteBookingFromMap(bookingids.get(i));
            }
        }
    }

    private void findBookingIdOfDeletedSpot(int parkinglotid, int spotid, ArrayList<Integer> bookingids) {
        Iterator<Integer> iterator = interfaceCrudBooking.getAll().keySet().iterator();
        while(iterator.hasNext()){
            //set the value of the int to be the bookingid, the key of the ConcurrentSkipListMap bookings
            int bid = iterator.next();
            iterateAndAddToArrayList(parkinglotid, spotid, bookingids, bid);
        }
    }

    private void iterateAndAddToArrayList(int parkinglotid, int spotid, ArrayList<Integer> bookingids, int bid) {
        //find the bookings related to the parkingspot to be deleted
        if (interfaceCrudBooking.getById(bid).getParkinglot().getId() == parkinglotid && interfaceCrudBooking.getAll().get(bid).getSpot().getSpotid() == spotid) {
            //put the bookingids in an arraylist called bookingids
            bookingids.add(interfaceCrudBooking.getById(bid).getId());
        }
    }

    private void deleteSpotFromLotInSchedule(int parkinglotid, Parkingspot ps) {
        if (interfaceCrudSchedule.getAll().containsKey(parkinglotid)) {
            deleteSpotsFromSchedule(parkinglotid, ps);
        }
    }

    //to exlude the deleted spots from the bookingschedule of that parkinglot
    private void deleteSpotsFromSchedule(int parkinglotid, Parkingspot spot) {
        //if there are no more parkingspots in the parkinglot after deleting spots, remove the bookingschedule
        if (interfaceCrudSchedule.getById(parkinglotid).getParkinglot().getSpots().size() == 0) {
            interfaceCrudSchedule.delete(parkinglotid);
        } else
            interfaceCrudSchedule.getById(parkinglotid).getSchedule().remove(spot);
    }

    @Override
    public void deleteBookingFromMap(int bookingnumber) throws Exception {
        MapCheckerService.isObjectInTheMap(interfaceCrudBooking, bookingnumber);

        checkIfUserIsAllowedDeleteTheBooking(bookingnumber);

        Parkingspot spot = interfaceCrudBooking.getById(bookingnumber).getSpot();

        resetStatusToAvailableAfterDeletingBooking(bookingnumber, spot);

        //delete the booking from the ConcurrentSkipListMap bookings
        interfaceCrudBooking.delete(bookingnumber);
    }

    private void resetStatusToAvailableAfterDeletingBooking(int bookingnumber, Parkingspot spot) {
        TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule = interfaceCrudSchedule.getById(interfaceCrudBooking.getById(bookingnumber).getParkinglot().getId()).getSchedule();

        //iterate over the schedule while it has more entries
        Iterator<String> iterator = schedule.get(spot).keySet().iterator();
        while (iterator.hasNext()) {
            String dateAndTime = iterator.next();
            //match the bookingdate and the date inside the schedule
            if (interfaceCrudBooking.getById(bookingnumber).getDateAndTime().contains(dateAndTime)) {
                //set the spot that was cancelled to available so it can be booked again
                schedule.get(spot).put(dateAndTime, true);
            }
        }
    }

    private void checkIfUserIsAllowedDeleteTheBooking(int bookingnumber) throws Exception {
        if (interfaceCrudBooking.getById(bookingnumber).getUser() != CurrentUser.loggedInAs)
            CurrentUser.doesUserHaveRightsManager(interfaceCrudBooking.getById(bookingnumber).getParkinglot().getOwner());
    }

    @Override
    public void deleteParkinglotFromMap(int parkinglotid) throws Exception {
        CurrentUser.doesUserHaveRightsManager(interfaceCrudParkinglot.getById(parkinglotid).getOwner());

        MapCheckerService.isObjectInTheMap(interfaceCrudParkinglot, parkinglotid);

        interfaceCrudParkinglot.delete(parkinglotid);

        deleteBookingsRelatedToParkinglotToBeDelete(parkinglotid);

        deleteLotFromSchedule(parkinglotid);
    }

    private void deleteLotFromSchedule(int parkinglotid) {
        if (interfaceCrudSchedule.getAll().containsKey(parkinglotid))
            interfaceCrudSchedule.delete(parkinglotid);
    }

    private void deleteBookingsRelatedToParkinglotToBeDelete(int parkinglotid) throws Exception {
        Iterator<Integer> iteratorBook = interfaceCrudBooking.getAll().keySet().iterator();
        //iterate over all the bookings inside the ConcurrentSkipListMap bookings and runs a while loop until it's reached the end
        while(iteratorBook.hasNext()){
            //set the value of the variable i to be the bookingid since that's the key of the ConcurrentSkipListMap bookings
            int i = iteratorBook.next();
            //find the bookings related to the parkinglot to be deleted
            if (interfaceCrudBooking.getById(i).getParkinglot().getId() == parkinglotid){
                //call the method deleteBookingFromMap to delete the bookings related to the parkinglot
                deleteBookingFromMap(i);
            }
        }
    }
}
