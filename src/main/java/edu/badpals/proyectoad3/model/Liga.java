package edu.badpals.proyectoad3.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "liga", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipoLiga> equipoLigas;

    public Liga() {
    }

    public Liga(Long id_liga, String nombre, LocalDate fecha_creacion, String tier, String region, List<EquipoLiga> equipoLigas) {
        this.id_liga = id_liga;
        this.nombre = nombre;
        this.fecha_creacion = fecha_creacion;
        Tier = tier;
        this.region = region;
        this.equipoLigas = equipoLigas;
    }

    public Long getId_liga() {
        return id_liga;
    }

    public void setId_liga(Long id_liga) {
        this.id_liga = id_liga;
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

    public String getTier() {
        return Tier;
    }

    public void setTier(String tier) {
        Tier = tier;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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
                "id_liga=" + id_liga +
                ", nombre='" + nombre + '\'' +
                ", fecha_creacion=" + fecha_creacion +
                ", region='" + region + '\'' +
                ", Tier='" + Tier + '\'' +
                ", equipoLigas=" + equipoLigas +
                '}';
    }
}
