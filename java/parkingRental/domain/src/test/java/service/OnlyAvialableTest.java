package service;

import org.junit.jupiter.api.*;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;
import model.*;
import service.interfaces.IBookingScheduleService;

import static model.BookingSchedule.dateFormat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OnlyAvialableTest {
    TestFactoryService tf;
    Parkingspot spot2;
    TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> onlyAvailable;
    IBookingScheduleService iBookingScheduleServiceAllTypes;

    @BeforeAll
    void before() throws Exception {
        tf = new TestFactoryService();
        spot2 = tf.iParkingspotService.createParkingspot(tf.parkinglot.getId(), TYPE.Handicap);
        iBookingScheduleServiceAllTypes = new BookingScheduleServiceAllTypes(tf.interfaceCrudSchedule);
    }


    @Test
    public void beforeBookingAllTypes() throws Exception {
        onlyAvailable = iBookingScheduleServiceAllTypes.getOnlyAvailableMap(tf.parkinglot.getId(), dateFormat.format(tf.bkdate), 3, null).getSchedule();
        assertTrue(onlyAvailable.get(tf.spot1).containsKey(dateFormat.format(tf.bkdate)));
    }

    @Test
    public void beforeBookingOneType() throws Exception {
        onlyAvailable = tf.iBookingScheduleService.getOnlyAvailableMap(tf.parkinglot.getId(), dateFormat.format(tf.bkdate), 3, TYPE.Handicap).getSchedule();
        assertTrue(onlyAvailable.get(spot2).containsKey(dateFormat.format(tf.bkdate)));
    }


    @AfterAll
    public void afterBookingOneType() throws Exception {
        Booking booking2 = tf.iBookingService.Book(spot2.getSpotid(), dateFormat.format(tf.bkdate), 2, tf.user.getId(), tf.parkinglot.getId());

        onlyAvailable = tf.iBookingScheduleService.getOnlyAvailableMap(tf.parkinglot.getId(), dateFormat.format(tf.bkdate), 3, TYPE.Handicap).getSchedule();

        assertFalse(onlyAvailable.get(spot2).containsKey(dateFormat.format(tf.bkdate)));
    }


    @AfterAll
    public void afterBookingAllTypes() throws Exception {
        tf.book();

        onlyAvailable = iBookingScheduleServiceAllTypes.getOnlyAvailableMap(tf.parkinglot.getId(), dateFormat.format(tf.bkdate), 3, null).getSchedule();

        assertFalse(onlyAvailable.get(tf.spot1).containsKey(dateFormat.format(tf.bkdate)));
    }
}
