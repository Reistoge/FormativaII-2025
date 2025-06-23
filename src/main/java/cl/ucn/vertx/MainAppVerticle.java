package cl.ucn.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class MainAppVerticle     {


    public static void main(String[] args) {
        // Aquí puedes iniciar tu aplicación Vert.x, por ejemplo, desplegando los verticles.
        System.out.println("Iniciando la aplicación Vert.x...");
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new RepositorioTareasVerticle());
        vertx.deployVerticle(new ClienteVerticle());
        vertx.deployVerticle(new ServicioTareasVerticle());
    }






}
