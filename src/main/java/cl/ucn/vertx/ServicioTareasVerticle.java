package cl.ucn.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class ServicioTareasVerticle extends AbstractVerticle {

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        startPromise.complete();
        System.out.println("ServicioTareasVerticle ha iniciado.");

        vertx.eventBus().consumer("servicio.tareas.crear", message -> {
            JsonObject body = (JsonObject) message.body();
            System.out.println("Servicio recibió tarea para crear: " + body);
            vertx.eventBus().publish("tareas.crear", body);
        });

        vertx.eventBus().consumer("servicio.tareas.listar", message -> {
            System.out.println("Servicio recibió solicitud de listar tareas.");
            vertx.eventBus().publish("tareas.listar", null);
        });

        // Listen for repository's response and forward to client
        vertx.eventBus().consumer("servicio.tareas.listadas", message -> {
            vertx.eventBus().publish("servicio.obtener.tareas", message.body());
        });

    }

    @Override
    public void stop() {
        System.out.println("ServicioTareasVerticle ha detenido.");
    }
}