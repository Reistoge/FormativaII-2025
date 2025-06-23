package cl.ucn.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RepositorioTareasVerticle extends AbstractVerticle {
    private final List<JsonObject> tareas = new ArrayList<JsonObject>();
    private long nextId = 1;



    @Override
    public void start(final Promise<Void> startPromise) throws Exception {


        startPromise.complete();
        // Aquí puedes implementar la lógica de inicio del repositorio de tareas.
        System.out.println("RepositorioTareaVerticle ha iniciado.");


        vertx.eventBus().consumer("tareas.crear", message -> {
             JsonObject body = (JsonObject) message.body();
             JsonObject tarea = new JsonObject()
                     .put("id", nextId)
                     .put("titulo", body.getString("titulo"))
                     .put("descripcion", body.getString("descripcion"))
                     .put("fecha", body.getString("fecha"))
                     .put("completada", false);
                tareas.add(tarea);
                message.reply(new JsonObject()
                        .put("estado", "ok")
                        .put("id", nextId));
                nextId++;

         });
        vertx.eventBus().consumer("tareas.listar", message -> {
                // Convertir la lista de tareas a JSON y enviarla como respuesta
                message.reply(Json.encodePrettily(tareas));
         });


    }

    @Override
    public void stop() {
        // Aquí puedes implementar la lógica de limpieza al detener el repositorio de tareas.
        System.out.println("RepositorioTareaVerticle ha detenido.");
    }
}
