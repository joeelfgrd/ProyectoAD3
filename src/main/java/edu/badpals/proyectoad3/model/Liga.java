package edu.badpals.proyectoad3.model;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Liga")
public class Liga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_liga;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fecha_creacion;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "Tier", nullable = false)
    private String Tier;
}
