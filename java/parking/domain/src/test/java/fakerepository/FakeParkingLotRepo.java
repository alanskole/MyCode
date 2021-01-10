package fakerepository;

import irepository.InterfaceCrud;
import model.Parkinglot;
import java.util.concurrent.ConcurrentSkipListMap;

public class FakeParkingLotRepo implements InterfaceCrud<Parkinglot> {
    ConcurrentSkipListMap<Integer, Parkinglot> list = new ConcurrentSkipListMap<>();

    @Override
    public void create(Parkinglot parkinglot) {
        list.put(parkinglot.getId(), parkinglot);
    }

    @Override
    public Parkinglot getById(int id) {
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
