package edu.badpals.proyectoad3.DAO;

import java.sql.*;
import java.util.Properties;

public class ConnectionDAO {

    private final String URLDB = "jdbc:mysql://esportsdb.cngww6m8qbrx.eu-west-2.rds.amazonaws.com:3308/EsportsDB";



    public Connection crearConexion() {
        try {
            Properties propiedadesConexion = new Properties();
            propiedadesConexion.setProperty("user", "root");
            propiedadesConexion.setProperty("password", "*on1OrIaQi9R");
            Connection c = DriverManager.getConnection(URLDB, propiedadesConexion);
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void cerrarConexion(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}