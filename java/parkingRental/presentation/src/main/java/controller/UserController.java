package controller;

import io.javalin.http.Context;
import service.interfaces.IDeletionServiceUser;
import service.interfaces.IUserService;
import service.CurrentUser;

public class UserController {
    private final IUserService userService;
    private final IDeletionServiceUser deletionService;

    public UserController(IUserService userService, IDeletionServiceUser deletionService) {
        this.userService = userService;
        this.deletionService = deletionService;
    }

    public IUserService getUserService() {
        return userService;
    }

    public void createUserCtr(Context context) {
        try {
            String forename = context.formParam("firstname");
            String surname = context.formParam("surname");
            String city = (context.formParam("city"));
            String address = (context.formParam("address"));
            int number = (Integer.parseInt(context.formParam("number")));
            int zipcode = (Integer.parseInt(context.formParam("zipcode")));
            String area = (context.formParam("area"));

            userService.createUserService(forename, surname, city, address, number, zipcode, area);

            context.redirect("/");
        } catch (Exception e) {
            context.status(404);
        }

    }

    public void deleteUserCtr(Context context) {
        try {
            if (CurrentUser.loggedInAs.getId() == Integer.parseInt(context.pathParam("userid"))) {
                deletionService.deleteUserFromMap(Integer.parseInt(context.pathParam("userid")));
                context.redirect("/");
            } else {
                deletionService.deleteUserFromMap(Integer.parseInt(context.pathParam("userid")));
                context.redirect("/users/");
            }
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void updateUserCtr(Context context) {
        try {
            int id = Integer.parseInt(context.pathParam("userid"));
            String forename = context.formParam("firstname");
            String surname = context.formParam("surname");
            String city = (context.formParam("city"));
            String address = (context.formParam("address"));
            int number = (Integer.parseInt(context.formParam("number")));
            int zipcode = (Integer.parseInt(context.formParam("zipcode")));
            String area = (context.formParam("area"));

            userService.updateUserService(id, forename, surname, city, address, number, zipcode, area);
            context.redirect("/users/" + id);
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void getUserCtr(Context context) {
        try {
            context.json(userService.getUserByIdService(Integer.parseInt(context.pathParam("userid"))));
            context.status(200);
        } catch (Exception e) {
            context.status(404);
        }
    }

    public void getAllUserCtr(Context context) {
        try {
            context.json(userService.getAllUsers());
            context.status(200);
        } catch (Exception e) {
            context.status(404);
        }
    }
}
