package edu.badpals.proyectoad3.model;


import jakarta.persistence.*;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        generateDB();
    }

    public static void generateDB() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Liga liga = new Liga();
            liga.setNombre("Liga de Ejemplo");
            liga.setFechaCreacion(LocalDate.of(2020, 1, 1));
            liga.setRegion("EU");
            liga.setTier("S");



            Equipo equipo = new Equipo();
            equipo.setNombre("Equipo de Prueba");
            equipo.setFechaCreacion(LocalDate.of(2015, 5, 15));
            equipo.setRegion("EU");
            equipo.setTier("A");


            EquipoLiga equipoLiga = new EquipoLiga();
            equipoLiga.setEquipo(equipo);
            equipoLiga.setLiga(liga);
            equipoLiga.setFechaInscripcion(LocalDate.of(2022, 10, 10));
            equipoLiga.setPrecioPlaza(5000.00);


            em.persist(liga);
            em.persist(equipo);
            em.persist(equipoLiga);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
