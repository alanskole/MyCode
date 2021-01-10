import application.Application;
import controller.*;
import irepository.*;
import repository.*;
import service.*;
import model.*;
import java.util.*;
import io.javalin.Javalin;
import service.interfaces.*;

import static model.BookingSchedule.dateFormat;

public class Main {
    public static void main(String[] args) throws Exception {
        InterfaceCrud<Booking> bookingInterface = new BookingRepo();
        InterfaceCrud<User> userInterface = new UserRepo();
        InterfaceCrud<BookingSchedule> bookingScheduleInterface = new BookingscheduleRepo();
        InterfaceCrud<Parkinglot> parkinglotInterface = new ParkinglotRepo();
        IBookingService iBookingService = new BookingService(bookingInterface, userInterface, bookingScheduleInterface);
        IUserService iUserService = new UserService(userInterface);
        IParkinglotService iParkinglotService = new ParkinglotService(parkinglotInterface, bookingScheduleInterface);
        IParkinspotService iParkingspotService = new ParkingspotService(parkinglotInterface, bookingScheduleInterface);
        IBookingScheduleService iBookingScheduleService = new BookingScheduleService(bookingScheduleInterface);
        ILoginService iLoginService = iUserService;
        DeletionService deletionService = new DeletionService(bookingInterface, bookingScheduleInterface, userInterface, parkinglotInterface);
        iUserService.createUserService("Ola", "Normann", "test", "add", 2, 6781, "Flee");
        iUserService.createUserService("kari", "Normann", "test", "adr", 43, 6781, "Bag");
        iUserService.createUserService("Mats", "Larsen", "testveien", "adrs", 36, 4431,"Random" );
        iUserService.createUserService("Petter", "Nman", "blabla", "Strt" ,5, 4325, "Sum");
        iUserService.createUserService("Lisa", "Blasen", "veien", "Drum",4, 3223, "Last");

        iUserService.getUserByIdService(1).setRole(ROLE.Administrator);
        iUserService.getUserByIdService(5).setRole(ROLE.Manager);

        CurrentUser.loggedInAs = iUserService.getUserByIdService(1);

        iParkinglotService.createParkingLotService(new Location("Sarpsborg", "Sarpevegen", 22, 1718, "Borgenhaugen"), iUserService.getUserByIdService(2));
        ArrayList<Parkingspot> arspots = new ArrayList<>();
        arspots.add(iParkingspotService.createParkingspot(1, TYPE.Regular));
        arspots.add(iParkingspotService.createParkingspot(1, TYPE.Handicap));
        arspots.add(iParkingspotService.createParkingspot(1, TYPE.Truck));


        iParkinglotService.createParkingLotService(new Location("Fredrikstad", "Fredrikveien", 11, 1534, "Glemmen"), iUserService.getUserByIdService(3));
        ArrayList<Parkingspot> arspots2 = new ArrayList<>();
        arspots2.add(iParkingspotService.createParkingspot(2, TYPE.Regular));
        arspots2.add(iParkingspotService.createParkingspot(2, TYPE.Handicap));
        arspots2.add(iParkingspotService.createParkingspot(2, TYPE.Truck));

        //create a bookingschedule
        iBookingScheduleService.createForXDays(iParkinglotService.getParkinglotByIdService(1), 1);
        iBookingScheduleService.createForXDays(iParkinglotService.getParkinglotByIdService(2), 1);

        Date now = new Date();
        //getting dates to book with
        Date date1 = DateCheckerService.addHoursToAnyTime(now,2);
        Date date2 = DateCheckerService.addHoursToAnyTime(now, 4);

        Booking emptybooking = new Booking();
        Booking b1 = (iBookingService.Book(2, dateFormat.format(date1), 2, 4, 1));
        Booking b2 = (iBookingService.Book(3, dateFormat.format(date1), 3, 3, 1));
        Booking b3 = (iBookingService.Book(2, dateFormat.format(date2), 4, 2, 2));

        BookingScheduleController bookingScheduleController = new BookingScheduleController(iBookingScheduleService, iParkinglotService);
        BookingController bookingController = new BookingController(iBookingService, deletionService);
        ParkinglotController parkinglotController = new ParkinglotController(iParkinglotService, iUserService, deletionService);
        ParkingspotController parkingspotController = new ParkingspotController(iParkingspotService, deletionService);
        UserController userController = new UserController(iUserService, deletionService);
        LoginController loginController = new LoginController(iLoginService);

        CurrentUser.loggedInAs = null;

        Javalin app = null;

        Application application = new Application(app, bookingScheduleController, bookingController, parkinglotController, parkingspotController, userController, loginController);
    }
}