package cl.ucn.vertx;

import io.vertx.core.Vertx;

/**
 * Punto de entrada principal del sistema.
 * Despliega los verticles de repositorio, servicio y cliente.
 */
public class MainAppVerticle {

    public static void main(String[] args) {
        var vertx = Vertx.vertx();

        // Desplegar el verticle que simula la base de datos
        vertx.deployVerticle(new RepositorioTareasVerticle());

        // Desplegar el verticle que manejaría reglas de negocio (en este caso vacío)
        vertx.deployVerticle(new ServicioTareasVerticle());

        // Desplegar el cliente que envía solicitudes al sistema
        vertx.deployVerticle(new ClienteVerticle());
    }
}
