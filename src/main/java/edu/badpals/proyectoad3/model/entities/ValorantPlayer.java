package edu.badpals.proyectoad3.model.entities;
import jakarta.persistence.*;

@Entity
@Table(name="ValorantPlayers")
@DiscriminatorValue(value="1")
public class ValorantPlayer extends Jugador{
    @Column(name="rol", nullable = false)
    private String rol;
    @Column(name="agente")
    private String agente;
    @Column(name="IGL")
    private boolean IGL;

    public ValorantPlayer() {
    }

    public ValorantPlayer(long id_jugador, InformacionPersonal informacionPersonal, String nickname, String equipo, String rol, String agente, boolean IGL) {
        super(id_jugador, informacionPersonal, nickname, equipo);
        this.rol = rol;
        this.agente = agente;
        this.IGL = IGL;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public boolean isIGL() {
        return IGL;
    }

    public void setIGL(boolean IGL) {
        this.IGL = IGL;
    }

    @Override
    public String toString() {
        return "ValorantPlayer{" +
                "rol='" + rol + '\'' +
                ", agente='" + agente + '\'' +
                ", IGL=" + IGL +
                '}';
    }
}
