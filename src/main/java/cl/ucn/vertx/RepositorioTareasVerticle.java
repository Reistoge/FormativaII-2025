package cl.ucn.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Este verticle actúa como una "base de datos" simulada, almacenando tareas en memoria.
 * Escucha eventos del EventBus para crear, completar y listar tareas.
 */
public class RepositorioTareasVerticle extends AbstractVerticle {
    private final List<JsonObject> tareas = new ArrayList<>(); // Lista simulada de tareas
    private long nextId = 1; // Contador incremental para IDs de tareas

    @Override
    public void start() {

        // Crear nueva tarea: escucha en "tareas.crear"
        vertx.eventBus().consumer("tareas.crear", message -> {
            JsonObject body = (JsonObject) message.body();
            JsonObject tarea = new JsonObject()
                    .put("id", nextId++)
                    .put("titulo", body.getString("titulo"))
                    .put("descripcion", body.getString("descripcion"))
                    .put("completada", false);
            tareas.add(tarea);
            message.reply(new JsonObject().put("estado", "ok").put("id", tarea.getLong("id")));
        });

        // Marcar una tarea como completada: escucha en "tareas.completar"
        vertx.eventBus().consumer("tareas.completar", message -> {
            JsonObject body = (JsonObject) message.body();
            Long id = body.getLong("id");
            for (JsonObject tarea : tareas) {
                if (tarea.getLong("id").equals(id)) {
                    tarea.put("completada", true);
                    message.reply(new JsonObject().put("estado", "ok"));
                    return;
                }
            }
            message.fail(404, "Tarea no encontrada");
        });

        // Listar tareas pendientes (no completadas): escucha en "tareas.listarPendientes"
        vertx.eventBus().consumer("tareas.listarPendientes", message -> {
            JsonArray resultado = new JsonArray();
            for (JsonObject tarea : tareas) {
                if (!tarea.getBoolean("completada", false)) {
                    resultado.add(tarea);
                }
            }
            message.reply(resultado);
        });

        // Listar todas las tareas sin filtrar
        vertx.eventBus().consumer("tareas.listarTodas", message -> {
            message.reply(new JsonArray(tareas));
        });
    }
}