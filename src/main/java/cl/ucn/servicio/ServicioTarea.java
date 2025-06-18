package cl.ucn.servicio;

import cl.ucn.dominio.Tarea;
import cl.ucn.repositorio.RepositorioTarea;

import java.time.LocalDate;
import java.util.List;

public class ServicioTarea {

    private final RepositorioTarea repo;

    public ServicioTarea(RepositorioTarea repo) {
        this.repo = repo;
    }

    public Tarea crearTarea(String title, String desc, LocalDate due) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la tarea no puede estar vacío.");
        }
        Tarea nueva = new Tarea();
        nueva.setTitulo(title);
        nueva.setDescripcion(desc);
        nueva.setFechaFinalizacion(due);
        return repo.guardar(nueva);
    }

    public List<Tarea> obtenerTodasLasTareas() {
        return repo.encontrarTodas();
    }

    public List<Tarea> obtenerTareasPendientes() {
        return repo.encontrarCompletas(false);
    }

    public void marcarCompletada(Long id) {
        Tarea tarea = repo.encontrarPorId(id);
        if (tarea == null) {
            throw new IllegalArgumentException("No se encontró la tarea con ID: " + id);
        }
        tarea.setCompletada(true);
        repo.guardar(tarea);
    }

    public void deleteTask(Long id) {
        repo.eliminar(id);
    }
}
