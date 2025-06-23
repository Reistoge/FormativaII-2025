package cl.ucn.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class ServicioTareasVerticle extends AbstractVerticle {



    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        startPromise.complete();
        // Aquí puedes implementar la lógica de inicio del servicio de tareas.
        System.out.println("ServicioTareasVerticle ha iniciado.");



        vertx.eventBus().consumer("servicio.tareas.crear", message -> {
            JsonObject body = (JsonObject) message.body(); // parseamos el mensaje
            System.out.println("Solicitando tarea: " + body);
            vertx.eventBus().request("tareas.crear", body, repoReply -> { // hacemos un request al repositorio
                if (repoReply.succeeded()) {
                    // Reply back to client with repository's response

                    message.reply(repoReply.result().body());
                } else {
                    message.fail(1, "Repository error");
                }
            });
        });



    }

    @Override
    public void stop() {
        // Aquí puedes implementar la lógica de limpieza al detener el servicio de tareas.
        System.out.println("ServicioTareasVerticle ha detenido.");
    }
}
