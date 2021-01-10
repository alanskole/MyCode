package service;

import model.ROLE;
import model.User;

public class CurrentUser {
    public static User loggedInAs;

    public static boolean doesUserHaveRightsManager(User user) throws Exception {
        if (checkIfUserIsManagerOrAdmin() || user.getId() == loggedInAs.getId())
            return true;
        else
            throw new Exception("Restricted access!");
    }

    public static boolean doesUserHaveRightsAdmin(User user) throws Exception {
        if (checkIfUserIsAdmin() || user.getId() == loggedInAs.getId())
            return true;
        else
            throw new Exception("Restricted access!");
    }

    public static boolean checkIfUserIsManagerOrAdmin() throws Exception {
        isAnyoneLoggedIn();
        if (loggedInAs.getRole() == ROLE.Administrator || loggedInAs.getRole() == ROLE.Manager)
            return true;
        return false;
    }

    public static boolean checkIfUserIsAdmin() throws Exception {
        isAnyoneLoggedIn();
        if (loggedInAs.getRole() == ROLE.Administrator)
            return true;
        return false;
    }

    public static void isAdmin() throws Exception {
        if (checkIfUserIsAdmin())
            return;
        else
            throw new Exception("Must be administrator to access!");
    }

    public static void isManagerOrAdmin() throws Exception {
        if (checkIfUserIsManagerOrAdmin())
            return;
        else
            throw new Exception("Must be manager or administrator to access!");
    }

    public static void isAnyoneLoggedIn() throws Exception {
        if (loggedInAs == null)
            throw new Exception("No user is logged in!");
    }
}
