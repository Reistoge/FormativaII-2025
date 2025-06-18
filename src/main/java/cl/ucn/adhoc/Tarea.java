package cl.ucn.adhoc;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Tarea {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String descripcion;
    private LocalDate fechaFinalizacion;
    private boolean completada;

    public void marcarCompleta() { this.completada = true; }

    public Long getId() {
        return id;
    }

    // Constructores, getters, setters
}

