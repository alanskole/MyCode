package repository;
import irepository.InterfaceCrud;
import model.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class BookingRepo implements InterfaceCrud<Booking> {
    private ConcurrentSkipListMap<Integer, Booking> bookings = new ConcurrentSkipListMap<>();

    public ConcurrentSkipListMap<Integer, Booking> getBookings() {
        return bookings;
    }

    public BookingRepo() {

    }

    @Override
    public void create(Booking booking) {
        this.getBookings().put(booking.getId(), booking);
    }

    @Override
    public Booking getById(int bookingid) {
         return this.getBookings().get(bookingid);
    }

    @Override
    public ConcurrentSkipListMap<Integer, Booking> getAll() {
        return this.getBookings();
    }

    @Override
    public void delete(int bookingid) {
        this.getBookings().remove(bookingid);
    }
}
