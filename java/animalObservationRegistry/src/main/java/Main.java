import io.javalin.Javalin;
import application.Application;
import controller.Controller;
import model.*;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File in = new File("observations.csv");
        File out = new File("output.csv");

        ObservationRepository observationRepository = new ObservationRepository(in, out);

        Controller controller = new Controller(observationRepository);

        Javalin app = null;

        Application application = new Application(app, controller);
    }
}
