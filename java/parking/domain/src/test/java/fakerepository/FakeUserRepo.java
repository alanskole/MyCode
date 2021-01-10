package fakerepository;

import irepository.InterfaceCrud;
import model.User;
import java.util.concurrent.ConcurrentSkipListMap;

public class FakeUserRepo implements InterfaceCrud<User> {
    ConcurrentSkipListMap<Integer, User> list = new ConcurrentSkipListMap<>();

    @Override
    public void create(User user) {
        list.put(user.getId(), user);
    }

    @Override
    public User getById(int id) {
        return list.get(id);
    }

    @Override
    public ConcurrentSkipListMap getAll() {
        return list;
    }

    @Override
    public void delete(int id) {
        list.remove(id);
    }
}
