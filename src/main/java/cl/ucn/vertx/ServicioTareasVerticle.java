package cl.ucn.vertx;

import io.vertx.core.AbstractVerticle;

/**
 * Verticle reservado para agregar lógica de negocio adicional si se desea.
 * Actualmente no intercepta mensajes.
 */
public class ServicioTareasVerticle extends AbstractVerticle {
    @Override
    public void start() {
        // Este verticle está disponible para lógica futura de reglas de negocio
    }
}
