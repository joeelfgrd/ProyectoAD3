package edu.badpals.proyectoad3.model;

import edu.badpals.proyectoad3.DAO.*;
import edu.badpals.proyectoad3.model.entities.*;
import jakarta.persistence.*;

import java.time.LocalDate;

public class Main {
    private static final ConnectionDAO conectionApp = new ConnectionDAO();

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            // Crear y agregar liga
            Liga liga = new Liga();
            liga.setNombre("League of Legends Championship Series");
            liga.setFechaCreacion(LocalDate.of(2020, 1, 1));
            liga.setRegion("NA");
            liga.setTier("1");

            LigasDAO.addLeague(liga);

            // Crear y agregar equipo
            Equipo equipo = new Equipo();
            equipo.setNombre("Giants Gaming");
            equipo.setFechaCreacion(LocalDate.of(2015, 5, 15));
            equipo.setRegion("EU");
            equipo.setTier("2");

            EquipoDAO.addTeam(equipo);

            // Crear y registrar equipo en liga
            EquipoLiga equipoLiga = new EquipoLiga();
            equipoLiga.setEquipo(em.find(Equipo.class, 2L)); // Verificación aquí
            if (equipoLiga.getEquipo() == null) {
                throw new IllegalStateException("Equipo no encontrado en la base de datos");
            }

            equipoLiga.setLiga(em.find(Liga.class, 2L)); // Asegurarse de que la liga exista
            if (equipoLiga.getLiga() == null) {
                throw new IllegalStateException("Liga no encontrada en la base de datos");
            }

            equipoLiga.setFechaInscripcion(LocalDate.of(2022, 10, 10));
            equipoLiga.setPrecioPlaza(5000.00);

            // Desinscribir y registrar el equipo
            EquipoLigaDAO.unregisterTeam(equipoLiga);
            EquipoLigaDAO.registerTeam(equipoLiga);

            // Eliminar liga y equipo por ID
            //ConnectionDAO.deleteTeamForID(1L);

            // Actualizar equipo, liga y participación
            Equipo equipoActualizado = new Equipo(3L, "Movistar Riders", LocalDate.of(2015, 5, 15), "EU", "2", null);
            Liga ligaActualizada = new Liga(2L, "Super Liga Orange", LocalDate.of(2020, 1, 1), "EU", "3", null);
            EquipoLiga equipoLigaActualizado = new EquipoLiga(2L, em.find(Equipo.class, 3L), em.find(Liga.class, 2L), LocalDate.of(2021, 10, 10), 1000.00);

            EquipoDAO.updateTeam(equipoActualizado);
            LigasDAO.updateLeague(ligaActualizada);
            EquipoLigaDAO.updateParticipation(equipoLigaActualizado);

            // Agregar jugadores de LoL y Valorant
            LolPlayer lolPlayer = new LolPlayer();
            lolPlayer.setPosicion("Top");
            lolPlayer.setEarlyShotcaller(true);
            lolPlayer.setLateShotcaller(false);
            lolPlayer.setInformacionPersonal(new InformacionPersonal("Juan", "Perez", "ES"));
            lolPlayer.setNickname("Wunder");
            lolPlayer.setEquipo(EquipoDAO.getEquipoFromName("Movistar Riders", conectionApp.crearConexion()));
            JugadorLolDAO.addLolPlayer(lolPlayer);

            ValorantPlayer valorantPlayer = new ValorantPlayer();
            valorantPlayer.setRol("Duelista");
            valorantPlayer.setAgente("Jett");
            valorantPlayer.setIGL(false);
            valorantPlayer.setInformacionPersonal(new InformacionPersonal("Juan", "Perez", "ES"));
            valorantPlayer.setNickname("Nats");
            valorantPlayer.setEquipo(EquipoDAO.getEquipoFromName("Movistar Riders", conectionApp.crearConexion()));
            JugadorValoDAO.addValoPlayer(valorantPlayer);

            // Eliminar jugadores por ID
            JugadorLolDAO.deleteLolPlayerForID(1L);
            JugadorValoDAO.deleteValoPlayerForID(2L);

            // Actualizar jugadores
            LolPlayer lolPlayerActualizado = new LolPlayer(3L, new InformacionPersonal("Rasmus", "Borregaard Winther", "DEN"), "Caps", EquipoDAO.getEquipoFromName("Movistar Riders", conectionApp.crearConexion()), "Mid", true, false);
            ValorantPlayer valorantPlayerActualizado = new ValorantPlayer(4L, new InformacionPersonal("Oscar", "Cañellas", "ES"), "Mixwell", EquipoDAO.getEquipoFromName("Movistar Riders", conectionApp.crearConexion()), "Centinela", "Chyper", true);

            JugadorLolDAO.updateLolPlayer(lolPlayerActualizado);
            JugadorValoDAO.updateValoPlayer(valorantPlayerActualizado);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    public static void generateDB() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            // Crear liga
            Liga liga = new Liga();
            liga.setNombre("Liga de Ejemplo");
            liga.setFechaCreacion(LocalDate.of(2020, 1, 1));
            liga.setRegion("EU");
            liga.setTier("S");

            // Crear equipo
            Equipo equipo = new Equipo();
            equipo.setNombre("Equipo de Prueba");
            equipo.setFechaCreacion(LocalDate.of(2015, 5, 15));
            equipo.setRegion("EU");
            equipo.setTier("A");

            // Crear y asociar equipo a liga
            EquipoLiga equipoLiga = new EquipoLiga();
            equipoLiga.setEquipo(equipo);
            equipoLiga.setLiga(liga);
            equipoLiga.setFechaInscripcion(LocalDate.of(2022, 10, 10));
            equipoLiga.setPrecioPlaza(5000.00);

            // Persistir objetos
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