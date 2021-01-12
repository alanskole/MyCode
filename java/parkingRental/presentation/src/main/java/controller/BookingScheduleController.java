package controller;

import io.javalin.http.Context;
import service.BookingScheduleService;
import service.BookingScheduleServiceAllTypes;
import service.interfaces.IBookingScheduleService;
import service.interfaces.IParkinglotService;
import model.BookingSchedule;
import model.*;

public class BookingScheduleController {
    private IBookingScheduleService iBookingScheduleService;
    private BookingScheduleServiceAllTypes bookingScheduleServiceAllTypes;
    private IParkinglotService iParkinglotService;

    public IBookingScheduleService getBookingScheduleService() {
        return this.iBookingScheduleService;
    }

    public IParkinglotService getParkinglotService() {
        return this.iParkinglotService;
    }

    public BookingScheduleController(IBookingScheduleService iBookingScheduleService, IParkinglotService iParkinglotService) {
        this.iBookingScheduleService = iBookingScheduleService;
        this.iParkinglotService = iParkinglotService;
    }

    public void createForXDaysCtr(Context context) {
        try {
            int id = Integer.parseInt(context.pathParam("lotid"));
            int days = Integer.parseInt(context.pathParam("days"));
            BookingSchedule bks = iBookingScheduleService.createForXDays(iParkinglotService.getParkinglotByIdService(id), days);

            context.redirect("/bsch/"+id);
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void getOnlyAvailableCtrlPost(Context context) {
        try {
            int id = Integer.parseInt(context.pathParam("lotid"));
            String date = context.formParam("date");
            int hours = Integer.parseInt(context.formParam("hours"));
            typeFinder(context, id, date, hours);
        } catch (Exception e) {
            context.status(404);
        }
    }

    private void typeFinder(Context context, int id, String date, int hours) throws Exception {
        if (context.formParam("type").equalsIgnoreCase("All types")) {
            bookingScheduleServiceAllTypes = new BookingScheduleServiceAllTypes(iBookingScheduleService.getInterfaceCrudSchedule());
            bookingScheduleServiceAllTypes.getOnlyAvailableMap(id, date, hours, null);
            context.redirect("/bsch/search/result/alltypes");
        }
        else {
            TYPE type = TYPE.Regular;
            if (context.formParam("type").equalsIgnoreCase("truck"))
                type = TYPE.Truck;
            else if (context.formParam("type").equalsIgnoreCase("handicap"))
                type = TYPE.Handicap;

            context.redirect("/bsch/search/result");

            iBookingScheduleService.getOnlyAvailableMap(id, date, hours, type);
        }
    }

    public void getOnlyAvailableCtrlGet(Context context) {
        try {
            context.json(iBookingScheduleService.getOnlyAvailable());
            context.status(200);
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void getOnlyAvailableAllTypesCtrlGet(Context context) {
        try {
            context.json(bookingScheduleServiceAllTypes.getOnlyAvailable());
            context.status(200);
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void deleteBookingScheduleCtr(Context context) {
        try {
            int id = Integer.parseInt(context.pathParam("lotid"));

            iBookingScheduleService.deleteBookingScheduleService(id);

            context.redirect("/api/lot/");
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void getBookingScheduleCtr(Context context) {
        try {
            int id = Integer.parseInt(context.pathParam("lotid"));

            context.json(iBookingScheduleService.getBookingScheduleByIdService(id));

            context.status(200);
        } catch (Exception e) {
            context.status(404);
        }
    }
}
