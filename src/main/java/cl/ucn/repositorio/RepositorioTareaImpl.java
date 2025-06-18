package cl.ucn.repositorio;

import cl.ucn.dominio.Tarea;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class RepositorioTareaImpl implements RepositorioTarea {

    private final EntityManager em;

    public RepositorioTareaImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Tarea guardar(Tarea tarea) {
        if (tarea.getId() == null)
            em.persist(tarea);
        else
            tarea = em.merge(tarea);

        return tarea;
    }

    @Override
    public List<Tarea> encontrarTodas() {
        TypedQuery<Tarea> query =  em.createQuery("SELECT t FROM Tarea t", Tarea.class);
        return query.getResultList();
    }

    @Override
    public List<Tarea> encontrarCompletas(boolean status) {
        return em.createQuery("SELECT t FROM Tarea t WHERE t.completada = :estado", Tarea.class)
                .setParameter("estado", status)
                .getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Tarea tarea = em.find(Tarea.class, id);
        if (tarea != null) {
            em.remove(tarea);
        }
    }

    @Override
    public Tarea encontrarPorId(Long id) {
        return em.find(Tarea.class, id); // retorna null si no existe
    }
}
