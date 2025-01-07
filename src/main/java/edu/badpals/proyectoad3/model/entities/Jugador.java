package edu.badpals.proyectoad3.model.entities;


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

    @ManyToOne
    @JoinColumn(name = "Equipo" , nullable = false)
    private Equipo Equipo;

    public Jugador() {
    }

    public Jugador(long id_jugador, InformacionPersonal informacionPersonal, String nickname, Equipo equipo) {
        this.id_jugador = id_jugador;
        this.informacionPersonal = informacionPersonal;
        Nickname = nickname;
        Equipo = equipo;
    }

    public long getId_jugador() {
        return id_jugador;
    }

    public void setId_jugador(long id_jugador) {
        this.id_jugador = id_jugador;
    }

    public InformacionPersonal getInformacionPersonal() {
        return informacionPersonal;
    }

    public void setInformacionPersonal(InformacionPersonal informacionPersonal) {
        this.informacionPersonal = informacionPersonal;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public Equipo getEquipo() {
        return Equipo;
    }

    public String getEquipoNombre() {
        return Equipo.getNombre();
    }

    public void setEquipo(Equipo equipo) {
        Equipo = equipo;
    }



    @Override
    public String toString() {
        return "Jugador{" +
                "id_jugador=" + id_jugador +
                ", informacionPersonal=" + informacionPersonal +
                ", Nickname='" + Nickname + '\'' +
                ", Equipo='" + Equipo.getNombre() + '\'' +
                '}';
    }
}
