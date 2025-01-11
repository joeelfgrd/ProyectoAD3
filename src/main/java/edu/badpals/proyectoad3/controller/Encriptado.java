package edu.badpals.proyectoad3.controller;

import org.mindrot.jbcrypt.BCrypt;

public class Encriptado {
    public static String encriptar(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verificar(String password, String hashedPassword) {
        try {
            return BCrypt.checkpw(password, hashedPassword);
        } catch (IllegalArgumentException e) {
            System.err.println("Error al verificar la contraseña: " + e.getMessage());
            return false;
        }
    }
}
