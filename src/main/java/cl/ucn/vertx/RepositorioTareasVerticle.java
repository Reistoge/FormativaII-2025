package cl.ucn.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTareasVerticle extends AbstractVerticle {
    private final List<JsonObject> tareas = new ArrayList<>();
    private long nextId = 1;

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        startPromise.complete();
        System.out.println("RepositorioTareaVerticle ha iniciado.");

        vertx.eventBus().<JsonObject>consumer("tareas.crear", message -> {
            JsonObject body = (JsonObject) message.body();
            JsonObject tarea = new JsonObject()
                    .put("id", nextId)
                    .put("titulo", body.getString("titulo"))
                    .put("descripcion", body.getString("descripcion"))
                    .put("fecha", LocalDate.now().toString())
                    .put("completada", false);
            tareas.add(tarea);
            System.out.println("Tarea creada: " + tarea);
            nextId++;
        });


        vertx.eventBus().consumer("tareas.listar", message -> {
            System.out.println("Listado de tareas solicitado:");
            vertx.eventBus().publish("servicio.tareas.listadas", Json.encodePrettily(tareas));
        });
    }

    @Override
    public void stop() {
        System.out.println("RepositorioTareaVerticle ha detenido.");
    }
}