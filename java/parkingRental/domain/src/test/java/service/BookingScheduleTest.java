package service;

import model.Location;
import model.Parkinglot;
import model.Parkingspot;
import model.TYPE;
import org.junit.jupiter.api.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static model.BookingSchedule.dateFormat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingScheduleTest {
    TestFactoryService tf;
    Class c;
    Parkinglot parkinglot2;
    ArrayList<String> listOfDates = new ArrayList<>();
    TreeMap<Parkingspot, ConcurrentSkipListMap<String, Boolean>> schedule = new TreeMap<>();
    ConcurrentSkipListMap<String, Boolean> availableDateAndTime = new ConcurrentSkipListMap<>();
    String searchDate;
    int hoursToSearchFor = 3;
    BookingScheduleServiceAllTypes bookingScheduleServiceAllTypes;

    @BeforeAll
    void before() throws Exception {
        tf = new TestFactoryService();
        c = tf.iBookingScheduleService.getClass();
        parkinglot2 = tf.iParkinglotService.createParkingLotService(new Location("s", "s", 2, 2, "f"), tf.user);
        searchDate = dateFormat.format(DateCheckerService.addHoursToAnyTime(new Date(), 1));
        bookingScheduleServiceAllTypes = new BookingScheduleServiceAllTypes(tf.iBookingScheduleService.getInterfaceCrudSchedule());
    }

    @BeforeEach
    void set() {
        listOfDates.add(searchDate);
    }

    @AfterEach
    void reset() {
        schedule = new TreeMap<>();
        availableDateAndTime = new ConcurrentSkipListMap<>();
        listOfDates = new ArrayList<>();
    }

    @Test
    void getById() throws Exception {
        assertEquals(tf.bookingSchedule, tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()));
    }

    @Test
    void getAll() throws Exception {
        assertEquals(tf.interfaceCrudSchedule.getAll(), tf.iBookingScheduleService.getAllBookingSchedules());
    }

    @Test
    void checkIfScheduleIsCorrectSize() throws Exception {
        assertEquals(tf.days*24, tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().firstEntry().getValue().size());
    }

    @Test
    void ifValidPrivateMethod() throws Exception {
        Method m = c.getDeclaredMethod("ifValid", Parkinglot.class);

        m.setAccessible(true);

        Assertions.assertThrows(Exception.class, () -> {
            m.invoke(tf.iBookingScheduleService, parkinglot2);
        });

        parkinglot2.setSpots(tf.parkinglot.getSpots());
    }

    @Test
    void getOnlyAvailBasedOnTypeTest() throws Exception {
        Method m = c.getMethod("getOnlyAvailBasedOnType", TYPE.class, int.class, TreeMap.class, ArrayList.class, ConcurrentSkipListMap.class);

        assertEquals(0, availableDateAndTime.size());
        assertEquals(0, schedule.size());
        m.invoke(tf.iBookingScheduleService, TYPE.Regular, tf.parkinglot.getId(), schedule, listOfDates, availableDateAndTime);
        assertNotEquals(0, availableDateAndTime.size());
        assertNotEquals(0, schedule.size());
    }

    @Test
    void getOnlyAvailableMapReturnsDifferentWhenDifferentInstanceUsed() throws Exception {
        tf.iParkingspotService.createParkingspot(tf.parkinglot.getId(), TYPE.Truck);

        assertEquals(
            tf.iBookingScheduleService.getOnlyAvailableMap(tf.parkinglot.getId(), dateFormat.format(tf.bkdate), 3, TYPE.Regular).getSchedule().size()  * 2,
            bookingScheduleServiceAllTypes.getOnlyAvailableMap(tf.parkinglot.getId(), dateFormat.format(tf.bkdate), 3, TYPE.Regular).getSchedule().size()
        );
    }

    @Test
    void getListOfDatesPrivateMethod() throws Exception {
        listOfDates = new ArrayList<>();

        Method m = c.getDeclaredMethod("getListOfDates", int.class, String.class, int.class, ArrayList.class);

        m.setAccessible(true);
        assertNotEquals(hoursToSearchFor, listOfDates.size());
        m.invoke(tf.iBookingScheduleService, tf.parkinglot.getId(), searchDate, hoursToSearchFor, listOfDates);
        assertEquals(hoursToSearchFor, listOfDates.size());
    }

    @Test
    void findDateInTheSchedulePrivateMethod() throws Exception {
        Method m = c.getDeclaredMethod("findDateInTheSchedule", int.class, TreeMap.class, ArrayList.class, ConcurrentSkipListMap.class, Parkingspot.class, int.class);

        m.setAccessible(true);

        assertEquals(0, availableDateAndTime.size());
        assertEquals(0, schedule.size());
        m.invoke(tf.iBookingScheduleService, tf.parkinglot.getId(), schedule, listOfDates, availableDateAndTime, tf.spot1, 0);
        assertNotEquals(0, availableDateAndTime.size());
        assertNotEquals(0, schedule.size());
    }

    @Test
    void addSpotToMapIfAvailablePrivateMethod() throws Exception {
        Method m = c.getDeclaredMethod("addSpotToMapIfAvailable", int.class, TreeMap.class, ArrayList.class, ConcurrentSkipListMap.class, Parkingspot.class, int.class);

        m.setAccessible(true);

        assertEquals(0, availableDateAndTime.size());
        assertEquals(0, schedule.size());
        m.invoke(tf.iBookingScheduleService, tf.parkinglot.getId(), schedule, listOfDates, availableDateAndTime, tf.spot1, 0);
        assertNotEquals(0, availableDateAndTime.size());
        assertNotEquals(0, schedule.size());
        assertEquals(tf.spot1, schedule.firstKey());
        assertEquals(listOfDates.get(0), schedule.firstEntry().getValue().firstKey());
    }

    @Test
    void expandExisitngScheduleByOneDay() throws Exception {
        int twiceAsBigSchedule = tf.days * 24 * 2;

        assertNotEquals(twiceAsBigSchedule, tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().firstEntry().getValue().size());

        tf.iBookingScheduleService.createForXDays(tf.parkinglot, 1);

        assertEquals(twiceAsBigSchedule, tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().firstEntry().getValue().size());
    }

    @AfterAll
    void nothingAddedToMapIfSpotNotAvailable() throws Exception {
        set();

        tf.iBookingService.Book(tf.spot1.getSpotid(), searchDate, 2, tf.user.getId(), tf.parkinglot.getId());

        Method m = c.getDeclaredMethod("addSpotToMapIfAvailable", int.class, TreeMap.class, ArrayList.class, ConcurrentSkipListMap.class, Parkingspot.class, int.class);

        m.setAccessible(true);

        assertEquals(0, availableDateAndTime.size());
        assertEquals(0, schedule.size());
        m.invoke(tf.iBookingScheduleService, tf.parkinglot.getId(), schedule, listOfDates, availableDateAndTime, tf.spot1, 0);
        assertEquals(0, availableDateAndTime.size());
        assertEquals(0, schedule.size());
    }

    @AfterAll
    void newSchedulePrivateMethod() throws Exception {
        Method m = c.getDeclaredMethod("newSchedule", Parkinglot.class, int.class);

        m.setAccessible(true);

        Assertions.assertThrows(Exception.class, () -> {
            tf.iBookingScheduleService.getBookingScheduleByIdService(parkinglot2.getId()).getSchedule();
        });

        m.invoke(tf.iBookingScheduleService, parkinglot2, 1);

        assertEquals(tf.days*24, tf.iBookingScheduleService.getBookingScheduleByIdService(parkinglot2.getId()).getSchedule().firstEntry().getValue().size());
    }

    @AfterAll
    void expandExisitngSchedulePrivateMethod() throws Exception {
        int threeTimesAsBigSchedule = tf.days * 24 * 3;

        Method m = c.getDeclaredMethod("addToExistingSchedule", Parkinglot.class, int.class);

        m.setAccessible(true);

        assertNotEquals(threeTimesAsBigSchedule, tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().firstEntry().getValue().size());

        m.invoke(tf.iBookingScheduleService, tf.parkinglot, 1);

        assertEquals(threeTimesAsBigSchedule, tf.iBookingScheduleService.getBookingScheduleByIdService(tf.parkinglot.getId()).getSchedule().firstEntry().getValue().size());
    }
}
