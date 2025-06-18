package cl.ucn.repositorio;

import cl.ucn.dominio.Tarea;

import java.util.List;

public interface RepositorioTarea {

    Tarea guardar(Tarea task);
    List<Tarea> encontrarTodas();
    List<Tarea> encontrarCompletas(boolean status);
    void eliminar(Long id);
    Tarea encontrarPorId(Long id); // ← nuevo método

}
