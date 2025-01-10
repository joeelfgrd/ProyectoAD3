package edu.badpals.proyectoad3.DAO;

import edu.badpals.proyectoad3.model.Equipo;
import jakarta.persistence.EntityManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static edu.badpals.proyectoad3.DAO.ConnectionDAO.getLlamadaEntityManager;

public class EquipoDAO {

    public static void addTeam(Equipo equipo) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
        try {
            c.createStatement().executeUpdate("INSERT INTO equipos (nombre, fecha_creacion, region, tier) VALUES ('" + equipo.getNombre() + "', '" + equipo.getFechaCreacion() + "', '" + equipo.getRegion() + "', '" + equipo.getTier() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionDAO.cerrarConexion(c);
    }

    public static void updateTeam(Equipo equipo) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
        try {
            c.createStatement().executeUpdate("UPDATE equipos SET nombre = '" + equipo.getNombre() + "', fecha_creacion = '" + equipo.getFechaCreacion() + "', region = '" + equipo.getRegion() + "', tier = '" + equipo.getTier() + "' WHERE idEquipo = " + equipo.getIdEquipo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionDAO.cerrarConexion(c);
    }

    public static void deleteTeam(Equipo equipo) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
        try {
            c.createStatement().executeUpdate("DELETE FROM equipos WHERE idEquipo = " + equipo.getIdEquipo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionDAO.cerrarConexion(c);
    }

    public static List<Equipo> getEquipos(Connection c) {
        List<Equipo> equipos = new ArrayList<>();
        String query = "SELECT * FROM equipos";

        try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery(query)) {
            while (rs.next()) {
                Equipo equipo = new Equipo();
                equipo.setIdEquipo(rs.getLong("idEquipo"));
                equipo.setNombre(rs.getString("nombre"));
                equipo.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                equipo.setRegion(rs.getString("region"));
                equipo.setTier(rs.getString("tier"));
                equipos.add(equipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return equipos;
    }

    public static List<String> returnAllTeams(Connection c) {
        List<String> teams = new ArrayList<>();
        String query = "SELECT nombre FROM equipos";

        try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery(query)) {
            while (rs.next()) {
                teams.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    public static Equipo getEquipoById(Connection c, Long idEquipo) {
        Equipo equipo = null;
        String query = "SELECT * FROM equipos WHERE idEquipo = ?";  // Buscar por idEquipo

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setLong(1, idEquipo);  // Usar el id del equipo
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    equipo = new Equipo();
                    equipo.setIdEquipo(rs.getLong("idEquipo"));
                    equipo.setNombre(rs.getString("nombre"));
                    equipo.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                    equipo.setRegion(rs.getString("region"));
                    equipo.setTier(rs.getString("tier"));
                    // Asigna otros campos de Equipo si es necesario
                } else {
                    System.err.println("Equipo no encontrado en la base de datos con id: " + idEquipo);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipo;
    }

    public static List<Equipo> getEquiposPorRegion(String region) {
        List<Equipo> equipos = new ArrayList<>();
        ConnectionDAO.llamadaEntityManager llamadaEM = ConnectionDAO.getLlamadaEntityManager();
        EntityManager em = llamadaEM.em();

        String jpql = "SELECT e FROM Equipo e WHERE e.region = :region";

        try {
            em.getTransaction().begin();
            equipos = em.createQuery(jpql, Equipo.class)
                    .setParameter("region", region)
                    .getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            llamadaEM.emf().close();
        }

        return equipos;
    }

    public static List<Equipo> getEquiposPorTier(String tier) {
        List<Equipo> equipos = new ArrayList<>();
        ConnectionDAO.llamadaEntityManager llamadaEM = ConnectionDAO.getLlamadaEntityManager();
        EntityManager em = llamadaEM.em();

        String jpql = "SELECT e FROM Equipo e WHERE e.tier = :tier";

        try {
            em.getTransaction().begin();
            equipos = em.createQuery(jpql, Equipo.class)
                    .setParameter("tier", tier)
                    .getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            llamadaEM.emf().close();
        }

        return equipos;
    }


    public static List<Equipo> getEquiposPorNombre(String nombre) {
        ConnectionDAO.llamadaEntityManager result = getLlamadaEntityManager();
        List<Equipo> EquiposPorNombre = new ArrayList<>();
        String jpql = "SELECT e FROM Equipo e WHERE e.nombre LIKE :nombre";

        try {
            result.em().getTransaction().begin();
            EquiposPorNombre = result.em().createQuery(jpql, Equipo.class).setParameter("nombre", "%" + nombre + "%").getResultList();
            result.em().getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            result.em().getTransaction().rollback();
        } finally {
            result.em().close();
            result.emf().close();
        }
        return EquiposPorNombre;
    }


    public static boolean equipoTieneJugadores(Equipo equipoSeleccionado) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
        boolean hasPlayers = false;
        String query = "SELECT * FROM personal WHERE equipo = ?";
        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setLong(1, equipoSeleccionado.getIdEquipo());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hasPlayers = true;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionDAO.cerrarConexion(c);
        }
        return hasPlayers;
    }
}