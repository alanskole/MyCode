package fakerepository;

import irepository.InterfaceCrud;
import model.Booking;
import java.util.concurrent.ConcurrentSkipListMap;

public class FakeBookingRepo implements InterfaceCrud<Booking> {
    ConcurrentSkipListMap<Integer, Booking> list = new ConcurrentSkipListMap<>();

    @Override
    public void create(Booking booking) {
        list.put(booking.getId(), booking);
    }

    @Override
    public Booking getById(int id) {
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
