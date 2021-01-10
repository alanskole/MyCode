package service;

import fakerepository.FakeBookingRepo;
import fakerepository.FakeBookingScheduleRepo;
import fakerepository.FakeParkingLotRepo;
import fakerepository.FakeUserRepo;
import irepository.InterfaceCrud;
import model.*;
import service.interfaces.*;

import java.util.Date;
import static model.BookingSchedule.dateFormat;

public class TestFactoryService {
    InterfaceCrud<Booking> interfaceCrudBooking = new FakeBookingRepo();
    InterfaceCrud<User> interfaceCrudUser = new FakeUserRepo();
    InterfaceCrud<BookingSchedule> interfaceCrudSchedule = new FakeBookingScheduleRepo();
    InterfaceCrud<Parkinglot> interfaceCrudLot = new FakeParkingLotRepo();
    IBookingService iBookingService = new BookingService(interfaceCrudBooking, interfaceCrudUser, interfaceCrudSchedule);
    IBookingScheduleService iBookingScheduleService = new BookingScheduleService(interfaceCrudSchedule);
    IUserService iUserService = new UserService(interfaceCrudUser);
    IParkinglotService iParkinglotService = new ParkinglotService(interfaceCrudLot, interfaceCrudSchedule);
    IParkinspotService iParkingspotService = new ParkingspotService(interfaceCrudLot, interfaceCrudSchedule);
    IDeletionService iDeletionService = new DeletionService(interfaceCrudBooking, interfaceCrudSchedule, interfaceCrudUser, interfaceCrudLot);
    User user;
    Parkinglot parkinglot;
    Parkingspot spot1;
    BookingSchedule bookingSchedule;
    Booking booking;
    Date now;
    Date bkdate;
    int days = 1;

    public TestFactoryService() throws Exception {
        user = iUserService.createUserService("First", "Usah", "Home", "ff", 2, 1001, "dd");
        CurrentUser.loggedInAs = user;

        parkinglot = iParkinglotService.createParkingLotService(new Location("Testistad", "Test road", 23, 1321, "Fake"), user);

        spot1 = this.iParkingspotService.createParkingspot(parkinglot.getId(), TYPE.Regular);

        now = new Date();
        bkdate = DateCheckerService.addHoursToAnyTime(now, 3);

        bookingSchedule = iBookingScheduleService.createForXDays(parkinglot, days);
    }

    public void book() throws Exception {
        booking = iBookingService.Book(spot1.getSpotid(), dateFormat.format(bkdate), 2, user.getId(), parkinglot.getId());
    }
}
