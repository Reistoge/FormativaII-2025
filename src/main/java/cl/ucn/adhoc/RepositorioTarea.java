package cl.ucn.adhoc;

import java.util.List;
import java.util.Optional;

public interface RepositorioTarea {

    Tarea guardar(Tarea task);
    List<Tarea> encontrarTodas();
    List<Tarea> encontrarCompletas(boolean status);
    void eliminar(Long id);

}
