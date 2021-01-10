package controller;

import io.javalin.http.Context;

import service.interfaces.IBookingService;
import service.interfaces.IDeletionServiceBooking;

import java.io.File;

public class BookingController {
    private IBookingService IBookingService;
    private IDeletionServiceBooking deletionService;

    public BookingController(IBookingService IBookingService, IDeletionServiceBooking deletionService) {
        this.IBookingService = IBookingService;
        this.deletionService = deletionService;
    }

    public service.interfaces.IBookingService getIBookingService() {
        return IBookingService;
    }

    public IDeletionServiceBooking getDeletionService() {
        return deletionService;
    }

    public void bookCtr(Context context) {
        try {
            int userid = Integer.parseInt(context.pathParam("userid").replaceAll("\\s+", ""));;
            String date = context.formParam("date").trim();
            int hours = Integer.parseInt(context.formParam("hours").replaceAll("\\s+", ""));
            int parkingsspotid = Integer.parseInt(context.formParam("spotid").replaceAll("\\s+", ""));
            context.redirect("/bookings/"+ IBookingService.Book(parkingsspotid, date, hours, userid, Integer.parseInt(context.pathParam("lotid"))).getId());
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void getBookingFromMapCtr(Context context) {
        try {
            int id = Integer.parseInt(context.pathParam("bid"));
            context.json(IBookingService.getBookingByIdService(id));
            context.status(200);
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void deleteBookingFromMapCtr(Context context) {
        try {
            int id = Integer.parseInt(context.pathParam("bid"));
            deletionService.deleteBookingFromMap(id);
            context.redirect("/bookings/");
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void getAllBookingFromMapCtr(Context context) {
        try {
            context.json(IBookingService.getAllBookings().values());

            context.status(200);
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void getAllBookingsOfOwnedSpotsFromMapCtr(Context context) {
        try {
            context.json(IBookingService.getAllBookingsOfOwnedSpots().values());

            context.status(200);
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void writeBookingsToCsvCtr(Context context) {
        try {
            IBookingService.writeBookingsToCsv(new File("bookings.csv"));
            context.redirect("/bookings/");
        } catch (Exception e) {
            context.status(404);
        }
    }
}
