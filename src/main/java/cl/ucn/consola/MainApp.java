package cl.ucn.consola;

import cl.ucn.repositorio.RepositorioTarea;
import cl.ucn.repositorio.RepositorioTareaImpl;
import cl.ucn.servicio.ServicioTarea;
import cl.ucn.dominio.Tarea;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
        RepositorioTarea repo = new RepositorioTareaImpl(emf.createEntityManager());
        ServicioTarea servicio = new ServicioTarea(repo);

//        servicio.crearTarea("B ", "Desc B", LocalDate.now().plusDays(5));
//        List<Tarea> tareas = servicio.obtenerTareasPendientes();
//        tareas.forEach(System.out::println);

    }
}