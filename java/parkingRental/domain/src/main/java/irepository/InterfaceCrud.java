package irepository;

import java.util.concurrent.ConcurrentSkipListMap;

public interface InterfaceCrud<Type> {
    void create(Type obj);

    Type getById(int id);

    ConcurrentSkipListMap<Integer, Type> getAll();

    void delete(int id);
}
