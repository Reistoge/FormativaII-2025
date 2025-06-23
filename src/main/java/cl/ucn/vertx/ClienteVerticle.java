package cl.ucn.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Random;

public class ClienteVerticle extends AbstractVerticle {



    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        System.out.println("ClienteVerticle ha iniciado.");
        startPromise.complete();

        vertx.setPeriodic(Duration.ofSeconds(5).toMillis(),id -> {
            vertx.eventBus().request("servicio.tareas.crear",crearTareaRandom(),reply->{
                if(reply.succeeded()){

                    System.out.println((reply.result().body().toString()));

                }
                else{
                    System.out.println((reply.cause().getMessage()));
                }
            });
            vertx.eventBus().request("servicio.tareas.listar",null,reply->{

            });
        });






    }
    JsonObject crearTareaRandom(){
        JsonObject tarea = new JsonObject()
                .put("titulo", generateRandomWord(5))
                .put("descripcion", generateRandomWord(10))
                .put("fecha", generateRandomDate());
        return  tarea;
    }
    String generateRandomWord(int wordLength) {
        Random r = new Random(); // Intialize a Random Number Generator with SysTime as the seed
        StringBuilder sb = new StringBuilder(wordLength);
        for(int i = 0; i < wordLength; i++) { // For each letter in the word
            char tmp = (char) ('a' + r.nextInt('z' - 'a')); // Generate a letter between a and z
            sb.append(tmp); // Add it to the String
        }
        return sb.toString();
    }
    String generateRandomDate() {
        Random r = new Random();
        int year = 2000 + r.nextInt(31); // 2000-2030
        int month = 1 + r.nextInt(12); // 1-12
        int day;
        switch (month) {
            case 2:
                // Check for leap year
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
        // Aquí puedes implementar la lógica de limpieza al detener el verticle.
        System.out.println("ClienteVerticle ha detenido.");
    }
}
