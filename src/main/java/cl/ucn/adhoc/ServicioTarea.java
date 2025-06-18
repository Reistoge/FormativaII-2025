package cl.ucn.adhoc;

import java.time.LocalDate;
import java.util.List;

public class ServicioTarea {

    private final RepositorioTarea repo;

    public ServicioTarea(RepositorioTarea repo) {
        this.repo = repo;
    }

    public Tarea crearTarea(String title, String desc, LocalDate due) {
        return null;
    }

    public List<Tarea> obtenerTareasPendientes() {
        return null;
    }

    public void marcarCompletada(Long id) {

    }

    public void deleteTask(Long id) {

    }
}


