package edu.badpals.proyectoad3.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Equipos")
public class Equipos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_equipo;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fecha_creacion;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "Tier", nullable = false)
    private String Tier;

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipoLiga> equipoLigas;

    public Equipos() {
    }

    public Equipos(Long id_equipo, List<EquipoLiga> equipoLigas, String tier, String region, LocalDate fecha_creacion, String nombre) {
        this.id_equipo = id_equipo;
        this.equipoLigas = equipoLigas;
        Tier = tier;
        this.region = region;
        this.fecha_creacion = fecha_creacion;
        this.nombre = nombre;
    }

    public Long getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(Long id_equipo) {
        this.id_equipo = id_equipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDate fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTier() {
        return Tier;
    }

    public void setTier(String tier) {
        Tier = tier;
    }

    public List<EquipoLiga> getEquipoLigas() {
        return equipoLigas;
    }

    public void setEquipoLigas(List<EquipoLiga> equipoLigas) {
        this.equipoLigas = equipoLigas;
    }

    @Override
    public String toString() {
        return "Equipos{" +
                "id_equipo=" + id_equipo +
                ", nombre='" + nombre + '\'' +
                ", fecha_creacion=" + fecha_creacion +
                ", region='" + region + '\'' +
                ", Tier='" + Tier + '\'' +
                ", equipoLigas=" + equipoLigas +
                '}';
    }
}
