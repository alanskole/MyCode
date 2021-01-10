package repository;
import model.*;
import irepository.InterfaceCrud;
import java.util.concurrent.ConcurrentSkipListMap;

public class BookingscheduleRepo implements InterfaceCrud<BookingSchedule> {
    private ConcurrentSkipListMap<Integer, BookingSchedule> bookingSchedules = new ConcurrentSkipListMap<>();
    private ConcurrentSkipListMap<Integer, BookingSchedule> onlyAvailable = new ConcurrentSkipListMap<>();

    public ConcurrentSkipListMap<Integer, BookingSchedule> getBookingSchedules() {
        return bookingSchedules;
    }


    public BookingscheduleRepo() {

    }

    @Override
    public void create(BookingSchedule bookingSchedule) {
        this.getBookingSchedules().put(bookingSchedule.getParkinglot().getId(), bookingSchedule);
    }

    @Override
    public BookingSchedule getById(int bookingscheduleid) {
        return this.getBookingSchedules().get(bookingscheduleid);
    }

    @Override
    public ConcurrentSkipListMap<Integer, BookingSchedule> getAll() {
        return this.getBookingSchedules();
    }

    @Override
    public void delete(int bookingScheduleid) {
        this.getBookingSchedules().remove(bookingScheduleid);
    }
}
