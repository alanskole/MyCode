package fakerepository;

import irepository.InterfaceCrud;
import model.BookingSchedule;
import java.util.concurrent.ConcurrentSkipListMap;

public class FakeBookingScheduleRepo implements InterfaceCrud<BookingSchedule> {
    ConcurrentSkipListMap<Integer, BookingSchedule> list = new ConcurrentSkipListMap<>();

    @Override
    public void create(BookingSchedule bookingSchedule) {
        list.put(bookingSchedule.getParkinglot().getId(), bookingSchedule);
    }

    @Override
    public BookingSchedule getById(int id) {
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
