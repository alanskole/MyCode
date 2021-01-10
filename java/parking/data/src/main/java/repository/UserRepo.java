package repository;
import model.*;
import irepository.InterfaceCrud;
import java.util.concurrent.ConcurrentSkipListMap;

public class UserRepo implements InterfaceCrud<User>{
    private ConcurrentSkipListMap<Integer, User> users = new ConcurrentSkipListMap<>();

    public UserRepo() {

    }

    @Override
    public void create(User user) {
        this.getAll().put(user.getId(), user);
    }

    @Override
    public User getById(int id) {
            return this.getAll().get(id);
    }

    @Override
    public ConcurrentSkipListMap<Integer, User> getAll() {
        return users;
    }

    @Override
    public void delete(int userid) {
        this.getAll().remove(userid);
    }
}
