package edu.badpals.proyectoad3.model.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "equipo_liga", uniqueConstraints = @UniqueConstraint(columnNames = {"id_equipo", "id_liga"}))
public class EquipoLiga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_equipo", nullable = false)
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "id_liga", nullable = false)
    private Liga liga;

    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDate fechaInscripcion;

    @Column(name = "precio_plaza", nullable = false)
    private Double precioPlaza;

    public EquipoLiga() {
    }

    public EquipoLiga(Long id, Equipo equipo, Liga liga, LocalDate fechaInscripcion, Double precioPlaza) {
        this.id = id;
        this.equipo = equipo;
        this.liga = liga;
        this.fechaInscripcion = fechaInscripcion;
        this.precioPlaza = precioPlaza;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public Long getEquipoId() {
        return equipo.getIdEquipo();
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }


    public Liga getLiga() {
        return liga;
    }

    public Long getLigaId() {
        return liga.getIdLiga();
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Double getPrecioPlaza() {
        return precioPlaza;
    }

    public void setPrecioPlaza(Double precioPlaza) {
        this.precioPlaza = precioPlaza;
    }

    @Override
    public String toString() {
        return "EquipoLiga{" +
                "id=" + id +
                ", equipo=" + equipo +
                ", liga=" + liga +
                ", fechaInscripcion=" + fechaInscripcion +
                ", precioPlaza=" + precioPlaza +
                '}';
    }
}
