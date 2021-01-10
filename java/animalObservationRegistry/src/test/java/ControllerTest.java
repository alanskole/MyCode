import io.javalin.Javalin;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ControllerTest {
    private int port = 4567;
    private Javalin app = null;
    ObservationFactoryTest oft;

    @BeforeAll
    void book() throws Exception {
        oft = new ObservationFactoryTest(port, app);
    }

    @Test
    void getAll() throws UnirestException {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/observations/").asString();
        assertEquals(200, response.getStatus());
    }

    @Test
    void getById() throws UnirestException {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/observations/1")
            .asString();
        assertEquals(200, response.getStatus());

        response = Unirest.get("http://localhost:"+port+"/api/observations/777")
            .asString();
        assertEquals(404, response.getStatus());
    }

    @Test
    void getAnimal() throws UnirestException {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/observations/1/animal")
            .asString();
        assertEquals(200, response.getStatus());

        response = Unirest.get("http://localhost:"+port+"/api/observations/7678/animal")
            .asString();
        assertEquals(404, response.getStatus());
    }

    @Test
    void createObservation() throws UnirestException {
        HttpResponse response = Unirest.post("http://localhost:"+port+"/api/observation/create")
            .field("name", "ff")
            .field("animalClass", "aves")
            .field("canFly", "yes")
            .field("venomous", "")
            .field("limbs", "")
            .field("limbless", "")
            .field("order", "ss")
            .field("family", "fsdsd")
            .field("genus", "ssddd")
            .field("species", "fsdsds")
            .field("englishName", "fffff")
            .field("planetName", "fasfaf")
            .field("latitude", "11")
            .field("longitude", "21")
            .field("biome", "dessert")
            .field("timeAndDate", "01-12-2337 17:40")
            .field("amount", "1")
            .field("picturePath", "https://www.peruaves.org/wp-content/uploads/2019/04/passer_domesticus_4.jpg")
            .field("comment", "fffss adsad ss")
            .asString();
        assertEquals(302, response.getStatus());

        response = Unirest.post("http://localhost:"+port+"/api/observation/create")
            .field("name", "ff")
            .field("animalClass", "aves")
            .field("canFly", "yes")
            .field("venomous", "")
            .field("limbs", "")
            .field("limbless", "")
            .field("order", "ss")
            .field("family", "fsdsd")
            .field("genus", "ssddd")
            .field("species", "fsdsds")
            .field("englishName", "fffff")
            .field("planetName", "fasfaf")
            .field("latitude", "ff")
            .field("longitude", "ff")
            .field("biome", "dessert")
            .field("timeAndDate", "01-12-2337 17:40")
            .field("amount", "1")
            .field("picturePath", "https://www.peruaves.org/wp-content/uploads/2019/04/passer_domesticus_4.jpg")
            .field("comment", "fffss adsad ss")
            .asString();
        assertEquals(404, response.getStatus());
    }

    @Test
    void update() throws UnirestException {
        HttpResponse response = Unirest.post("http://localhost:"+port+"/api/observations/1/update")
            .field("name", "ff")
            .field("animalClass", "aves")
            .field("canFly", "yes")
            .field("venomous", "")
            .field("limbs", "")
            .field("limbless", "")
            .field("order", "ss")
            .field("family", "fsdsd")
            .field("genus", "ssddd")
            .field("species", "fsdsds")
            .field("englishName", "fffff")
            .field("planetName", "fasfaf")
            .field("latitude", "11")
            .field("longitude", "21")
            .field("biome", "dessert")
            .field("timeAndDate", "01-12-2337 17:40")
            .field("amount", "1")
            .field("picturePath", "https://www.peruaves.org/wp-content/uploads/2019/04/passer_domesticus_4.jpg")
            .field("comment", "fffss adsad ss")
            .asString();
        assertEquals(302, response.getStatus());

        response = Unirest.post("http://localhost:"+port+"/api/observations/1/update")
            .field("name", "ff")
            .field("animalClass", "aves")
            .field("canFly", "yes")
            .field("venomous", "")
            .field("limbs", "")
            .field("limbless", "")
            .field("order", "ss")
            .field("family", "fsdsd")
            .field("genus", "ssddd")
            .field("species", "fsdsds")
            .field("englishName", "fffff")
            .field("planetName", "fasfaf")
            .field("latitude", "ff")
            .field("longitude", "ff")
            .field("biome", "dessert")
            .field("timeAndDate", "01-12-2337 17:40")
            .field("amount", "1")
            .field("picturePath", "https://www.peruaves.org/wp-content/uploads/2019/04/passer_domesticus_4.jpg")
            .field("comment", "fffss adsad ss")
            .asString();
        assertEquals(404, response.getStatus());
    }


    @AfterAll
    void delete() throws Exception {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/observations/1/delete").asString();
        assertEquals(200, response.getStatus());

        response = Unirest.get("http://localhost:"+port+"/api/observations/1/delete").asString();
        assertEquals(404, response.getStatus());
        oft.quit(app);
    }
}