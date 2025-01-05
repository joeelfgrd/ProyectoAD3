package edu.badpals.proyectoad3.model.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "equipos")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "tier", nullable = false)
    private String tier;

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipoLiga> equipoLigas;

    public Equipo() {
    }

    public Equipo(Long idEquipo, String nombre, LocalDate fechaCreacion, String region, String tier, List<EquipoLiga> equipoLigas) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.region = region;
        this.tier = tier;
        this.equipoLigas = equipoLigas;
    }

    public Long getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Long idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public List<EquipoLiga> getEquipoLigas() {
        return equipoLigas;
    }

    public void setEquipoLigas(List<EquipoLiga> equipoLigas) {
        this.equipoLigas = equipoLigas;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "idEquipo=" + idEquipo +
                ", nombre='" + nombre + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", region='" + region + '\'' +
                ", tier='" + tier + '\'' +
                ", equipoLigas=" + equipoLigas +
                '}';
    }
}
