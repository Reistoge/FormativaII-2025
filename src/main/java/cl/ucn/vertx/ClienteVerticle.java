package cl.ucn.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

/**
 * Simula un cliente que interactúa con el sistema mediante el EventBus.
 * Envía una tarea nueva y luego solicita listar las tareas pendientes.
 */
public class ClienteVerticle extends AbstractVerticle {
    @Override
    public void start() {

        // Crear un objeto Json con los datos de la nueva tarea
        JsonObject nuevaTarea = new JsonObject()
                .put("titulo", "Estudiar para la prueba")
                .put("descripcion", "Capítulos 3 y 4");

        // Publicar mensaje para crear tarea
        vertx.eventBus().request("tareas.crear", nuevaTarea, respuesta -> {
            if (respuesta.succeeded()) {
                System.out.println("Tarea creada: " + respuesta.result().body());

                // Luego, pedir listar tareas pendientes
                vertx.eventBus().request("tareas.listarPendientes", new JsonObject(), res2 -> {
                    if (res2.succeeded()) {
                        System.out.println("Tareas pendientes: " + res2.result().body());
                    }
                });
            }
        });
    }
}