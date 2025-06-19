package cl.ucn.adhoc;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

