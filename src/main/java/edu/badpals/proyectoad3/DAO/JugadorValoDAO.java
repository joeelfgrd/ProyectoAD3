package edu.badpals.proyectoad3.DAO;

import edu.badpals.proyectoad3.model.Equipo;
import edu.badpals.proyectoad3.model.InformacionPersonal;
import edu.badpals.proyectoad3.model.ValorantPlayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;

public class JugadorValoDAO {

    public static void addValoPlayer(ValorantPlayer valoPlayer) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
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

    public static void deleteValoPlayerForID(Long id) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
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
            ConnectionDAO.cerrarConexion(c);
        }
    }

    public static void updateValoPlayer(ValorantPlayer valoPlayer) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Connection c = connectionDAO.crearConexion();
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
            ConnectionDAO.cerrarConexion(c);
        }
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
                Equipo equipo = EquipoDAO.getEquipoById(c, equipoId);
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

    public static List<ValorantPlayer> getJugadorValorantPorEquipo(String nombreEquipo) {
        ConnectionDAO.llamadaEntityManager llamadaEM = ConnectionDAO.getLlamadaEntityManager();
        EntityManager em = llamadaEM.em();
        List<ValorantPlayer> jugadoresValorant = new ArrayList<>();


        String jpql = "SELECT vp FROM ValorantPlayer vp " +
                "JOIN vp.Equipo e " +
                "WHERE e.nombre = :nombreEquipo";

        try {
            em.getTransaction().begin();
            jugadoresValorant = em.createQuery(jpql, ValorantPlayer.class)
                    .setParameter("nombreEquipo", nombreEquipo)
                    .getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            llamadaEM.emf().close();
        }

        return jugadoresValorant;
    }

    public static List<ValorantPlayer> getJugadorValorantPorPais(String pais) {
        ConnectionDAO.llamadaEntityManager llamadaEM = ConnectionDAO.getLlamadaEntityManager();
        EntityManager em = llamadaEM.em();
        List<ValorantPlayer> jugadoresValorant = new ArrayList<>();

        String jpql = "SELECT vp FROM ValorantPlayer vp " +
                "WHERE vp.informacionPersonal.pais = :pais";

        try {
            em.getTransaction().begin();
            jugadoresValorant = em.createQuery(jpql, ValorantPlayer.class)
                    .setParameter("pais", pais)
                    .getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            llamadaEM.emf().close();
        }

        return jugadoresValorant;
    }

    public static List<ValorantPlayer> getJugadorValorantPorRol(String rol) {
        ConnectionDAO.llamadaEntityManager llamadaEM = ConnectionDAO.getLlamadaEntityManager();
        EntityManager em = llamadaEM.em();
        List<ValorantPlayer> jugadoresValorant = new ArrayList<>();
        String jpql = "SELECT vp FROM ValorantPlayer vp " +
                "WHERE vp.rol = :rol";

        try {
            em.getTransaction().begin();
            jugadoresValorant = em.createQuery(jpql, ValorantPlayer.class)
                    .setParameter("rol", rol)
                    .getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            llamadaEM.emf().close();
        }

        return jugadoresValorant;
    }

    public static List<ValorantPlayer> getJugadorPorNickname(String nombreNickname) {
        ConnectionDAO.llamadaEntityManager llamadaEM = ConnectionDAO.getLlamadaEntityManager();
        EntityManager em = llamadaEM.em();
        List<ValorantPlayer> jugadoresValorant = new ArrayList<>();
        String jpql = "SELECT vp FROM ValorantPlayer vp WHERE vp.Nickname like :nickname";

        try {
            em.getTransaction().begin();
            jugadoresValorant = em.createQuery(jpql, ValorantPlayer.class).setParameter("nickname", nombreNickname).getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            llamadaEM.emf().close();
        }

        return jugadoresValorant;
    }
}