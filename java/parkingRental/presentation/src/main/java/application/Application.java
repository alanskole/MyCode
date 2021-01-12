package application;

import controller.*;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.vue.JavalinVue;
import io.javalin.plugin.rendering.vue.VueComponent;

public class Application {
    public Application(Javalin app, BookingScheduleController bookingScheduleController, BookingController bookingController, ParkinglotController parkinglotController, ParkingspotController parkingspotController, UserController userController, LoginController loginController) {
        app = Javalin.create().start();
        app.config.enableWebjars();
        JavalinVue.rootDirectory("/vue", Location.CLASSPATH);

        app.get("/", new VueComponent("login"));

        app.get("/users/", new VueComponent("all-users"));
        app.get("/users/:userid", new VueComponent("user"));
        app.get("/users/create/new/user", new VueComponent("create-user"));
        app.get("/users/:userid/update", new VueComponent("update-user"));

        app.get("/lot/", new VueComponent("all-lots"));
        app.get("/lot/:lotid", new VueComponent("lot"));
        app.get("/lot/create/new/lot/:userid", new VueComponent("create-lot"));
        app.get("/lot/:lotid/update/:userid", new VueComponent("update-lot"));

        app.get("/lot/:lotid/update/create/spot/", new VueComponent("create-spot"));
        app.get("/lot/:lotid/update/spot/:spotid", new VueComponent("update-spot"));

        app.get("/bsch/:lotid", new VueComponent("search"));
        app.get("/bsch/search/result", new VueComponent("result"));
        app.get("/bsch/search/result/alltypes", new VueComponent("result-alltypes"));

        app.get("/bookings", new VueComponent("all-bookings"));
        app.get("/bookings/ownedspots", new VueComponent("ownedspots-bookings"));
        app.get("/bookings/:bid", new VueComponent("booking"));

        app.get("api/currentuser/:userid", context -> loginController.setCurrentUser(context));
        app.get("api/currentuser/", context -> loginController.getCurrentUser(context));
        app.get("api/access/", context -> loginController.canAccess(context));
        app.get("api/isadmin/", context -> loginController.isAdminCtr(context));
        app.get("api/logout/", context -> loginController.logOut(context));

        app.get("api/users/", context -> userController.getAllUserCtr(context));
        app.get("api/users/:userid/", context -> userController.getUserCtr(context));
        app.post("users/create/new/user/api/create", context -> userController.createUserCtr(context));
        app.post("/users/:userid/api/update/", context -> userController.updateUserCtr(context));
        app.get("api/users/delete/:userid", context -> userController.deleteUserCtr(context));

        app.get("api/lot/", context -> parkinglotController.getAllParkinglotCtr(context));
        app.get("api/lot/:lotid", context -> parkinglotController.getParkinglotCtr(context));
        app.post("/lot/create/new/lot/api/create/:userid", context -> parkinglotController.createParkinglotCtr(context));
        app.post("/lot/:lotid/update/api/update/:userid", context -> parkinglotController.updateParkinglotCtr(context));
        app.get("api/lot/:lotid/delete/", context -> parkinglotController.deleteParkinglotCtr(context));

        app.get("api/parkingspot/:lotid/:spotid", context -> parkingspotController.getParkingspotCtr(context));
        app.post("lot/:lotid/update/create/:lotid/", context -> parkingspotController.createParkingspotCrt(context));
        app.post("lot/:lotid/update/spot/:lotid/:spotid/", context -> parkingspotController.updateParkingspotCtr(context));
        app.get("api/parkingspot/:lotid/delete/:spotid", context -> parkingspotController.deleteParkingspotCtr(context));

        app.get("lot/api/bsch/:lotid/create/:days", context -> bookingScheduleController.createForXDaysCtr(context));
        app.get("api/bsch/:lotid", context -> bookingScheduleController.getBookingScheduleCtr(context));
        app.post("/bsch/api/:lotid", context -> bookingScheduleController.getOnlyAvailableCtrlPost(context));
        app.get("/bsch/api/result", context -> bookingScheduleController.getOnlyAvailableCtrlGet(context));
        app.get("/bsch/api/result/alltypes", context -> bookingScheduleController.getOnlyAvailableAllTypesCtrlGet(context));
        app.get("api/bsch/:lotid/delete", context -> bookingScheduleController.deleteBookingScheduleCtr(context));

        app.post("/bsch/search/booking/book/:lotid/:userid", context -> bookingController.bookCtr(context));
        app.get("api/bookings/", context -> bookingController.getAllBookingFromMapCtr(context));
        app.get("api/bookings/ownedspots", context -> bookingController.getAllBookingsOfOwnedSpotsFromMapCtr(context));
        app.get("api/bookings/:bid", context -> bookingController.getBookingFromMapCtr(context));
        app.get("api/bookings/delete/:bid", context -> bookingController.deleteBookingFromMapCtr(context));

        app.get("bookings/api/bookings/csv", context -> bookingController.writeBookingsToCsvCtr(context));
    }

    public Application(int port, Javalin app, BookingScheduleController bookingScheduleController, BookingController bookingController, ParkinglotController parkinglotController, ParkingspotController parkingspotController, UserController userController, LoginController loginController) {
        app = Javalin.create().start(port);
        app.config.enableWebjars();

        app.get("/", new VueComponent("login"));

        app.get("/users/", new VueComponent("all-users"));
        app.get("/users/:userid", new VueComponent("user"));
        app.get("/users/create/new/user", new VueComponent("create-user"));
        app.get("/users/:userid/update", new VueComponent("update-user"));

        app.get("/lot/", new VueComponent("all-lots"));
        app.get("/lot/:lotid", new VueComponent("lot"));
        app.get("/lot/create/new/lot/:userid", new VueComponent("create-lot"));
        app.get("/lot/:lotid/update/:userid", new VueComponent("update-lot"));

        app.get("/lot/:lotid/update/create/spot/", new VueComponent("create-spot"));
        app.get("/lot/:lotid/update/spot/:spotid", new VueComponent("update-spot"));

        app.get("/bsch/:lotid", new VueComponent("search"));
        app.get("/bsch/search/result", new VueComponent("result"));

        app.get("/bookings", new VueComponent("all-bookings"));
        app.get("/bookings/ownedspots", new VueComponent("ownedspots-bookings"));
        app.get("/bookings/:bid", new VueComponent("booking"));

        app.get("api/currentuser/:userid", context -> loginController.setCurrentUser(context));
        app.get("api/currentuser/", context -> loginController.getCurrentUser(context));
        app.get("api/access/", context -> loginController.canAccess(context));
        app.get("api/isadmin/", context -> loginController.isAdminCtr(context));
        app.get("api/logout/", context -> loginController.logOut(context));

        app.get("api/users/", context -> userController.getAllUserCtr(context));
        app.get("api/users/:userid/", context -> userController.getUserCtr(context));
        app.post("users/create/new/user/api/create", context -> userController.createUserCtr(context));
        app.post("/users/:userid/api/update/", context -> userController.updateUserCtr(context));
        app.get("api/users/delete/:userid", context -> userController.deleteUserCtr(context));

        app.get("api/lot/", context -> parkinglotController.getAllParkinglotCtr(context));
        app.get("api/lot/:lotid", context -> parkinglotController.getParkinglotCtr(context));
        app.post("/lot/create/new/lot/api/create/:userid", context -> parkinglotController.createParkinglotCtr(context));
        app.post("/lot/:lotid/update/api/update/:userid", context -> parkinglotController.updateParkinglotCtr(context));
        app.get("api/lot/:lotid/delete/", context -> parkinglotController.deleteParkinglotCtr(context));

        app.get("api/parkingspot/:lotid/:spotid", context -> parkingspotController.getParkingspotCtr(context));
        app.post("lot/:lotid/update/create/:lotid/", context -> parkingspotController.createParkingspotCrt(context));
        app.post("lot/:lotid/update/spot/:lotid/:spotid/", context -> parkingspotController.updateParkingspotCtr(context));
        app.get("api/parkingspot/:lotid/delete/:spotid", context -> parkingspotController.deleteParkingspotCtr(context));

        app.get("lot/api/bsch/:lotid/create/:days", context -> bookingScheduleController.createForXDaysCtr(context));
        app.get("api/bsch/:lotid", context -> bookingScheduleController.getBookingScheduleCtr(context));
        app.post("/bsch/api/:lotid", context -> bookingScheduleController.getOnlyAvailableCtrlPost(context));
        app.get("/bsch/api/result", context -> bookingScheduleController.getOnlyAvailableCtrlGet(context));
        app.get("api/bsch/:lotid/delete", context -> bookingScheduleController.deleteBookingScheduleCtr(context));

        app.post("/bsch/search/booking/book/:lotid/:userid", context -> bookingController.bookCtr(context));
        app.get("api/bookings/", context -> bookingController.getAllBookingFromMapCtr(context));
        app.get("api/bookings/ownedspots", context -> bookingController.getAllBookingsOfOwnedSpotsFromMapCtr(context));
        app.get("api/bookings/:bid", context -> bookingController.getBookingFromMapCtr(context));
        app.get("api/bookings/delete/:bid", context -> bookingController.deleteBookingFromMapCtr(context));

        app.get("bookings/api/bookings/csv", context -> bookingController.writeBookingsToCsvCtr(context));
    }
}
