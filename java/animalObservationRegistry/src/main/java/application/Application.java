package application;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.vue.JavalinVue;
import io.javalin.plugin.rendering.vue.VueComponent;
import controller.Controller;

public class Application {
    public Application(Javalin app, Controller controller) {
        app = Javalin.create().start();
        app.config.enableWebjars();
        JavalinVue.rootDirectory("/vue", Location.CLASSPATH);

        app.get("/", ctx -> ctx.redirect("/observations"));

        app.get("/observations", new VueComponent("observations-overview"));

        app.get("/observations/:id", new VueComponent("observation-detail"));

        app.get("/observations/:id/animal", new VueComponent("animal-detail"));

        app.get("/observations/:id/update", new VueComponent("observation-update"));

        app.get("/observation/create", new VueComponent("observation-create"));



        app.get("/api/observations", ctx -> controller.getObservations(ctx));

        app.get("/api/observations/:id", ctx -> controller.getObservation(ctx));

        app.get("/api/observations/:id/animal", ctx -> controller.getAnimal(ctx));

        app.get("/api/observations/:id/delete", ctx -> controller.deleteOrUpdateObservation(ctx));

        app.post("/api/observations/:id/update", ctx -> controller.deleteOrUpdateObservation(ctx));

        app.post("/api/observation/create", ctx -> controller.createObservation(ctx));
    }

    public Application(int port, Javalin app, Controller controller) {
        app = Javalin.create().start(port);
        app.config.enableWebjars();
        JavalinVue.rootDirectory("/vue", Location.CLASSPATH);

        app.get("/", ctx -> ctx.redirect("/observations"));

        app.get("/observations", new VueComponent("observations-overview"));

        app.get("/observations/:id", new VueComponent("observation-detail"));

        app.get("/observations/:id/animal", new VueComponent("animal-detail"));

        app.get("/observations/:id/update", new VueComponent("observation-update"));

        app.get("/observation/create", new VueComponent("observation-create"));



        app.get("/api/observations", ctx -> controller.getObservations(ctx));

        app.get("/api/observations/:id", ctx -> controller.getObservation(ctx));

        app.get("/api/observations/:id/animal", ctx -> controller.getAnimal(ctx));

        app.get("/api/observations/:id/delete", ctx -> controller.deleteOrUpdateObservation(ctx));

        app.post("/api/observations/:id/update", ctx -> controller.deleteOrUpdateObservation(ctx));

        app.post("/api/observation/create", ctx -> controller.createObservation(ctx));
    }
}
