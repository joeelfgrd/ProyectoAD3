package edu.badpals.proyectoad3.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ligas")
public class Liga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLiga;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "tier", nullable = false)
    private String tier;

    @OneToMany(mappedBy = "liga", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipoLiga> equipoLigas;

    public Liga() {
    }

    public Liga(Long idLiga, String nombre, LocalDate fechaCreacion, String region, String tier, List<EquipoLiga> equipoLigas) {
        this.idLiga = idLiga;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.region = region;
        this.tier = tier;
        this.equipoLigas = equipoLigas;
    }

    public Long getIdLiga() {
        return idLiga;
    }

    public void setIdLiga(Long idLiga) {
        this.idLiga = idLiga;
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
        return "Liga{" +
                "idLiga=" + idLiga +
                ", nombre='" + nombre + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", region='" + region + '\'' +
                ", tier='" + tier + '\'' +
                ", equipoLigas=" + equipoLigas +
                '}';
    }
}
