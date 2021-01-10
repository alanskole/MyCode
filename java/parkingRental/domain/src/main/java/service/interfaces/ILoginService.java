package service.interfaces;

import model.User;

public interface ILoginService {
    User getUserByIdService(int id) throws Exception;
}
