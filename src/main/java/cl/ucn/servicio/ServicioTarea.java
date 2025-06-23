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

        Tarea tarea = new Tarea();
        tarea.setTitulo(title);
        tarea.setDescripcion(desc);
        tarea.setFechaFinalizacion(due);
        tarea.setCompletada(false);
        return this.repo.guardar(tarea);
    }

    public List<Tarea> obtenerTareasPendientes() {
        return this.repo.encontrarCompletas(false);

    }
    public List<Tarea> obtenerTareasCompletadas() {
        return this.repo.encontrarCompletas(true);
    }

    public void marcarCompletada(Long id) {
        Tarea tarea = this.repo.encontrarTodas().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada con ID: " + id));
        tarea.marcarCompleta();
        this.repo.guardar(tarea);
    }

    public void deleteTask(Long id) {
        repo.eliminar(id);
    }
}


