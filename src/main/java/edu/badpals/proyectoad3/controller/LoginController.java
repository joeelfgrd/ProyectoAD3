package edu.badpals.proyectoad3.controller;

import edu.badpals.proyectoad3.DAO.MongoDbDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    public void toMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/badpals/proyectoad3/hello-view.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    private final MongoDbDAO mongoConnection;

    public LoginController() {
        this.mongoConnection = new MongoDbDAO();
    }

    @FXML
    private TextField txtFieldUser;
    @FXML
    private PasswordField txtFieldPwd;

    @FXML
    public void acceder(ActionEvent event) throws IOException {
        String username = txtFieldUser.getText();
        String password = txtFieldPwd.getText();

        if (username.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return;
        }

        boolean loggedIn = mongoConnection.loginUser(username, password);
        if (loggedIn) {
            mostrarAlerta("Éxito", "¡Inicio de sesión exitoso!", Alert.AlertType.INFORMATION);
            toMainMenu(event);
        } else {
            mostrarAlerta("Error", "Credenciales incorrectas.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    public void registrarUser() {
        String username = txtFieldUser.getText();
        String password = txtFieldPwd.getText();

        if (username.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return;
        }

        boolean registrado = mongoConnection.registerUser(username, password);
        if (registrado) {
            mostrarAlerta("Éxito", "¡Usuario registrado exitosamente!", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "El usuario ya existe o hubo un problema.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
