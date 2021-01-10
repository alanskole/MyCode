package controller;

import service.interfaces.ILoginService;
import io.javalin.http.Context;
import service.CurrentUser;

public class LoginController {
    private ILoginService userService;

    public LoginController(ILoginService userService) {
        this.userService = userService;
    }

    public void setCurrentUser(Context context) throws Exception {
        CurrentUser.loggedInAs = userService.getUserByIdService(Integer.parseInt(context.pathParam("userid")));
        context.redirect("/lot/");
    }

    public void getCurrentUser(Context context) {
        if (CurrentUser.loggedInAs == null)
            context.json("logged out");
        else
            context.json(CurrentUser.loggedInAs);
    }

    public void logOut(Context context) {
        CurrentUser.loggedInAs = null;
        context.redirect("/");
    }

    public void canAccess(Context context) throws Exception {
        if (CurrentUser.checkIfUserIsManagerOrAdmin())
            context.json(true);
        else
            context.json(false);
    }

    public void isAdminCtr(Context context) throws Exception {
        if (CurrentUser.checkIfUserIsAdmin())
            context.json(true);
        else
            context.json(false);
    }
}
