package cl.ucn.adhoc;

import io.vertx.core.net.impl.pool.Task;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
        RepositorioTarea repo = new RepositorioTareaImpl(emf.createEntityManager());
        ServicioTarea servicio = new ServicioTarea(repo);

        servicio.crearTarea("Preparar presentación", "Exponer capítulo 3", LocalDate.now().plusDays(2));
        List<Tarea> tareas = servicio.obtenerTareasPendientes();
        tareas.forEach(System.out::println);
    }
}