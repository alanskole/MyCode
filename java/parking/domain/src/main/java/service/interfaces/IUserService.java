package service.interfaces;

import model.User;
import java.util.concurrent.ConcurrentSkipListMap;

public interface IUserService extends ILoginService {

    User createUserService(String firstname, String surname, String city, String address, int nummber, int zipcode, String area);

    ConcurrentSkipListMap<Integer, User> getAllUsers() throws Exception;

    User updateUserService(int userid, String forename, String surname, String city, String address, int nummber, int zipcode, String area) throws Exception;
}
