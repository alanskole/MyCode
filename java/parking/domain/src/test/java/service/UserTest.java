package service;

import org.junit.jupiter.api.*;
import model.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest {
    TestFactoryService tf;

    @BeforeAll
    void create() throws Exception {
        tf = new TestFactoryService();
        tf.book();
    }

    @Test
    void getById() throws Exception {
        assertEquals(tf.user, tf.iUserService.getUserByIdService(tf.user.getId()));

        Assertions.assertThrows(Exception.class, () -> {
            tf.iUserService.getUserByIdService(tf.user.getId() + 500);
        });
    }

    @Test
    void getAll() throws Exception {
        assertEquals(tf.interfaceCrudUser.getAll(), tf.iUserService.getAllUsers());
    }

    @Test
    void update() throws Exception {
        int newZip = 0000;

        assertNotEquals(newZip, tf.iUserService.getUserByIdService(tf.user.getId()).getLocation().getZipcode());

        tf.iUserService.updateUserService(tf.user.getId(), tf.user.getFirstname(), tf.user.getSurname(), tf.user.getLocation().getCity(), tf.user.getLocation().getAddress(), tf.user.getLocation().getNumber(), newZip, tf.user.getLocation().getArea());

        assertEquals(newZip, tf.iUserService.getUserByIdService(tf.user.getId()).getLocation().getZipcode());
    }

    @AfterAll
    void delete() throws Exception {
        assertEquals(tf.user, tf.iUserService.getUserByIdService(tf.user.getId()));
        assertEquals(tf.user, tf.iParkinglotService.getParkinglotByIdService(tf.parkinglot.getId()).getOwner());
        assertEquals(tf.user, tf.iBookingService.getBookingByIdService(tf.booking.getId()).getUser());

        tf.iDeletionService.deleteUserFromMap(tf.user.getId());

        Assertions.assertThrows(Exception.class, () -> {
            tf.iUserService.getUserByIdService(tf.user.getId());
        });

        Assertions.assertThrows(Exception.class, () -> {
            tf.iParkinglotService.getParkinglotByIdService(tf.parkinglot.getId());
        });

        Assertions.assertThrows(Exception.class, () -> {
            tf.iBookingService.getBookingByIdService(tf.booking.getId());
        });
    }
}
