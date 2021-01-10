package controller;

import io.javalin.Javalin;
import service.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Date;

import static model.BookingSchedule.dateFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingControllerTest {
    private TestFactoryController tf;
    private int port = 4567;
    private Javalin app = null;

    @BeforeAll
    void book() throws Exception {
        tf = new TestFactoryController(port, app);
        String bkdate = dateFormat.format(DateCheckerService.addHoursToAnyTime(new Date(), 3).getTime());

        HttpResponse response = Unirest.post("http://localhost:"+port+"/bsch/search/booking/book/"+tf.parkinglot.getId()+"/"+tf.user.getId())
            .field("date", bkdate)
            .field("hours", "2")
            .field("spotid", "1")
            .asString();
        assertEquals(302, response.getStatus());

        response = Unirest.post("http://localhost:"+port+"/bsch/search/booking/book/"+tf.parkinglot.getId()+"/"+tf.user.getId())
            .field("date", bkdate)
            .field("hours", "w")
            .field("spotid", "e")
            .asString();
        assertEquals(404, response.getStatus());
    }

    @Test
    void getAll() throws Exception {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/bookings/").asString();
        assertEquals(200, response.getStatus());
    }

    @Test
    void getById() throws UnirestException {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/bookings/10000").asString();
        assertEquals(404, response.getStatus());

        response = Unirest.get("http://localhost:"+port+"/api/bookings/1").asString();
        assertEquals(200, response.getStatus());
    }


    @AfterAll
    void delete() throws Exception {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/bookings/delete/1").asString();
        assertEquals(200, response.getStatus());

        response = Unirest.get("http://localhost:"+port+"/api/bookings/delete/1").asString();
        assertEquals(404, response.getStatus());

        tf.quit(app);
    }
}
