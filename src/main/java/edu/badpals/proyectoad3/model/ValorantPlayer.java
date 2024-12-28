package edu.badpals.proyectoad3.model;
import jakarta.persistence.*;

@Entity
@Table(name="ValorantPlayers")
@DiscriminatorValue(value="1")
public class ValorantPlayer extends Jugador{

}
