package edu.badpals.proyectoad3.model;

import edu.badpals.proyectoad3.model.entities.Equipo;
import edu.badpals.proyectoad3.model.entities.EquipoLiga;
import edu.badpals.proyectoad3.model.entities.Liga;
import edu.badpals.proyectoad3.model.entities.LolPlayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conection_App {

    private final String URLDB = "jdbc:mysql://127.0.0.1:3307/ESPORTS";

    public Connection crearConexion() {
        try {
            Properties propiedadesConexion = new Properties();
            propiedadesConexion.setProperty("user", "root");
            propiedadesConexion.setProperty("password", "root");
            Connection c = DriverManager.getConnection(URLDB, propiedadesConexion);
            System.out.println("Conexion establecida");
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void cerrarConexion(Connection c) {
        try {
            c.close();
            System.out.println("Conexión cerrada");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addLeague(Liga liga) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.createStatement().executeUpdate("INSERT INTO ligas (nombre, fecha_creacion, region, tier) VALUES ('" + liga.getNombre() + "', '" + liga.getFechaCreacion() + "', '" + liga.getRegion() + "', '" + liga.getTier() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cerrarConexion(c);
    }

    public static void addTeam(Equipo equipo) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.createStatement().executeUpdate("INSERT INTO equipos (nombre, fecha_creacion, region, tier) VALUES ('" + equipo.getNombre() + "', '" + equipo.getFechaCreacion() + "', '" + equipo.getRegion() + "', '" + equipo.getTier() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cerrarConexion(c);
    }

    public static void registerTeam(EquipoLiga equipoLiga) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.createStatement().executeUpdate("INSERT INTO equipo_liga (id_equipo, id_liga, fecha_inscripcion, precio_plaza) VALUES ('" + equipoLiga.getEquipoId() + "', '" + equipoLiga.getLigaId() + "', '" + equipoLiga.getFechaInscripcion() + "', '" + equipoLiga.getPrecioPlaza() + "')");
        } catch (SQLException e) {
            e.printStackTrace();

        }
        cerrarConexion(c);
    }

    public static void unregisterTeam(EquipoLiga equipoLiga) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.createStatement().executeUpdate("DELETE FROM equipo_liga WHERE id_equipo = " + equipoLiga.getEquipoId() + " AND id_liga = " + equipoLiga.getLigaId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cerrarConexion(c);
    }

    public static void deleteTeam(Equipo equipo) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.createStatement().executeUpdate("DELETE FROM equipos WHERE  idEquipo= " + equipo.getIdEquipo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cerrarConexion(c);
    }

    public static void deleteLeague(Liga liga) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.createStatement().executeUpdate("DELETE FROM ligas WHERE idLiga = " + liga.getIdLiga());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cerrarConexion(c);
    }

    public static void delteLeagueForID(Long id) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.createStatement().executeUpdate("DELETE FROM ligas WHERE idLiga = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cerrarConexion(c);
    }

    public static void deleteTeamForID(Long id) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.createStatement().executeUpdate("DELETE FROM equipos WHERE idEquipo = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cerrarConexion(c);
    }


    public static void updateTeam(Equipo equipo) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.createStatement().executeUpdate("UPDATE equipos SET nombre = '" + equipo.getNombre() + "', fecha_creacion = '" + equipo.getFechaCreacion() + "', region = '" + equipo.getRegion() + "', tier = '" + equipo.getTier() + "' WHERE idEquipo = " + equipo.getIdEquipo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cerrarConexion(c);
    }

    public static void updateLeague(Liga liga) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.createStatement().executeUpdate("UPDATE ligas SET nombre = '" + liga.getNombre() + "', fecha_creacion = '" + liga.getFechaCreacion() + "', region = '" + liga.getRegion() + "', tier = '" + liga.getTier() + "' WHERE idLiga = " + liga.getIdLiga());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cerrarConexion(c);
    }

    public static void updateParticipation(EquipoLiga equipoLiga) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.createStatement().executeUpdate("UPDATE equipo_liga SET fecha_inscripcion = '" + equipoLiga.getFechaInscripcion() + "', precio_plaza = '" + equipoLiga.getPrecioPlaza() + "' WHERE id_equipo = " + equipoLiga.getEquipoId() + " AND id_liga = " + equipoLiga.getLigaId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cerrarConexion(c);
    }

    // Faltan los methods de los jugadores, el problema es el objeto embebido que tiene

}
