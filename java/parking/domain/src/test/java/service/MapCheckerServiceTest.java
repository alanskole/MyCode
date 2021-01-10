package service;

import fakerepository.FakeBookingScheduleRepo;
import fakerepository.FakeParkingLotRepo;
import irepository.InterfaceCrud;
import model.*;
import org.junit.jupiter.api.*;
import service.interfaces.IParkinglotService;

import java.lang.reflect.Method;
import java.util.ArrayList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MapCheckerServiceTest {
    InterfaceCrud<Parkinglot> interfaceCrudLot = new FakeParkingLotRepo();
    InterfaceCrud<BookingSchedule> interfaceCrudSched = new FakeBookingScheduleRepo();
    IParkinglotService iParkinglotService = new ParkinglotService(interfaceCrudLot, interfaceCrudSched);
    TestFactoryService tf;
    Parkinglot parkinglotNotInsideAnyMap;
    Parkingspot parkingspot = new Parkingspot(2, TYPE.Handicap);
    ArrayList<Parkingspot> spots = new ArrayList<>();

    @BeforeAll
    void before() throws Exception {
        tf = new TestFactoryService();
        parkinglotNotInsideAnyMap = new Parkinglot();
        parkinglotNotInsideAnyMap.setLocation(tf.parkinglot.getLocation());
        spots.add(parkingspot);
    }

    @Test
    void isAnythingInTheMapTest() throws Exception {
        Assertions.assertThrows(Exception.class, () -> {
            MapCheckerService.isAnythingInTheMap(interfaceCrudLot);
        });

        MapCheckerService.isAnythingInTheMap(tf.interfaceCrudLot);
    }

    @Test
    void isObjectInTheMapTest() throws Exception {
        Assertions.assertThrows(Exception.class, () -> {
            MapCheckerService.isObjectInTheMap(interfaceCrudLot, parkinglotNotInsideAnyMap.getId());
        });

        MapCheckerService.isObjectInTheMap(tf.interfaceCrudLot, tf.parkinglot.getId());
    }

    @Test
    void doesLotHaveSpotsTest() throws Exception {
        Class c = MapCheckerService.class;

        Method m = c.getDeclaredMethod("doesLotHaveSpots", Parkinglot.class);

        m.setAccessible(true);

        Assertions.assertThrows(Exception.class, () -> {
            m.invoke(c, parkinglotNotInsideAnyMap);
        });
    }

    @AfterAll
    void isSpotInLotTest() throws Exception {
        parkinglotNotInsideAnyMap.setSpots(spots);

        Assertions.assertThrows(Exception.class, () -> {
            MapCheckerService.isSpotInLot(parkinglotNotInsideAnyMap, tf.spot1.getSpotid());
        });

        MapCheckerService.isSpotInLot(parkinglotNotInsideAnyMap, parkingspot.getSpotid());
    }
}