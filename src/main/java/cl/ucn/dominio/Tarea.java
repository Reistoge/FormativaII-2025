package cl.ucn.dominio;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tarea")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String descripcion;
    @Column(name = "fecha_finalizacion")
    private LocalDate fechaFinalizacion;
    private boolean completada;

    public void marcarCompleta() { this.completada = true; }

    public Long getId() {
        return id;
    }

    public Tarea(){}

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public boolean isCompletada() {
        return completada;
    }
    // Constructores, getters, setters
}

