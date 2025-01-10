package edu.badpals.proyectoad3.DAO;

import edu.badpals.proyectoad3.model.entities.Liga;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LigasDAO {


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

}