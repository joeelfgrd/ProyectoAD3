package edu.badpals.proyectoad3.DAO;

import edu.badpals.proyectoad3.model.EquipoLiga;
import jakarta.persistence.EntityManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
}