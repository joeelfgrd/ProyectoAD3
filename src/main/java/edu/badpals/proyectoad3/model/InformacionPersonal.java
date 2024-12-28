package edu.badpals.proyectoad3.model;
import jakarta.persistence.Embeddable;
@Embeddable
public class InformacionPersonal {
    private String nombre;
    private String apellidos;
    private String pais;
}
