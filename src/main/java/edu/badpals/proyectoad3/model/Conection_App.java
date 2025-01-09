package edu.badpals.proyectoad3.model;

import edu.badpals.proyectoad3.model.entities.*;
import jakarta.persistence.EntityManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    public static void deleteTeam(Equipo equipo) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.createStatement().executeUpdate("DELETE FROM equipos WHERE idEquipo = " + equipo.getIdEquipo());
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

                // Aquí debes usar el ID del equipo, no el nombre
                psPersonal.setLong(5, lolPlayer.getEquipo().getIdEquipo()); // Ahora insertamos el ID del equipo

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
                psLolPlayer.setBoolean(3, lolPlayer.isEarlyShotCaller());
                psLolPlayer.setBoolean(4, lolPlayer.isLateShotCaller());
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

            // Obtén el ID del equipo en lugar del nombre
            long idEquipo = valoPlayer.getEquipo().getIdEquipo();  // Asegúrate de que el equipo tenga el ID asignado

            try (PreparedStatement psPersonal = c.prepareStatement(insertPersonal, Statement.RETURN_GENERATED_KEYS)) {
                psPersonal.setString(1, valoPlayer.getInformacionPersonal().getNombre());
                psPersonal.setString(2, valoPlayer.getInformacionPersonal().getApellidos());
                psPersonal.setString(3, valoPlayer.getInformacionPersonal().getPais());
                psPersonal.setString(4, valoPlayer.getNickname());
                psPersonal.setLong(5, idEquipo);  // Inserta el ID del equipo
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

            // Paso 2: Insertar en 'ValorantPlayers' usando la clave generada
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
            c.setAutoCommit(false);
            String deleteValoPlayers = "DELETE FROM ValorantPlayers WHERE id_jugador = ?";
            try (PreparedStatement psValoPlayers = c.prepareStatement(deleteValoPlayers)) {
                psValoPlayers.setLong(1, id);
                psValoPlayers.executeUpdate();
            }
            String deletePersonal = "DELETE FROM personal WHERE id_jugador = ?";
            try (PreparedStatement psPersonal = c.prepareStatement(deletePersonal)) {
                psPersonal.setLong(1, id);
                psPersonal.executeUpdate();
            }

            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                c.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                c.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            cerrarConexion(c);
        }
    }

    public static void updateLolPlayer(LolPlayer lolPlayer) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.setAutoCommit(false);
            String updateLolPlayers = "UPDATE LolPlayers SET posicion = ?, earlyShotcaller = ?, lateShotcaller = ? WHERE id_jugador = ?";
            try (PreparedStatement psLolPlayers = c.prepareStatement(updateLolPlayers)) {
                psLolPlayers.setString(1, lolPlayer.getPosicion());
                psLolPlayers.setBoolean(2, lolPlayer.isEarlyShotCaller());
                psLolPlayers.setBoolean(3, lolPlayer.isLateShotCaller());
                psLolPlayers.setLong(4, lolPlayer.getId_jugador());
                psLolPlayers.executeUpdate();
            }
            String updatePersonal = "UPDATE personal SET nombre = ?, apellidos = ?, pais = ?, nickname = ?, equipo = ? WHERE id_jugador = ?";
            try (PreparedStatement psPersonal = c.prepareStatement(updatePersonal)) {
                psPersonal.setString(1, lolPlayer.getInformacionPersonal().getNombre());
                psPersonal.setString(2, lolPlayer.getInformacionPersonal().getApellidos());
                psPersonal.setString(3, lolPlayer.getInformacionPersonal().getPais());
                psPersonal.setString(4, lolPlayer.getNickname());

                long idEquipo = lolPlayer.getEquipo().getIdEquipo();
                psPersonal.setLong(5, idEquipo);

                psPersonal.setLong(6, lolPlayer.getId_jugador());
                psPersonal.executeUpdate();
            }

            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                c.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                c.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            cerrarConexion(c);
        }
    }


    public static void updateValoPlayer(ValorantPlayer valoPlayer) {
        Conection_App conection_app = new Conection_App();
        Connection c = conection_app.crearConexion();
        try {
            c.setAutoCommit(false);

            String updateValoPlayers = "UPDATE ValorantPlayers SET rol = ?, agente = ?, IGL = ? WHERE id_jugador = ?";
            try (PreparedStatement psValoPlayers = c.prepareStatement(updateValoPlayers)) {
                psValoPlayers.setString(1, valoPlayer.getRol());
                psValoPlayers.setString(2, valoPlayer.getAgente());
                psValoPlayers.setBoolean(3, valoPlayer.isIGL());
                psValoPlayers.setLong(4, valoPlayer.getId_jugador());
                psValoPlayers.executeUpdate();
            }
            String updatePersonal = "UPDATE personal SET nombre = ?, apellidos = ?, pais = ?, nickname = ?, equipo = ? WHERE id_jugador = ?";
            try (PreparedStatement psPersonal = c.prepareStatement(updatePersonal)) {
                psPersonal.setString(1, valoPlayer.getInformacionPersonal().getNombre());
                psPersonal.setString(2, valoPlayer.getInformacionPersonal().getApellidos());
                psPersonal.setString(3, valoPlayer.getInformacionPersonal().getPais());
                psPersonal.setString(4, valoPlayer.getNickname());

                long idEquipo = valoPlayer.getEquipo().getIdEquipo();
                psPersonal.setLong(5, idEquipo);

                psPersonal.setLong(6, valoPlayer.getId_jugador());
                psPersonal.executeUpdate();
            }

            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                c.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                c.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            cerrarConexion(c);
        }
    }


    //LISTAR LOS OBJETOS

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

    public static List<EquipoLiga> getEquipoLiga(Connection connection, EntityManager em) {
        List<EquipoLiga> equipoLigas = new ArrayList<>();
        String query = "SELECT * FROM equipo_liga";

        try (Statement s = connection.createStatement(); ResultSet rs = s.executeQuery(query)) {
            while (rs.next()) {
                EquipoLiga equipoLiga = new EquipoLiga();
                long idEquipo = rs.getLong("id_equipo");
                long idLiga = rs.getLong("id_liga");
                Equipo equipo = em.find(Equipo.class, idEquipo);
                Liga liga = em.find(Liga.class, idLiga);

                if (equipo == null) {
                    throw new IllegalStateException("Equipo con ID " + idEquipo + " no encontrado.");
                }
                if (liga == null) {
                    throw new IllegalStateException("Liga con ID " + idLiga + " no encontrada.");
                }
                equipoLiga.setEquipo(equipo);
                equipoLiga.setLiga(liga);
                equipoLiga.setFechaInscripcion(rs.getDate("fecha_inscripcion").toLocalDate());
                equipoLiga.setPrecioPlaza(rs.getDouble("precio_plaza"));

                equipoLigas.add(equipoLiga);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipoLigas;
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

    public static List<String> returnAllLeagues(Connection c) {
        List<String> leagues = new ArrayList<>();
        String query = "SELECT nombre FROM ligas";

        try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery(query)) {
            while (rs.next()) {
                leagues.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leagues;
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

    public static List<ValorantPlayer> getJugadorValorant(Connection c) {
        List<ValorantPlayer> valorantPlayer = new ArrayList<>();
        String query = "SELECT * FROM ValorantPlayers " +
                "INNER JOIN personal ON ValorantPlayers.id_jugador = personal.id_jugador";

        try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery(query)) {

            while (rs.next()) {
                ValorantPlayer vp = new ValorantPlayer();
                vp.setId_jugador(rs.getLong("id_jugador"));
                vp.setRol(rs.getString("rol"));
                vp.setAgente(rs.getString("agente"));
                vp.setIGL(rs.getBoolean("IGL"));
                vp.setInformacionPersonal(new InformacionPersonal(rs.getString("nombre"), rs.getString("apellidos"), rs.getString("pais")));
                vp.setNickname(rs.getString("nickname"));
                Long equipoId = rs.getLong("equipo");
                Equipo equipo = getEquipoById(c, equipoId);
                if (equipo != null) {
                    vp.setEquipo(equipo);
                } else {
                    System.err.println("Equipo no encontrado con id: " + equipoId);
                }
                valorantPlayer.add(vp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valorantPlayer;
    }

    public static List<LolPlayer> getLolPlayers(Connection c) {
        List<LolPlayer> lolPlayers = new ArrayList<>();
        String query = "SELECT * FROM LolPlayers " +
                "INNER JOIN personal ON LolPlayers.id_jugador = personal.id_jugador";

        try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery(query)) {
            while (rs.next()) {
                LolPlayer lp = new LolPlayer();
                lp.setId_jugador(rs.getLong("id_jugador"));
                lp.setPosicion(rs.getString("Posicion"));
                lp.setEarlyShotcaller(rs.getBoolean("EarlyShotcaller"));
                lp.setLateShotcaller(rs.getBoolean("LateShotcaller"));
                lp.setInformacionPersonal(new InformacionPersonal(
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("pais")
                ));
                lp.setNickname(rs.getString("nickname"));
                Long equipoId = rs.getLong("equipo");
                Equipo equipo = getEquipoById(c, equipoId);
                if (equipo != null) {
                    lp.setEquipo(equipo);
                } else {
                    System.err.println("Equipo no encontrado con id: " + equipoId);
                }

                lolPlayers.add(lp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener LolPlayers", e);
        }
        return lolPlayers;
    }

    private static Equipo getEquipoById(Connection c, Long idEquipo) {
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

