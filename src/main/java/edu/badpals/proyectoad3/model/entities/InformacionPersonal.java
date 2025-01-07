package edu.badpals.proyectoad3.model.entities;
import jakarta.persistence.Embeddable;
@Embeddable
public class InformacionPersonal {
    public String nombre;
    public String apellidos;
    public String pais;

    public InformacionPersonal() {
    }
    public InformacionPersonal(String nombre, String apellidos, String pais) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "InformacionPersonal{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}
