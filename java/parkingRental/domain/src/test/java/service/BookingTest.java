package service;

import model.Booking;
import model.Parkingspot;
import model.User;
import org.junit.jupiter.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static model.BookingSchedule.dateFormat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingTest {
    TestFactoryService tf;
    Booking booking;
    Class c;
    ConcurrentSkipListMap<Integer, Booking> allBookingsOfUser = new ConcurrentSkipListMap<>();

    @BeforeAll
    void book() throws Exception {
        tf = new TestFactoryService();
        c = tf.iBookingService.getClass();
        booking = tf.iBookingService.Book(tf.spot1.getSpotid(), dateFormat.format(tf.bkdate), 2, tf.user.getId(), tf.parkinglot.getId());
    }


    @Test
    void bookingInfoIsCorrect() throws Exception {
        assertEquals((booking.getDateAndTime().size() * 50), tf.iBookingService.getBookingByIdService(booking.getId()).getPrice());
        assertEquals(booking.getSpot().getSpotid(), tf.spot1.getSpotid());
        assertEquals(booking.getUser(), tf.user);
    }

    @Test
    void getById() throws Exception {
        assertEquals(booking, tf.iBookingService.getBookingByIdService(booking.getId()));
    }

    @Test
    void getAll() throws Exception {
        assertEquals(tf.interfaceCrudBooking.getAll(), tf.iBookingService.getAllBookings());
    }

    @Test
    void findAllBookingsOfUserPrivateMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = c.getDeclaredMethod("findAllBookingsOfUser", ConcurrentSkipListMap.class);

        m.setAccessible(true);

        assertEquals(0, allBookingsOfUser.size());
        m.invoke(tf.iBookingService, allBookingsOfUser);
        assertEquals(booking, allBookingsOfUser.firstEntry().getValue());
    }

    @Test
    void fetchingAllTheBookingsOfOwnedSpotsFailsWhenNoneAvailable() {
        User actualOwner = booking.getParkinglot().getOwner();
        User tempOwner = new User(100, "s", "f", tf.user.getLocation());
        booking.getParkinglot().setOwner(tempOwner);

        Assertions.assertThrows(Exception.class, () -> {
            assertEquals(0, tf.iBookingService.getAllBookingsOfOwnedSpots());
        });

        booking.getParkinglot().setOwner(actualOwner);
    }

    @Test
    void fetchingAllTheBookingsOfOwnedSpots() throws Exception {
        assertEquals(tf.iBookingService.getAllBookings().size(), tf.iBookingService.getAllBookingsOfOwnedSpots().size());
        assertEquals(CurrentUser.loggedInAs, tf.iBookingService.getAllBookingsOfOwnedSpots().firstEntry().getValue().getParkinglot().getOwner());
    }

    @Test
    void getParkingspotToBeBookedFromLotPrivateMethod() throws Exception {
        Method m = c.getDeclaredMethod("getParkingspotToBeBookedFromLot", int.class, int.class);

        m.setAccessible(true);

        assertEquals(tf.spot1, m.invoke(tf.iBookingService, tf.spot1.getSpotid(), tf.parkinglot.getId()));
    }

    @Test
    void errorWhenTryingToBookAlreadyBooked() {
        Assertions.assertThrows(Exception.class, () -> {
            tf.iBookingService.Book(tf.spot1.getSpotid(), booking.getDateAndTime().get(0), 2, tf.user.getId(), tf.parkinglot.getId());
        });
    }

    @Test
    void errorWhenTryingToBookDateAfterLastDateOfSchedule() {
        Assertions.assertThrows(Exception.class, () -> {
            tf.iBookingService.Book(tf.spot1.getSpotid(), dateFormat.format(tf.bkdate), 5, tf.user.getId(), tf.parkinglot.getId());
        });
    }

    @Test
    void exceptionWhenNotFindingBookingToDelete() {
        Assertions.assertThrows(Exception.class, () -> {
            tf.iDeletionService.deleteBookingFromMap(1090);
        });
    }

    @AfterAll
    void delete() throws Exception {
        assertTrue(tf.iBookingService.getAllBookings().containsKey(booking.getId()));

        tf.iDeletionService.deleteBookingFromMap(booking.getId());

        Assertions.assertThrows(Exception.class, () -> {
            tf.iBookingService.getAllBookings();
        });

        availableToBookAgainAfterDeletingBooking();
    }

    void availableToBookAgainAfterDeletingBooking() throws Exception {
        booking = tf.iBookingService.Book(tf.spot1.getSpotid(), dateFormat.format(tf.bkdate), 2, tf.user.getId(), tf.parkinglot.getId());
        assertTrue(tf.iBookingService.getAllBookings().containsKey(booking.getId()));
    }
}
