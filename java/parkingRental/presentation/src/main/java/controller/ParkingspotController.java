package controller;

import io.javalin.http.Context;
import service.interfaces.IDeletionServiceSpot;
import service.interfaces.IParkinspotService;
import model.*;


public class ParkingspotController {
    private IParkinspotService parkinglotService;
    private IDeletionServiceSpot deletionService;


    public ParkingspotController(IParkinspotService parkinglotService, IDeletionServiceSpot deletionService) {
        this.parkinglotService = parkinglotService;
        this.deletionService = deletionService;
    }

    public IParkinspotService getParkinglotService() {
        return parkinglotService;
    }

    public IDeletionServiceSpot getDeletionService() {
        return deletionService;
    }

    public void createParkingspotCrt(Context context) throws Exception {
        try {
            int lotid = Integer.parseInt(context.pathParam("lotid"));

            TYPE type = TYPE.Regular;
            type = getType(context, type);

            parkinglotService.createParkingspot(lotid, type);

            context.redirect("/lot/"+lotid);
        } catch (Exception e) {
            context.status(404);
        }
    }

    private TYPE getType(Context context, TYPE type) {
        if (context.formParam("type").equalsIgnoreCase("truck"))
            type = TYPE.Truck;
        else if (context.formParam("type").equalsIgnoreCase("handicap"))
            type = TYPE.Handicap;
        return type;
    }

    public void updateParkingspotCtr(Context context) throws Exception {
        try {
            int lotid = Integer.parseInt(context.pathParam("lotid"));
            int spotid = Integer.parseInt(context.pathParam("spotid"));

            TYPE type = TYPE.Regular;
            type = getType(context, type);

            parkinglotService.updateParkingspot(lotid, spotid, type);

            context.redirect("/lot/"+lotid);
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void deleteParkingspotCtr(Context context) throws Exception {
        try {
            deletionService.deleteParkingspot(Integer.parseInt(context.pathParam("lotid")), Integer.parseInt(context.pathParam("spotid")));
            context.redirect("/lot/"+context.pathParam("lotid"));
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void getParkingspotCtr(Context context) throws Exception {
        try {
            context.json(parkinglotService.getParkingspotFromParkingLot(Integer.parseInt(context.pathParam("lotid")), Integer.parseInt(context.pathParam("spotid"))));
            context.status(200);
        } catch (Exception e) {
            context.status(404);
        }
    }
}
