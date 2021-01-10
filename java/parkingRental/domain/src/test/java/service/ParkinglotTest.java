package service;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParkinglotTest {
    TestFactoryService tf;

    @BeforeAll
    void create() throws Exception {
        tf = new TestFactoryService();
        tf.book();
    }


    @Test
    void getById() {
        assertEquals(tf.parkinglot, tf.interfaceCrudLot.getById(tf.parkinglot.getId()));

        Assertions.assertThrows(Exception.class, () -> {
            tf.iParkinglotService.getParkinglotByIdService(tf.parkinglot.getId() + 500);
        });
    }

    @Test
    void getAll() throws Exception {
        assertEquals(tf.interfaceCrudLot.getAll(), tf.iParkinglotService.getAllParkinglots());
    }

    @Test
    void update() throws Exception {
        String newCity = "New City";
        assertNotEquals(newCity, tf.iParkinglotService.getParkinglotByIdService(tf.parkinglot.getId()).getLocation().getCity());
        tf.iParkinglotService.updateParkinglotService(tf.parkinglot.getId(), newCity, tf.parkinglot.getLocation().getAddress(), tf.parkinglot.getLocation().getNumber(), tf.parkinglot.getLocation().getZipcode(), tf.parkinglot.getLocation().getArea(), tf.user);
        assertEquals(newCity, tf.iParkinglotService.getParkinglotByIdService(tf.parkinglot.getId()).getLocation().getCity());
    }

    @AfterAll
    void delete() throws Exception {
        assertEquals(tf.parkinglot, tf.iBookingService.getBookingByIdService(tf.booking.getId()).getParkinglot());
        assertEquals(tf.parkinglot, tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getParkinglot());
        assertTrue(tf.iBookingScheduleService.getAllBookingSchedules().size() > 0);

        tf.iDeletionService.deleteParkinglotFromMap(tf.parkinglot.getId());

        Assertions.assertThrows(Exception.class, () -> {
            tf.iBookingScheduleService.getAllBookingSchedules();
        });
        Assertions.assertThrows(Exception.class, () -> {
            tf.iParkinglotService.getAllParkinglots();
        });
        Assertions.assertThrows(Exception.class, () -> {
            tf.iBookingService.getAllBookings();
        });
    }
}