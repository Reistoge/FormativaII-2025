package cl.ucn.repositorio;

import cl.ucn.dominio.Tarea;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class RepositorioTareaImpl implements RepositorioTarea {

    @PersistenceContext
    private EntityManager em;

    public RepositorioTareaImpl(EntityManager em) {
        this.em = em;
    }

    public RepositorioTareaImpl() {}

    @Override
    public Tarea guardar(Tarea tarea) {
        // Si la tarea ya tiene un ID, significa que es una actualización
        if (tarea.getId() != null) {
            em.getTransaction().begin();
            em.merge(tarea);
            em.getTransaction().commit();
        } else {
            em.getTransaction().begin();
            // Si no tiene ID, es una nueva tarea
            em.persist(tarea);
            em.getTransaction().commit();
        }

        return tarea;
    }

    @Override
    public List<Tarea> encontrarTodas() {
        return em.createQuery("SELECT t FROM Tarea t", Tarea.class).getResultList();

    }

    @Override
    public List<Tarea> encontrarCompletas(boolean status) {
        List<Tarea> tareas = em.createQuery("SELECT t FROM Tarea t WHERE t.completada = :status", Tarea.class)
                .setParameter("status", status)
                .getResultList();

        return tareas;
    }

    @Override
    public void eliminar(Long id) {
        Tarea tarea = em.find(Tarea.class, id);
        if (tarea != null) {
            em.remove(tarea);
        } else {
            throw new IllegalArgumentException("Tarea con ID " + id + " no encontrada.");
        }

    }
}
