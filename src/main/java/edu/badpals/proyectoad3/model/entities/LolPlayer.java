package edu.badpals.proyectoad3.model.entities;
import jakarta.persistence.*;

@Entity
@Table(name="LolPlayers")
@DiscriminatorValue(value="2")
public class LolPlayer extends Jugador{

    @Column(name="Posicion", nullable = false)
    private String posicion;
    @Column(name="EarlyShotcaller")
    private boolean EarlyShotcaller;
    @Column(name="LateShotcaller")
    private boolean LateShotcaller;

    public LolPlayer() {
    }

    public LolPlayer(long id_jugador, InformacionPersonal informacionPersonal, String nickname, Equipo equipo, String posicion, boolean earlyShotcaller, boolean lateShotcaller) {
        super(id_jugador, informacionPersonal, nickname, equipo);
        this.posicion = posicion;
        EarlyShotcaller = earlyShotcaller;
        LateShotcaller = lateShotcaller;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public boolean isEarlyShotCaller() {
        return EarlyShotcaller;
    }

    public void setEarlyShotcaller(boolean earlyShotcaller) {
        EarlyShotcaller = earlyShotcaller;
    }

    public boolean isLateShotCaller() {
        return LateShotcaller;
    }

    public boolean getEarlyShotcaller() {
        return EarlyShotcaller;
    }

    public boolean getLateShotcaller() {
        return LateShotcaller;
    }



    public void setLateShotcaller(boolean lateShotcaller) {
        LateShotcaller = lateShotcaller;
    }

    @Override
    public String toString() {
        return "LolPlayer{" +
                "posicion='" + posicion + '\'' +
                ", EarlyShotcaller=" + EarlyShotcaller +
                ", LateShotcaller=" + LateShotcaller +
                '}';
    }
}

