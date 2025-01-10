package edu.badpals.proyectoad3.DAO;

import edu.badpals.proyectoad3.model.entities.Equipo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static Equipo getEquipoFromName(String name, Connection c) {
        Equipo equipo = new Equipo();
        String query = "SELECT * FROM equipos WHERE nombre = '" + name + "'";

        try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery(query)) {
            while (rs.next()) {
                equipo.setIdEquipo(rs.getLong("idEquipo"));
                equipo.setNombre(rs.getString("nombre"));
                equipo.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                equipo.setRegion(rs.getString("region"));
                equipo.setTier(rs.getString("tier"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipo;
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
}