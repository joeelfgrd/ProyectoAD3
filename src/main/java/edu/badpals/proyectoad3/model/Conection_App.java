package edu.badpals.proyectoad3.model;

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


}
