package service;

import model.*;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminManagerUserTest {
    TestFactoryService tf;
    User admin;
    User manager;
    User userToBeDeleted;

    @BeforeAll
    void before() throws Exception {
        tf = new TestFactoryService();
        tf.book();

        admin = tf.iUserService.createUserService("Second", "User", "Fakestad", "Mn kl", 29, 8796, "Somewhere");
        manager = tf.iUserService.createUserService("Third", "Usr", "Realstad", "Lk oi", 82, 4356, "Here");;
        userToBeDeleted = tf.iUserService.createUserService("Mnmn", "Jhuu", "Nggy", "ff", 6, 7766, "There");
        admin.setRole(ROLE.Administrator);
        manager.setRole(ROLE.Manager);
    }

    @Test
    void userCantUpdateOtherUsers() {
        Assertions.assertThrows(Exception.class, () -> {
            tf.iUserService.updateUserService(manager.getId(), "ss", "ss", "ss", "dd", 19, 1231, "Where");
        });
    }

    @Test
    void managerCanUpdateOtherUser() throws Exception {
        CurrentUser.loggedInAs = manager;
        String newData = "xsxs";

        assertNotEquals(newData, tf.iUserService.getUserByIdService(tf.user.getId()).getFirstname());

        tf.iUserService.updateUserService(tf.user.getId(), newData, "ss", "ss", "dd", 19, 1231, "Where");

        assertEquals(newData, tf.iUserService.getUserByIdService(tf.user.getId()).getFirstname());
    }

    @Test
    void managerCanUpdateParkinglots() throws Exception {
        CurrentUser.loggedInAs = manager;
        String newData = "ss";

        assertNotEquals(newData, tf.iParkinglotService.getParkinglotByIdService(tf.parkinglot.getId()).getLocation().getCity());

        tf.iParkinglotService.updateParkinglotService(tf.parkinglot.getId(), newData, newData, 2, 2, newData, tf.user);

        assertEquals(newData, tf.iParkinglotService.getParkinglotByIdService(tf.parkinglot.getId()).getLocation().getCity());

    }

    @Test
    void managerCanCreateParkinglots() throws Exception {
        CurrentUser.loggedInAs = manager;

        assertFalse(tf.iParkinglotService.getAllParkinglots().size() > 1);

        tf.iParkinglotService.createParkingLotService(tf.parkinglot.getLocation(), tf.user);

        assertTrue(tf.iParkinglotService.getAllParkinglots().size() > 1);
    }

    @Test
    void managerCanCreateBookingschedule() throws Exception {
        CurrentUser.loggedInAs = manager;

        assertNotEquals(tf.days*24*2, tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().firstEntry().getValue().size());

        tf.iBookingScheduleService.createForXDays(tf.parkinglot, tf.days);

        assertEquals(tf.days*24*2, tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().firstEntry().getValue().size());
    }

    @Test
    void managerCantDeleteOtherUsers() {
        CurrentUser.loggedInAs = manager;

        Assertions.assertThrows(Exception.class, () -> {
            tf.iDeletionService.deleteUserFromMap(userToBeDeleted.getId());
        });
    }

    @Test
    void testWriteBookingsToCsv() throws Exception {
        CurrentUser.loggedInAs = admin;
        File test = new File("test.csv");
        tf.iBookingService.writeBookingsToCsv(test);

        try (BufferedReader br = new BufferedReader(new FileReader(test))) {
            String line;
            List<String[]> list = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                list.add(line.split(","));
            }

            assertEquals(list.get(0)[0], String.valueOf(tf.booking.getId()));
            assertEquals(list.get(0)[1], String.valueOf(tf.booking.getUser().getId()));
            assertEquals(list.get(0)[4], String.valueOf(tf.booking.getSpot().getSpotid()));
            assertEquals(list.get(0)[5], tf.booking.getDateAndTime().get(0));
            assertEquals(list.get(0)[list.get(0).length -1], String.valueOf(tf.booking.getPrice()));
        }

        test.delete();
    }

    @AfterEach
    void resetCurrentUser() {
        CurrentUser.loggedInAs = tf.user;
    }

    @AfterAll
    void managerCanDeleteBookings() throws Exception {
        CurrentUser.loggedInAs = manager;
        assertTrue(tf.iBookingService.getAllBookings().containsKey(tf.booking.getId()));

        tf.iDeletionService.deleteBookingFromMap(tf.booking.getId());

        Assertions.assertThrows(Exception.class, () -> {
            tf.iBookingService.getAllBookings();
        });
    }

    @AfterAll
    void managerCanDeleteParkinglots() throws Exception {
        CurrentUser.loggedInAs = manager;

        assertTrue(tf.iParkinglotService.getAllParkinglots().containsKey(tf.parkinglot.getId()));

        tf.iDeletionService.deleteParkinglotFromMap(tf.parkinglot.getId());

        Assertions.assertThrows(Exception.class, () -> {
            tf.iParkinglotService.getParkinglotByIdService((tf.parkinglot.getId()));
        });
    }

    @AfterAll
    void adminCanDeleteOtherUsers() throws Exception {
        CurrentUser.loggedInAs = admin;

        tf.iDeletionService.deleteUserFromMap(userToBeDeleted.getId());

        assertFalse(tf.iUserService.getAllUsers().containsKey(userToBeDeleted.getId()));
    }
}