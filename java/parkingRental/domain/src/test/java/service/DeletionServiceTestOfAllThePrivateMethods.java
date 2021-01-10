package service;

import model.Booking;
import model.Parkingspot;
import model.TYPE;
import model.User;
import org.junit.jupiter.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static model.BookingSchedule.dateFormat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeletionServiceTestOfAllThePrivateMethods {
    TestFactoryService tf1;
    TestFactoryService tf2;
    TestFactoryService tf3;
    TestFactoryService tf4;
    Booking booking1;
    Booking booking2;
    Booking booking3;
    Booking booking4;
    Class c;

    @BeforeAll
    void book() throws Exception {
        tf1 = new TestFactoryService();
        tf2 = new TestFactoryService();
        tf3 = new TestFactoryService();
        tf4 = new TestFactoryService();
        c = tf1.iDeletionService.getClass();
        booking1 = tf1.iBookingService.Book(tf1.spot1.getSpotid(), dateFormat.format(tf1.bkdate), 2, tf1.user.getId(), tf1.parkinglot.getId());
        booking2 = tf2.iBookingService.Book(tf2.spot1.getSpotid(), dateFormat.format(tf2.bkdate), 2, tf2.user.getId(), tf2.parkinglot.getId());
        booking3 = tf3.iBookingService.Book(tf3.spot1.getSpotid(), dateFormat.format(tf3.bkdate), 2, tf3.user.getId(), tf3.parkinglot.getId());
        booking4 = tf4.iBookingService.Book(tf4.spot1.getSpotid(), dateFormat.format(tf4.bkdate), 2, tf4.user.getId(), tf4.parkinglot.getId());
    }

    @Test
    void deleteBookingsOfDeletedUserPrivateMethod() throws Exception {
        Method m = c.getDeclaredMethod("deleteBookingsOfDeletedUser", int.class);
        m.setAccessible(true);

        assertEquals(tf1.user, tf1.iBookingService.getBookingByIdService(booking1.getId()).getUser());

        m.invoke(tf1.iDeletionService, booking1.getUser().getId());

        Assertions.assertThrows(Exception.class, () -> {
            tf1.iBookingService.getAllBookings();
        });
    }


    @Test
    void findLotsOfUserToBeDeletedPrivateMethod() throws Exception {
        Method m = c.getDeclaredMethod("findLotsOfUserToBeDeleted", int.class, ArrayList.class);
        m.setAccessible(true);
        ArrayList<Integer> lots = new ArrayList<>();

        m.invoke(tf1.iDeletionService, tf1.parkinglot.getOwner().getId(), lots);

        assertEquals(1, lots.size());
    }

    @AfterAll
    void deleteOwnedParkinglotsOfUserToBeDeletedPrivateMethod() throws Exception {
        Method m = c.getDeclaredMethod("deleteOwnedParkinglotsOfUserToBeDeleted", int.class);
        m.setAccessible(true);

        assertEquals(tf1.user, tf1.iParkinglotService.getParkinglotByIdService(tf1.parkinglot.getId()).getOwner());

        m.invoke(tf1.iDeletionService, tf1.parkinglot.getOwner().getId());

        Assertions.assertThrows(Exception.class, () -> {
            tf1.iParkinglotService.getAllParkinglots();
        });
    }

    @Test
    void findBookingIdOfDeletedSpotPrivateMethod() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method m = c.getDeclaredMethod("findBookingIdOfDeletedSpot", int.class, int.class, ArrayList.class);
        m.setAccessible(true);
        ArrayList<Integer> bookingids = new ArrayList<>();

        m.invoke(tf2.iDeletionService, tf2.parkinglot.getId(), tf2.spot1.getSpotid(), bookingids);
        assertEquals(1, bookingids.size());
        assertEquals((int)bookingids.get(0), booking2.getId());
    }

    @Test
    void iterateAndAddToArrayListPrivateMethod() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method m = c.getDeclaredMethod("iterateAndAddToArrayList", int.class, int.class, ArrayList.class, int.class);
        m.setAccessible(true);
        ArrayList<Integer> bookingids = new ArrayList<>();

        m.invoke(tf2.iDeletionService, tf2.parkinglot.getId(), tf2.spot1.getSpotid(), bookingids, booking2.getId());
        assertEquals(1, bookingids.size());
        assertEquals((int)bookingids.get(0), booking2.getId());
    }

    @Test
    void deleteSpotsFromSchedulePrivateMethodSpotsLeftInLotAfterDeletingSpot() throws Exception {
        Method m = c.getDeclaredMethod("deleteSpotsFromSchedule", int.class, Parkingspot.class);
        m.setAccessible(true);
        Parkingspot spot2 = tf2.iParkingspotService.createParkingspot(tf2.parkinglot.getId(), TYPE.Truck);
        tf2.parkinglot.getSpots().remove(spot2);

        assertNotEquals(1, tf2.iBookingScheduleService.getBookingScheduleByIdService(tf2.parkinglot.getId()).getSchedule().size());
        assertEquals(spot2, tf2.iBookingScheduleService.getBookingScheduleByIdService(tf2.parkinglot.getId()).getSchedule().lastKey());
        m.invoke(tf2.iDeletionService, tf2.parkinglot.getId(), spot2);
        assertEquals(1, tf2.iBookingScheduleService.getBookingScheduleByIdService(tf2.parkinglot.getId()).getSchedule().size());
        assertNotEquals(spot2, tf2.iBookingScheduleService.getBookingScheduleByIdService(tf2.parkinglot.getId()).getSchedule().lastKey());
    }

    @AfterAll
    void deleteSpotsFromSchedulePrivateMethodNoSpotsInLotAfterDeletingSpot() throws Exception {
        Method m = c.getDeclaredMethod("deleteSpotsFromSchedule", int.class, Parkingspot.class);
        m.setAccessible(true);
        tf2.parkinglot.getSpots().remove(tf2.spot1);

        assertNotEquals(0, tf2.iBookingScheduleService.getAllBookingSchedules().size());
        assertEquals(tf2.spot1, tf2.iBookingScheduleService.getBookingScheduleByIdService(tf2.parkinglot.getId()).getSchedule().firstKey());
        m.invoke(tf2.iDeletionService, tf2.parkinglot.getId(), tf2.spot1);

        Assertions.assertThrows(Exception.class, () -> {
            tf2.iBookingScheduleService.getAllBookingSchedules();
        });
    }

    @Test
    void resetStatusToAvailableAfterDeletingBookingPtivateMethod() throws Exception {
        Method m = c.getDeclaredMethod("resetStatusToAvailableAfterDeletingBooking", int.class, Parkingspot.class);
        m.setAccessible(true);

        assertFalse(tf3.iBookingScheduleService.getBookingScheduleByIdService(tf3.parkinglot.getId()).getSchedule().get(tf3.spot1).get(booking3.getDateAndTime().get(0)));
        m.invoke(tf3.iDeletionService, booking3.getId(), tf3.spot1);
        assertTrue(tf3.iBookingScheduleService.getBookingScheduleByIdService(tf3.parkinglot.getId()).getSchedule().get(tf3.spot1).get(booking3.getDateAndTime().get(0)));
    }

    @Test
    void checkIfUserIsAllowedDeleteTheBookingPrivateMethod() throws Exception {
        Method m = c.getDeclaredMethod("checkIfUserIsAllowedDeleteTheBooking", int.class);
        m.setAccessible(true);
        User usr = tf3.iUserService.createUserService("f", "f", "g", "f", 2, 33, "d");
        CurrentUser.loggedInAs = usr;

        Assertions.assertThrows(Exception.class, () -> {
            m.invoke(tf3.iDeletionService, booking3.getId());
        });

        tf3.parkinglot.setOwner(usr);
        m.invoke(tf3.iDeletionService, booking3.getId());

        CurrentUser.loggedInAs = tf3.user;
        m.invoke(tf3.iDeletionService, booking3.getId());
    }

    @Test
    void deleteBookingsRelatedToParkinglotToBeDeletePrivateMethod() throws Exception {
        Method m = c.getDeclaredMethod("deleteBookingsRelatedToParkinglotToBeDelete", int.class);
        m.setAccessible(true);

        assertEquals(booking4, tf4.iBookingService.getBookingByIdService(booking4.getId()));
        assertEquals(tf4.parkinglot, tf4.iBookingService.getBookingByIdService(booking4.getId()).getParkinglot());

        m.invoke(tf4.iDeletionService, tf4.parkinglot.getId());

        Assertions.assertThrows(Exception.class, () -> {
            tf4.iBookingService.getBookingByIdService(booking4.getId());
        });
    }
}
