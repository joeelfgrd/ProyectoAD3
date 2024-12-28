package edu.badpals.proyectoad3.model;
import jakarta.persistence.*;

@Entity
@Table(name="LolPlayers")
@DiscriminatorValue(value="2")
public class LolPlayer extends Jugador{

}

