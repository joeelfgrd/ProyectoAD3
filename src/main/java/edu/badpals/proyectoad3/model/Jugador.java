package edu.badpals.proyectoad3.model;


import jakarta.persistence.*;


@Entity
@Table(name="personal")
@Inheritance(strategy= InheritanceType.JOINED)
@DiscriminatorColumn(name="tipo_jugador", discriminatorType=DiscriminatorType.INTEGER)
@DiscriminatorValue(value="0")
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_jugador;

    @Embedded
    private InformacionPersonal informacionPersonal;
    @Column(name="Nickname", unique = true, nullable = false)
    private String Nickname;
    @Column(name="Equipo", unique = true, nullable = false)
    private String Equipo;
}
