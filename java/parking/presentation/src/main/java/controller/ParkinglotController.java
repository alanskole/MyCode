package controller;
import io.javalin.http.Context;
import service.interfaces.IDeletionServiceLot;
import service.interfaces.IParkinglotService;
import service.interfaces.IUserService;
import model.*;

public class ParkinglotController {
    private IParkinglotService parkinglotService;
    private IUserService userService;
    private IDeletionServiceLot deletionService;

    public ParkinglotController(IParkinglotService parkinglotService, IUserService userService, IDeletionServiceLot deletionService) {
        this.parkinglotService = parkinglotService;
        this.userService = userService;
        this.deletionService = deletionService;
    }

    public IDeletionServiceLot getDeletionService() {
        return deletionService;
    }

    public IParkinglotService getParkinglotService() {
        return parkinglotService;
    }

    public IUserService getUserService() {
        return userService;
    }

    public void createParkinglotCtr(Context context) {
        try {
            Location loc = new Location();
            loc.setCity(context.formParam("city"));
            loc.setAddress(context.formParam("address"));
            loc.setNumber(Integer.parseInt(context.formParam("number")));
            loc.setZipcode(Integer.parseInt(context.formParam("zipcode")));
            loc.setArea(context.formParam("area"));

            User user = userService.getUserByIdService(Integer.parseInt(context.pathParam("userid")));
            parkinglotService.createParkingLotService(loc, user);

            context.redirect("/lot/");
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void deleteParkinglotCtr(Context context) {
        try {
            deletionService.deleteParkinglotFromMap(Integer.parseInt(context.pathParam("lotid")));
            context.redirect("/lot/");
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void updateParkinglotCtr(Context context) {
        try {
            Location loc = new Location();
            loc.setCity(context.formParam("city"));
            loc.setAddress(context.formParam("address"));
            loc.setNumber(Integer.parseInt(context.formParam("number")));
            loc.setZipcode(Integer.parseInt(context.formParam("zipcode")));
            loc.setArea(context.formParam("area"));
            int lotid = Integer.parseInt(context.pathParam("lotid"));
            User user = userService.getUserByIdService(Integer.parseInt(context.pathParam("userid")));

            parkinglotService.updateParkinglotService(lotid, loc.getCity(), loc.getAddress(), loc.getNumber(), loc.getZipcode(), loc.getArea(), user);
            context.redirect("/lot/"+lotid);
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void getParkinglotCtr(Context context) {
        try {
            context.json(parkinglotService.getParkinglotByIdService(Integer.parseInt(context.pathParam("lotid"))));
            context.status(200);
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void getAllParkinglotCtr(Context context) {
        try {
            context.json(parkinglotService.getAllParkinglots());
            context.status(200);
        } catch (Exception e) {
            context.status(404);
        }
    }
}
