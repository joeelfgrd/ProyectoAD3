package edu.badpals.proyectoad3.controller;

import edu.badpals.proyectoad3.model.Conection_App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;



public class RegisterViewController {

    @FXML
    private ComboBox SelectTeamCmb;

    @FXML
    private ComboBox SelectLeagueCmb;

    @FXML
    private Button DeleteRegisterBtn;

    @FXML
    private Button UpdateRegisterBtn;

    @FXML
    private Button CreateRegisterBtn;

    @FXML
    private Button VolverRegisterBtn;

    @FXML
    private TextField PriceRegisterTxt;

    @FXML
    private TextField DateRegisterTxt;

    private final Conection_App conectionApp = new Conection_App();

    @FXML
    public void toMainMenu(ActionEvent event) throws IOException {
        // Carga el archivo FXML usando una ruta absoluta
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/badpals/proyectoad3/MainView.fxml"));

        // Carga la vista principal
        Parent root = loader.load();

        // Crear y mostrar una nueva ventana
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        // Cerrar la ventana actual
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void initialize() {
        List<String> listaEquipos = Conection_App.returnAllTeams(conectionApp.crearConexion());
        List<String> listaLigas = Conection_App.returnAllLeagues(conectionApp.crearConexion());
        SelectTeamCmb.getItems().addAll(listaEquipos);
        SelectLeagueCmb.getItems().addAll(listaLigas);
    }
}
