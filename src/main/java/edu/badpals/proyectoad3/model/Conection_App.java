package edu.badpals.proyectoad3.model;

import edu.badpals.proyectoad3.model.entities.*;

import java.sql.*;
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


    public static void addLolPlayer(LolPlayer lolPlayer) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            // Paso 1: Insertar en 'personal'
            String insertPersonal = "INSERT INTO personal (nombre, apellidos, pais, nickname, equipo, tipo_jugador) VALUES (?, ?, ?, ?, ?, ?)";
            long idJugadorGenerado;

            try (PreparedStatement psPersonal = c.prepareStatement(insertPersonal, Statement.RETURN_GENERATED_KEYS)) {
                psPersonal.setString(1, lolPlayer.getInformacionPersonal().getNombre());
                psPersonal.setString(2, lolPlayer.getInformacionPersonal().getApellidos());
                psPersonal.setString(3, lolPlayer.getInformacionPersonal().getPais());
                psPersonal.setString(4, lolPlayer.getNickname());
                psPersonal.setString(5, lolPlayer.getEquipo());
                psPersonal.setInt(6, 1); // Valor para tipo_jugador, por ejemplo: 1 para LolPlayer
                psPersonal.executeUpdate();

                // Recuperar la clave generada
                try (ResultSet generatedKeys = psPersonal.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idJugadorGenerado = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Error al obtener la clave generada para personal.");
                    }
                }
            }

            // Paso 2: Insertar en 'LolPlayers' usando la clave generada
            String insertLolPlayer = "INSERT INTO LolPlayers (id_jugador, posicion, earlyShotcaller, lateShotcaller) VALUES (?, ?, ?, ?)";
            try (PreparedStatement psLolPlayer = c.prepareStatement(insertLolPlayer)) {
                psLolPlayer.setLong(1, idJugadorGenerado);
                psLolPlayer.setString(2, lolPlayer.getPosicion());
                psLolPlayer.setBoolean(3, lolPlayer.isEarlyShotcaller());
                psLolPlayer.setBoolean(4, lolPlayer.isLateShotcaller());
                psLolPlayer.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addValoPlayer(ValorantPlayer valoPlayer) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            // Paso 1: Insertar en 'personal'
            String insertPersonal = "INSERT INTO personal (nombre, apellidos, pais, nickname, equipo, tipo_jugador) VALUES (?, ?, ?, ?, ?, ?)";
            long idJugadorGenerado;

            try (PreparedStatement psPersonal = c.prepareStatement(insertPersonal, Statement.RETURN_GENERATED_KEYS)) {
                psPersonal.setString(1, valoPlayer.getInformacionPersonal().getNombre());
                psPersonal.setString(2, valoPlayer.getInformacionPersonal().getApellidos());
                psPersonal.setString(3, valoPlayer.getInformacionPersonal().getPais());
                psPersonal.setString(4, valoPlayer.getNickname());
                psPersonal.setString(5, valoPlayer.getEquipo());
                psPersonal.setInt(6, 2); // Valor para tipo_jugador, por ejemplo: 2 para ValoPlayer
                psPersonal.executeUpdate();

                // Recuperar la clave generada
                try (ResultSet generatedKeys = psPersonal.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idJugadorGenerado = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Error al obtener la clave generada para personal.");
                    }
                }
            }

            // Paso 2: Insertar en 'ValoPlayers' usando la clave generada
            String insertValoPlayer = "INSERT INTO ValorantPlayers (id_jugador, rol, agente, IGL) VALUES (?, ?, ?, ?)";
            try (PreparedStatement psValoPlayer = c.prepareStatement(insertValoPlayer)) {
                psValoPlayer.setLong(1, idJugadorGenerado);
                psValoPlayer.setString(2, valoPlayer.getRol());
                psValoPlayer.setString(3, valoPlayer.getAgente());
                psValoPlayer.setBoolean(4, valoPlayer.isIGL());
                psValoPlayer.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteLolPlayerForID(Long id) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.setAutoCommit(false); // Iniciar transacción

            // Paso 1: Eliminar registros relacionados en LolPlayers
            String deleteLolPlayers = "DELETE FROM LolPlayers WHERE id_jugador = ?";
            try (PreparedStatement psLolPlayers = c.prepareStatement(deleteLolPlayers)) {
                psLolPlayers.setLong(1, id);
                psLolPlayers.executeUpdate();
            }

            // Paso 2: Eliminar el registro en personal
            String deletePersonal = "DELETE FROM personal WHERE id_jugador = ?";
            try (PreparedStatement psPersonal = c.prepareStatement(deletePersonal)) {
                psPersonal.setLong(1, id);
                psPersonal.executeUpdate();
            }

            c.commit(); // Confirmar transacción
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                c.rollback(); // Revertir en caso de error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                c.setAutoCommit(true); // Restaurar estado por defecto
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            cerrarConexion(c); // Cerrar conexión
        }
    }

    public static void deleteValoPlayerForID(Long id) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.setAutoCommit(false); // Iniciar transacción

            // Paso 1: Eliminar registros relacionados en ValorantPlayers
            String deleteValoPlayers = "DELETE FROM ValorantPlayers WHERE id_jugador = ?";
            try (PreparedStatement psValoPlayers = c.prepareStatement(deleteValoPlayers)) {
                psValoPlayers.setLong(1, id);
                psValoPlayers.executeUpdate();
            }

            // Paso 2: Eliminar el registro en personal
            String deletePersonal = "DELETE FROM personal WHERE id_jugador = ?";
            try (PreparedStatement psPersonal = c.prepareStatement(deletePersonal)) {
                psPersonal.setLong(1, id);
                psPersonal.executeUpdate();
            }

            c.commit(); // Confirmar transacción
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                c.rollback(); // Revertir en caso de error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                c.setAutoCommit(true); // Restaurar estado por defecto
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            cerrarConexion(c); // Cerrar conexión
        }
    }

    public static void updateLolPlayer(LolPlayer lolPlayer) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.setAutoCommit(false); // Iniciar transacción

            // Paso 1: Actualizar registros relacionados en LolPlayers
            String updateLolPlayers = "UPDATE LolPlayers SET posicion = ?, earlyShotcaller = ?, lateShotcaller = ? WHERE id_jugador = ?";
            try (PreparedStatement psLolPlayers = c.prepareStatement(updateLolPlayers)) {
                psLolPlayers.setString(1, lolPlayer.getPosicion());
                psLolPlayers.setBoolean(2, lolPlayer.isEarlyShotcaller());
                psLolPlayers.setBoolean(3, lolPlayer.isLateShotcaller());
                psLolPlayers.setLong(4, lolPlayer.getId_jugador());
                psLolPlayers.executeUpdate();
            }

            // Paso 2: Actualizar el registro en personal
            String updatePersonal = "UPDATE personal SET nombre = ?, apellidos = ?, pais = ?, nickname = ?, equipo = ? WHERE id_jugador = ?";
            try (PreparedStatement psPersonal = c.prepareStatement(updatePersonal)) {
                psPersonal.setString(1, lolPlayer.getInformacionPersonal().getNombre());
                psPersonal.setString(2, lolPlayer.getInformacionPersonal().getApellidos());
                psPersonal.setString(3, lolPlayer.getInformacionPersonal().getPais());
                psPersonal.setString(4, lolPlayer.getNickname());
                psPersonal.setString(5, lolPlayer.getEquipo());
                psPersonal.setLong(6, lolPlayer.getId_jugador());
                psPersonal.executeUpdate();
            }

            c.commit(); // Confirmar transacción
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                c.rollback(); // Revertir en caso de error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                c.setAutoCommit(true); // Restaurar estado por defecto
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            cerrarConexion(c); // Cerrar conexión
        }
    }

    public static void updateValoPlayer(ValorantPlayer valoPlayer) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.setAutoCommit(false); // Iniciar transacción

            // Paso 1: Actualizar registros relacionados en ValorantPlayers
            String updateValoPlayers = "UPDATE ValorantPlayers SET rol = ?, agente = ?, IGL = ? WHERE id_jugador = ?";
            try (PreparedStatement psValoPlayers = c.prepareStatement(updateValoPlayers)) {
                psValoPlayers.setString(1, valoPlayer.getRol());
                psValoPlayers.setString(2, valoPlayer.getAgente());
                psValoPlayers.setBoolean(3, valoPlayer.isIGL());
                psValoPlayers.setLong(4, valoPlayer.getId_jugador());
                psValoPlayers.executeUpdate();
            }

            // Paso 2: Actualizar el registro en personal
            String updatePersonal = "UPDATE personal SET nombre = ?, apellidos = ?, pais = ?, nickname = ?, equipo = ? WHERE id_jugador = ?";
            try (PreparedStatement psPersonal = c.prepareStatement(updatePersonal)) {
                psPersonal.setString(1, valoPlayer.getInformacionPersonal().getNombre());
                psPersonal.setString(2, valoPlayer.getInformacionPersonal().getApellidos());
                psPersonal.setString(3, valoPlayer.getInformacionPersonal().getPais());
                psPersonal.setString(4, valoPlayer.getNickname());
                psPersonal.setString(5, valoPlayer.getEquipo());
                psPersonal.setLong(6, valoPlayer.getId_jugador());
                psPersonal.executeUpdate();
            }

            c.commit(); // Confirmar transacción
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                c.rollback(); // Revertir en caso de error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                c.setAutoCommit(true); // Restaurar estado por defecto
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            cerrarConexion(c); // Cerrar conexión
        }
    }





}
