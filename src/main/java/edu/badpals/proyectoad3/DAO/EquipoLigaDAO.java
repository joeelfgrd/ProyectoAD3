package edu.badpals.proyectoad3.DAO;

import edu.badpals.proyectoad3.model.EquipoLiga;
import jakarta.persistence.EntityManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class EquipoLigaDAO {

    public static void registerTeam(EquipoLiga equipoLiga) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
        try {
            c.createStatement().executeUpdate("INSERT INTO equipo_liga (id_equipo, id_liga, fecha_inscripcion, precio_plaza) VALUES ('" + equipoLiga.getEquipoId() + "', '" + equipoLiga.getLigaId() + "', '" + equipoLiga.getFechaInscripcion() + "', '" + equipoLiga.getPrecioPlaza() + "')");
        } catch (SQLException e) {
            e.printStackTrace();

        }
        ConnectionDAO.cerrarConexion(c);
    }

    public static void updateParticipation(EquipoLiga equipoLiga) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
        try {
            c.createStatement().executeUpdate("UPDATE equipo_liga SET fecha_inscripcion = '" + equipoLiga.getFechaInscripcion() + "', precio_plaza = '" + equipoLiga.getPrecioPlaza() + "', id_equipo = '" + equipoLiga.getEquipoId() + "' , id_liga = '" + equipoLiga.getLigaId() + "' where id = " + equipoLiga.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionDAO.cerrarConexion(c);
    }

    public static void unregisterTeam(EquipoLiga equipoLiga) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
        try {
            c.createStatement().executeUpdate("DELETE FROM equipo_liga WHERE id_equipo = " + equipoLiga.getEquipoId() + " AND id_liga = " + equipoLiga.getLigaId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionDAO.cerrarConexion(c);
    }

    public static List<EquipoLiga> getEquipoLiga(Connection connection, EntityManager em) {
        List<EquipoLiga> equipoLigas = em.createQuery("SELECT e FROM EquipoLiga e", EquipoLiga.class).getResultList();
        return equipoLigas;
    }

    public static List<EquipoLiga> getEquipoLigaPorEquipo(Long equipoId) {
        List<EquipoLiga> equipoLigas = new ArrayList<>();
        ConnectionDAO.llamadaEntityManager llamadaEM = ConnectionDAO.getLlamadaEntityManager();
        EntityManager em = llamadaEM.em();

        // Consulta JPQL para obtener las asociaciones de equipos en ligas
        String jpql = "SELECT el FROM EquipoLiga el WHERE el.equipo.idEquipo = :equipoId";

        try {
            // Inicia una transacción
            em.getTransaction().begin();

            // Ejecuta la consulta con el parámetro del equipoId
            equipoLigas = em.createQuery(jpql, EquipoLiga.class)
                    .setParameter("equipoId", equipoId)
                    .getResultList();

            // Confirma la transacción
            em.getTransaction().commit();
        } catch (Exception e) {
            // Si ocurre un error, se revierte la transacción
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            // Cierra los recursos
            em.close();
            llamadaEM.emf().close();
        }

        // Devuelve la lista de asociaciones de equipo y liga encontradas
        return equipoLigas;
    }

    public static List<EquipoLiga> getEquipoLigaPorLiga(Long ligaId) {
        List<EquipoLiga> equipoLigas = new ArrayList<>();
        ConnectionDAO.llamadaEntityManager llamadaEM = ConnectionDAO.getLlamadaEntityManager();
        EntityManager em = llamadaEM.em();

        // Consulta JPQL para obtener las asociaciones de equipos en una liga
        String jpql = "SELECT el FROM EquipoLiga el WHERE el.liga.idLiga = :ligaId";

        try {
            // Inicia una transacción
            em.getTransaction().begin();

            // Ejecuta la consulta con el parámetro del ligaId
            equipoLigas = em.createQuery(jpql, EquipoLiga.class)
                    .setParameter("ligaId", ligaId)
                    .getResultList();

            // Confirma la transacción
            em.getTransaction().commit();
        } catch (Exception e) {
            // Si ocurre un error, se revierte la transacción
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            // Cierra los recursos
            em.close();
            llamadaEM.emf().close();
        }

        // Devuelve la lista de asociaciones de equipo y liga encontradas
        return equipoLigas;
    }

}