package edu.badpals.proyectoad3.DAO;

import edu.badpals.proyectoad3.model.Equipo;
import edu.badpals.proyectoad3.model.InformacionPersonal;
import edu.badpals.proyectoad3.model.LolPlayer;
import jakarta.persistence.EntityManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorLolDAO {

    public static void addLolPlayer(LolPlayer lolPlayer) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
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

    public static void deleteLolPlayerForID(Long id) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
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
            ConnectionDAO.cerrarConexion(c); // Cerrar conexión
        }
    }

    public static void updateLolPlayer(LolPlayer lolPlayer) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
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
            ConnectionDAO.cerrarConexion(c);
        }
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
                Equipo equipo = EquipoDAO.getEquipoById(c, equipoId);
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

    public static List<LolPlayer> getJugadorLolPorEquipo(String nombreEquipo) {
        List<LolPlayer> jugadores = new ArrayList<>();
        ConnectionDAO.llamadaEntityManager llamadaEM = ConnectionDAO.getLlamadaEntityManager();
        EntityManager em = llamadaEM.em();

        // JPQL adaptada para buscar por nombre del equipo
        String jpql = "SELECT lp FROM LolPlayer lp " +
                "JOIN lp.Equipo e " +
                "WHERE e.nombre = :nombreEquipo"; // Ahora se usa el nombre del equipo

        try {
            em.getTransaction().begin();

            // Ejecutar la consulta con el parámetro del nombre del equipo
            jugadores = em.createQuery(jpql, LolPlayer.class)
                    .setParameter("nombreEquipo", nombreEquipo)  // Usar el nombre del equipo
                    .getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            llamadaEM.emf().close();
        }

        return jugadores;
    }

    public static List<LolPlayer> getJugadorLolPorPais(String pais) {
        List<LolPlayer> jugadores = new ArrayList<>();
        ConnectionDAO.llamadaEntityManager llamadaEM = ConnectionDAO.getLlamadaEntityManager();
        EntityManager em = llamadaEM.em();

        // JPQL adaptada para buscar por país
        String jpql = "SELECT lp FROM LolPlayer lp " +
                "WHERE lp.informacionPersonal.pais = :pais"; // Ahora se usa el país

        try {
            em.getTransaction().begin();

            // Ejecutar la consulta con el parámetro del país
            jugadores = em.createQuery(jpql, LolPlayer.class)
                    .setParameter("pais", pais)  // Usar el país como parámetro
                    .getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            llamadaEM.emf().close();
        }

        return jugadores;
    }

    public static List<LolPlayer> getJugadoresLolPorPosicion(String posicionSeleccionada) {
        List<LolPlayer> jugadores = new ArrayList<>();
        ConnectionDAO.llamadaEntityManager llamadaEM = ConnectionDAO.getLlamadaEntityManager();
        EntityManager em = llamadaEM.em();

        // JPQL adaptada para buscar por posición
        String jpql = "SELECT lp FROM LolPlayer lp " +
                "WHERE lp.posicion = :posicion"; // Se usa la posición como parámetro

        try {
            em.getTransaction().begin();

            // Ejecutar la consulta con el parámetro de la posición
            jugadores = em.createQuery(jpql, LolPlayer.class)
                    .setParameter("posicion", posicionSeleccionada)  // Usar la posición como parámetro
                    .getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            llamadaEM.emf().close();
        }

        return jugadores;
    }
}