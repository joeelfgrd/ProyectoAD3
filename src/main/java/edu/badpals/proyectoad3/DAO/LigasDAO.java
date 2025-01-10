package edu.badpals.proyectoad3.DAO;

import edu.badpals.proyectoad3.model.Liga;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LigasDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    private EntityManager em;

    public static void addLeague(Liga liga) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
        try {
            c.createStatement().executeUpdate("INSERT INTO ligas (nombre, fecha_creacion, region, tier) VALUES ('" + liga.getNombre() + "', '" + liga.getFechaCreacion() + "', '" + liga.getRegion() + "', '" + liga.getTier() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionDAO.cerrarConexion(c);
    }

    public static void updateLeague(Liga liga) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
        try {
            c.createStatement().executeUpdate("UPDATE ligas SET nombre = '" + liga.getNombre() + "', fecha_creacion = '" + liga.getFechaCreacion() + "', region = '" + liga.getRegion() + "', tier = '" + liga.getTier() + "' WHERE idLiga = " + liga.getIdLiga());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionDAO.cerrarConexion(c);
    }

    public static void deleteLeague(Liga liga) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
        try {
            c.createStatement().executeUpdate("DELETE FROM ligas WHERE idLiga = " + liga.getIdLiga());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionDAO.cerrarConexion(c);
    }

    public static List<Liga> getLigas(Connection c) {
        List<Liga> ligas = new ArrayList<>();
        String query = "SELECT * FROM ligas";

        try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery(query)) {
            while (rs.next()) {
                Liga liga = new Liga();
                liga.setIdLiga(rs.getLong("idLiga"));
                liga.setNombre(rs.getString("nombre"));
                liga.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                liga.setRegion(rs.getString("region"));
                liga.setTier(rs.getString("tier"));
                ligas.add(liga);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ligas;
    }

    public static List<Liga> getLigasPorNombre(String nombre) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<Liga> ligasPorNombre = new ArrayList<>();
        String jpql = "SELECT l FROM Liga l WHERE l.nombre LIKE :nombre";

        try {
            em.getTransaction().begin();
            ligasPorNombre = em.createQuery(jpql, Liga.class).setParameter("nombre", "%" + nombre + "%")
                    .getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
        return ligasPorNombre;
    }


    public static List<Liga> getLigasPorRegion(String region) {
        List<Liga> ligas = new ArrayList<>();
        ConnectionDAO.llamadaEntityManager llamadaEM = ConnectionDAO.getLlamadaEntityManager();
        EntityManager em = llamadaEM.em();

        // Consulta JPQL para obtener ligas por región
        String jpql = "SELECT l FROM Liga l WHERE l.region = :region";

        try {
            // Inicia una transacción
            em.getTransaction().begin();

            // Ejecuta la consulta con el parámetro de la región
            ligas = em.createQuery(jpql, Liga.class)
                    .setParameter("region", region)
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

        // Devuelve la lista de ligas encontradas
        return ligas;
    }

    public static List<Liga> getLigasPorTier(String tier) {
        List<Liga> ligas = new ArrayList<>();
        ConnectionDAO.llamadaEntityManager llamadaEM = ConnectionDAO.getLlamadaEntityManager();
        EntityManager em = llamadaEM.em();

        // Consulta JPQL para obtener ligas por tier
        String jpql = "SELECT l FROM Liga l WHERE l.tier = :tier";

        try {
            // Inicia una transacción
            em.getTransaction().begin();

            // Ejecuta la consulta con el parámetro del tier
            ligas = em.createQuery(jpql, Liga.class)
                    .setParameter("tier", tier)
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

        // Devuelve la lista de ligas encontradas
        return ligas;
    }

}