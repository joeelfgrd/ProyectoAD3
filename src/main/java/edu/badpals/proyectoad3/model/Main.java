package edu.badpals.proyectoad3.model;


import edu.badpals.proyectoad3.model.entities.*;
import jakarta.persistence.*;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    private static final Conection_App conectionApp = new Conection_App();

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
//            Liga liga = new Liga();
//            liga.setNombre("League of Legends Championship Series");
//            liga.setFechaCreacion(LocalDate.of(2020, 1, 1));
//            liga.setRegion("NA");
//            liga.setTier("1");
//
//            Conection_App.addLeague(liga);
//
//            Equipo equipo = new Equipo();
//            equipo.setNombre("Giants Gaming");
//            equipo.setFechaCreacion(LocalDate.of(2015, 5, 15));
//            equipo.setRegion("EU");
//            equipo.setTier("2");
//
//            Conection_App.addTeam(equipo);
//
//            EquipoLiga equipoLiga = new EquipoLiga();
//            equipoLiga.setEquipo(em.find(Equipo.class, 3L));
//            equipoLiga.setLiga(em.find(Liga.class, 2L));
//            equipoLiga.setFechaInscripcion(LocalDate.of(2022, 10, 10));
//            equipoLiga.setPrecioPlaza(5000.00);

//            Conection_App.unregisterTeam(equipoLiga);

//            Conection_App.registerTeam(equipoLiga);
//
//
//
//            Conection_App.delteLeagueForID(1L);
//
//            Conection_App.deleteTeamForID(1L);
//
//            Equipo equipoActualizado = new Equipo(3L, "Movistar Riders", LocalDate.of(2015, 5, 15), "EU", "2", null);
//
//            Liga ligaActualizada = new Liga(2L, "Super Liga Orange", LocalDate.of(2020, 1, 1), "EU", "3", null);
////
//            EquipoLiga equipoLigaActualizado = new EquipoLiga(2L, em.find(Equipo.class, 3L), em.find(Liga.class, 2L), LocalDate.of(2021, 10, 10), 1000.00);
//            Conection_App.updateTeam(equipoActualizado);
//            Conection_App.updateLeague(ligaActualizada);
//            Conection_App.updateParticipation(equipoLigaActualizado);
//
//            LolPlayer lolPlayer = new LolPlayer();
//            lolPlayer.setPosicion("Top");
//            lolPlayer.setEarlyShotcaller(true);
//            lolPlayer.setLateShotcaller(false);
//            lolPlayer.setInformacionPersonal(new InformacionPersonal("Juan", "Perez", "ES"));
//            lolPlayer.setNickname("Wunder");
//            lolPlayer.setEquipo(Conection_App.getEquipoFromName("Movistar Riders", conectionApp.crearConexion()));
//
//            Conection_App.addLolPlayer(lolPlayer);
//
//
//            ValorantPlayer valorantPlayer = new ValorantPlayer();
//            valorantPlayer.setRol("Duelista");
//            valorantPlayer.setAgente("Jett");
//            valorantPlayer.setIGL(false);
//            valorantPlayer.setInformacionPersonal(new InformacionPersonal("Juan", "Perez", "ES"));
//            valorantPlayer.setNickname("Nats");
//            valorantPlayer.setEquipo(Conection_App.getEquipoFromName("Movistar Riders", conectionApp.crearConexion()));
//
//            Conection_App.addValoPlayer(valorantPlayer);
//
//
//            Conection_App.deleteLolPlayerForID(1L);
////
//            Conection_App.deleteValoPlayerForID(2L);
//
//
//            LolPlayer lolPlayerActualizado = new LolPlayer(3L, new InformacionPersonal("Rasmus", "Borregaard Winther", "DEN"), "Caps", Conection_App.getEquipoFromName("Movistar Riders", conectionApp.crearConexion()), "Mid", true, false);
////
//            ValorantPlayer valorantPlayerActualizado = new ValorantPlayer(4L, new InformacionPersonal("Oscar", "Cañellas", "ES"), "Mixwell", Conection_App.getEquipoFromName("Movistar Riders", conectionApp.crearConexion()), "Centinela", "Chyper", true);
////
//            Conection_App.updateLolPlayer(lolPlayerActualizado);
//            Conection_App.updateValoPlayer(valorantPlayerActualizado);


            em.getTransaction().commit();

        }

        catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

        finally {
            em.close();
            emf.close();
        }
    }

    public static void generateDB() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Liga liga = new Liga();
            liga.setNombre("Liga de Ejemplo");
            liga.setFechaCreacion(LocalDate.of(2020, 1, 1));
            liga.setRegion("EU");
            liga.setTier("S");



            Equipo equipo = new Equipo();
            equipo.setNombre("Equipo de Prueba");
            equipo.setFechaCreacion(LocalDate.of(2015, 5, 15));
            equipo.setRegion("EU");
            equipo.setTier("A");


            EquipoLiga equipoLiga = new EquipoLiga();
            equipoLiga.setEquipo(equipo);
            equipoLiga.setLiga(liga);
            equipoLiga.setFechaInscripcion(LocalDate.of(2022, 10, 10));
            equipoLiga.setPrecioPlaza(5000.00);


            em.persist(liga);
            em.persist(equipo);
            em.persist(equipoLiga);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
