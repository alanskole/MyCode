package service.interfaces;

import model.BookingSchedule;
import model.Parkinglot;
import model.TYPE;
import java.util.concurrent.ConcurrentSkipListMap;

public interface IBookingScheduleService {
    BookingSchedule getOnlyAvailable();

    BookingSchedule getBookingScheduleByIdService(int bookingscheduleid) throws Exception;

    ConcurrentSkipListMap<Integer, BookingSchedule> getAllBookingSchedules() throws Exception;

    void deleteBookingScheduleService(int bookingscheduleid) throws Exception;

    BookingSchedule createForXDays(Parkinglot parkinglot, int days) throws Exception;

    BookingSchedule getOnlyAvailableMap(int parkinglotid, String dateAndTime, int hours, TYPE type) throws Exception;

}
