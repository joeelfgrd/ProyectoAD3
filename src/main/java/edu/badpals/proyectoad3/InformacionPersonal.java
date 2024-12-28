package edu.badpals.proyectoad3;
import jakarta.persistence.Embeddable;

public class InformacionPersonal {



    @Embeddable
    public class Direccion {
        private String calle;
        private String ciudad;
        private String codigoPostal;
        private String pais;
}
