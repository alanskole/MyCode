package service;

import org.junit.jupiter.api.*;

import model.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentSkipListMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParkingspotTest {
    TestFactoryService tf;
    Class c;
    Parkinglot parkinglot2;
    Parkingspot parkingspot2;

    @BeforeAll
    void create() throws Exception {
        tf = new TestFactoryService();
        tf.book();
        c = tf.iParkingspotService.getClass();
        parkinglot2 = tf.iParkinglotService.createParkingLotService(tf.parkinglot.getLocation(), tf.user);
        parkingspot2 = new Parkingspot(tf.spot1.getSpotid() + 1, TYPE.Truck);
    }

    @Test
    void addSpotToLotPrivateMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = c.getDeclaredMethod("addSpotToLot", int.class, TYPE.class);
        m.setAccessible(true);
        Parkingspot ps = (Parkingspot) m.invoke(tf.iParkingspotService, parkinglot2.getId(), TYPE.Handicap);
        assertEquals(1, ps.getSpotid());
        Parkingspot ps2 = (Parkingspot) m.invoke(tf.iParkingspotService, parkinglot2.getId(), TYPE.Truck);
        assertEquals(2, ps2.getSpotid());
    }

    @Test
    void addSpotToBookingSchedulePrivateMethod() throws Exception {
        tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getParkinglot().getSpots().add(parkingspot2);

        Method m = c.getDeclaredMethod("addSpotToBookingSchedule", int.class, Parkingspot.class);
        m.setAccessible(true);

        int amountOfSpotsInLot = tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().size();
        m.invoke(tf.iParkingspotService, tf.parkinglot.getId(), parkingspot2);

        assertEquals(amountOfSpotsInLot + 1, tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().size());
        assertEquals(parkingspot2, tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().lastKey());

        tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getParkinglot().getSpots().remove(parkingspot2);
    }

    @Test
    void addAllTheDatesToScheduleOfSpotPrivateMethod() throws Exception {
        tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getParkinglot().getSpots().add(parkingspot2);

        Method m = c.getDeclaredMethod("addAllTheDatesToScheduleOfSpot", int.class, ConcurrentSkipListMap.class);
        m.setAccessible(true);

        assertNotEquals(tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().get(tf.spot1), tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().get(parkingspot2));

        m.invoke(tf.iParkingspotService, tf.parkinglot.getId(), tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().get(tf.spot1));

        assertEquals(tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().get(tf.spot1), tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().get(parkingspot2));

        tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getParkinglot().getSpots().remove(parkingspot2);
    }

    @Test
    void getById() throws Exception {
        assertEquals(tf.spot1, tf.iParkingspotService.getParkingspotFromParkingLot(tf.parkinglot.getId(), tf.spot1.getSpotid()));

        Assertions.assertThrows(Exception.class, () -> {
            tf.iParkingspotService.getParkingspotFromParkingLot(tf.parkinglot.getId(), tf.spot1.getSpotid() + 500);
        });
    }

    @Test
    void update() throws Exception {
        assertNotEquals(TYPE.Handicap, tf.iParkingspotService.getParkingspotFromParkingLot(tf.parkinglot.getId(), tf.spot1.getSpotid()).getType());
        tf.iParkingspotService.updateParkingspot(tf.parkinglot.getId(), tf.spot1.getSpotid(), TYPE.Handicap);
        assertEquals(TYPE.Handicap, tf.iParkingspotService.getParkingspotFromParkingLot(tf.parkinglot.getId(), tf.spot1.getSpotid()).getType());
    }

    @AfterAll
    void firstSpotofLotPrivateMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = c.getDeclaredMethod("firstSpotofLot", int.class, TYPE.class);
        m.setAccessible(true);
        parkinglot2.setSpots(null);
        Parkingspot ps = (Parkingspot) m.invoke(tf.iParkingspotService, parkinglot2.getId(), TYPE.Handicap);
        assertEquals(1, ps.getSpotid());
    }

    @AfterAll
    void addAnotherSpotToLotPrivateMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = c.getDeclaredMethod("addAnotherSpotToLot", int.class, TYPE.class);
        m.setAccessible(true);
        int amountOfSpotsInLot = parkinglot2.getSpots().size();
        Parkingspot ps = (Parkingspot) m.invoke(tf.iParkingspotService, parkinglot2.getId(), TYPE.Handicap);
        assertEquals(amountOfSpotsInLot+1, ps.getSpotid());
    }

    @AfterAll
    void delete() throws Exception {
        assertEquals(tf.spot1.getSpotid(), tf.iBookingService.getBookingByIdService(tf.booking.getId()).getSpot().getSpotid());
        assertEquals(tf.spot1.getSpotid(), tf.iParkinglotService.getParkinglotByIdService(tf.parkinglot.getId()).getSpots().get(0).getSpotid());

        assertTrue(tf.iParkinglotService.getParkinglotByIdService(tf.parkinglot.getId()).getSpots().size() > 0);

        tf.iDeletionService.deleteParkingspot(tf.parkinglot.getId(), tf.spot1.getSpotid());

        assertFalse(tf.iParkinglotService.getParkinglotByIdService(tf.parkinglot.getId()).getSpots().size() > 0);

        Assertions.assertThrows(Exception.class, () -> {
            tf.iBookingScheduleService.getAllBookingSchedules();
        });

        Assertions.assertThrows(Exception.class, () -> {
            tf.iBookingService.getAllBookings();
        });
    }
}
