package cl.ucn.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

import java.time.Duration;
import java.util.Random;

public class ClienteVerticle extends AbstractVerticle {
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        System.out.println("ClienteVerticle ha iniciado.");
        startPromise.complete();

        vertx.setPeriodic(Duration.ofSeconds(5).toMillis(), id -> {
            vertx.eventBus().publish("servicio.tareas.crear", generarParametrosTareaAleatorio());
            vertx.eventBus().publish("servicio.tareas.listar", null);
        });
        vertx.eventBus().consumer("servicio.obtener.tareas", message -> {
            System.out.println("Obtener tareas: " + message.body());
        });

    }

    JsonObject generarParametrosTareaAleatorio() {
        JsonObject tarea = new JsonObject()
                .put("titulo", generateRandomWord(5))
                .put("descripcion", generateRandomWord(10));
        return tarea;
    }

    String generateRandomWord(int wordLength) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(wordLength);
        for (int i = 0; i < wordLength; i++) {
            char tmp = (char) ('a' + r.nextInt('z' - 'a'));
            sb.append(tmp);
        }
        return sb.toString();
    }

    String generateRandomDate() {
        Random r = new Random();
        int year = 2000 + r.nextInt(31);
        int month = 1 + r.nextInt(12);
        int day;
        switch (month) {
            case 2:
                boolean leap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
                day = 1 + r.nextInt(leap ? 29 : 28);
                break;
            case 4: case 6: case 9: case 11:
                day = 1 + r.nextInt(30);
                break;
            default:
                day = 1 + r.nextInt(31);
        }
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    @Override
    public void stop() {
        System.out.println("ClienteVerticle ha detenido.");
    }
}