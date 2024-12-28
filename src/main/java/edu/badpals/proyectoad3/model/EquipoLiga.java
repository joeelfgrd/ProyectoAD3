package edu.badpals.proyectoad3.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "equipo_liga", uniqueConstraints = @UniqueConstraint(columnNames = {"id_equipo", "id_liga"}))
public class EquipoLiga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idEquipo", nullable = false)
    private Equipos equipo;

    @ManyToOne
    @JoinColumn(name = "idLiga", nullable = false)
    private Liga liga;

    @Column(name = "Fecha_inscripcion", nullable = false)
    private LocalDate fechaInscripcion;

    @Column(name = "Precio_plaza", nullable = false)
    private Double precioPlaza;

    // Getters, setters y constructores
}
