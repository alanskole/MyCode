import io.javalin.Javalin;
import application.Application;
import controller.Controller;
import model.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ObservationFactoryTest {
    static ObservationRepository observationRepository;
    static Animalia amphibian;
    static Animalia reptile;
    static Animalia bird;
    static Location location1;
    static Location location2;
    static Location location3;
    static Observation observation1Seven;
    static Observation observation2Four;
    static Observation observation3One;
    static Controller controller;
    static Observation observationController;
    static Animalia animalController;
    static Application application;

    public ObservationFactoryTest() throws Exception {
        observationRepository = new ObservationRepository();
        amphibian = new Amphibia("Gymnophiona", "Herpelidae", "Boulengerula", "Boulengerula boulengeri", "Boulengerula boulengeri", true);
        reptile = new Reptilia("Squamata", "Elapidae", "Ophiophagus", "Ophiophagus hannah", "King Cobra", true, 0);
        bird = new Aves("Passeriformes", "Passeridae", "Passer", "Passer domesticus", "House sparrow", true );
        location1 = new Location(new Planet("Uranus"), 20.0001, (-2.4451), new ArrayList<Biome>(Arrays.asList(new Biome("Tropical Rain Forest"))));
        location2 = new Location(new Planet("Saturn"), 24.4211, (-31.8343), new ArrayList<Biome>(Arrays.asList(new Biome("Tropical Rain Forest"), new Biome("Temperate Forest"))));
        location3 = new Location(new Planet("Venus"), 2.3, -243.3, new ArrayList<Biome>(Arrays.asList(new Biome("Tundra"))));
         observationRepository.createObservation(
            "Slimy litte Boulengerula boulengeri",
            amphibian,
            location1,
            "22-09-2337 16:49",
            1,
            "https://calphotos.berkeley.edu/imgs/512x768/0000_0000/0116/4224.jpeg",
            "Slimy little worm looking thing.");
         observation1Seven = observationRepository.getObservation(1);
        observationRepository.createObservation(
            "KING COBRAS MATING!!!!!!!",
            reptile,
            location2,
            ("04-06-2337 08:56"),
            2,
            "https://cdn.newsapi.com.au/image/v1/a30379e7e1f18faf41ef1b6545a67562?width=650",
            "Better call my spaceship ASAP, two deadly King Cobras are coming after me after I accidentally disturbed their vigorous lovemaking session!! They are going to do me harder than they were doing each other if I can't get in my spaceship in heartbeat! Maybe this is it for me? I can see my life flashing before my eyes, NOOO I'm too young to die, MOMMY HEEEELP!");
        observation2Four = observationRepository.getObservation(2);
        observationRepository.createObservation(
            "Little Sparrows",
            bird,
            location3,
            ("01-12-2337 17:40"),
            2,
            "https://www.peruaves.org/wp-content/uploads/2019/04/passer_domesticus_4.jpg",
            "Little sparrows chilling out after a long day of just chilling because they can!");
        observation3One = observationRepository.getObservation(3);
    }

    public ObservationFactoryTest(int port, Javalin app) throws Exception {
        controller = new Controller(new ObservationRepository());
        bird = new Aves("Passeriformes", "Passeridae", "Passer", "Passer domesticus", "House sparrow", true );

        controller.getObservationRepository().createObservation(
            "Little Sparrows",
            bird,
            location3,
            ("01-12-2337 17:40"),
            2,
            "https://www.peruaves.org/wp-content/uploads/2019/04/passer_domesticus_4.jpg",
            "Little sparrows chilling out after a long day of just chilling because they can!");

        observationController = controller.getObservationRepository().getObservation(1);
        animalController = controller.getObservationRepository().getAnimal(1);

        application = new Application(port, app, controller);
    }

    public void quit(Javalin app) {
        if (app != null)
            app.stop();
    }
}
