package service;

import irepository.InterfaceCrud;
import service.interfaces.IUserService;
import model.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class UserService implements IUserService {
    private InterfaceCrud<User> interfaceCrudUser;
    private int id = 1;

    public UserService(InterfaceCrud<User> interfaceCrudUser) {
        this.interfaceCrudUser = interfaceCrudUser;
    }

    @Override
    public User createUserService(String firstname, String surname, String city, String address, int nummber, int zipcode, String area) {
        Location adr = new Location(city, address, nummber, zipcode, area);
        User usr = new User(id, firstname, surname, adr);
        interfaceCrudUser.create(usr);
        id++;
        return usr;
    }

    @Override
    public User getUserByIdService(int id) throws Exception {
        MapCheckerService.isObjectInTheMap(interfaceCrudUser, id);

        return interfaceCrudUser.getById(id);
    }

    @Override
    public ConcurrentSkipListMap<Integer, User> getAllUsers() throws Exception {
        MapCheckerService.isAnythingInTheMap(interfaceCrudUser);

        return interfaceCrudUser.getAll();
    }

    @Override
    public User updateUserService(int userid, String forename, String surname, String city, String address, int nummber, int zipcode, String area) throws Exception {
        CurrentUser.doesUserHaveRightsManager(interfaceCrudUser.getById(userid));
        MapCheckerService.isObjectInTheMap(interfaceCrudUser, userid);
        Location adr = new Location(city, address, nummber, zipcode, area);
        interfaceCrudUser.getById(userid).setFirstname(forename);
        interfaceCrudUser.getById(userid).setSurname(surname);
        interfaceCrudUser.getById(userid).setLocation(adr);

        return interfaceCrudUser.getById(userid);
    }
}
