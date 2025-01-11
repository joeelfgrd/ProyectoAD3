package edu.badpals.proyectoad3.controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class AlertasController {

    // Cargar el archivo CSS con los estilos personalizados
    private static String rutaCSS = "ruta/al/archivo/Styles.css"; // Actualiza esta ruta

    private static void aplicarEstilo(Alert alerta) {
        Scene scene = new Scene(alerta.getDialogPane());
        scene.getStylesheets().add(AlertasController.class.getResource(rutaCSS).toExternalForm());
        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.setScene(scene);
    }

    public static void mostrarInformacion(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        aplicarEstilo(alerta);
        alerta.showAndWait();
    }

    public static void mostrarAdvertencia(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        aplicarEstilo(alerta);
        alerta.showAndWait();
    }

    public static void mostrarError(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        aplicarEstilo(alerta);
        alerta.showAndWait();
    }
}
