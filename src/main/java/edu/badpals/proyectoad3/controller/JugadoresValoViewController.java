package edu.badpals.proyectoad3.controller;

import edu.badpals.proyectoad3.model.Conection_App;
import edu.badpals.proyectoad3.model.entities.ValorantPlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class JugadoresValoViewController {

    @FXML
    private TableView<ValorantPlayer> ValoPlayerTableView;

    @FXML
    private TableColumn<ValorantPlayer, String> colName;

    @FXML
    private TableColumn<ValorantPlayer, String> colSurname;

    @FXML
    private TableColumn<ValorantPlayer, String> colCountry;

    @FXML
    private TableColumn<ValorantPlayer, String> colTeam;

    @FXML
    private TableColumn<ValorantPlayer, String> colRole;

    @FXML
    private TableColumn<ValorantPlayer, String> colAgent;

    @FXML
    private TableColumn<ValorantPlayer, String> colNickname;

    @FXML
    private TableColumn<ValorantPlayer, Boolean> colIGL;

    @FXML
    private Button VolverValoPlayerBtn;

    @FXML
    private TextField ValoPlayerNameTxt;

    @FXML
    private TextField ValoPlayerSurnameTxt;

    @FXML
    private ComboBox<String> ValoPlayerCountryCmb;

    @FXML
    private ComboBox<String> ValoPlayerTeamCmb;

    @FXML
    private ComboBox<String> ValoPlayerRolCmb;

    @FXML
    private TextField ValoPlayerNickTxt;

    @FXML
    private ComboBox<String> ValoPlayerAgentCmb;

    @FXML
    private CheckBox IGLCheckbox;



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
    public void setCells() {
        colName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("pais"));
        colTeam.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colNickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        colAgent.setCellValueFactory(new PropertyValueFactory<>("agente"));
        colIGL.setCellValueFactory(new PropertyValueFactory<>("IGL"));
    }

    @FXML
    public void loadData() {
        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            List<ValorantPlayer> valorantPlayers = Conection_App.getJugadorValorant(connection);
            ObservableList<ValorantPlayer> valorantPlayerObservableList = FXCollections.observableArrayList(valorantPlayers);
            ValoPlayerTableView.setItems(valorantPlayerObservableList);
            conectionApp.cerrarConexion(connection);
        } else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void initialize() {
        setCells();
        loadData();
        ValoPlayerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ValoPlayerTableView.setOnMouseClicked(event -> {
            if (!ValoPlayerTableView.getSelectionModel().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                cargarDatosJugador();
            }
        });

        if (IGLCheckbox != null) {
            IGLCheckbox.setSelected(true);
        } else {
            System.out.println("CheckBox no está inicializado");
        }
    }


    private void cargarDatosJugador() {
        ValorantPlayer jugadorSeleccionado = ValoPlayerTableView.getSelectionModel().getSelectedItem();

        if (jugadorSeleccionado != null) {
            ValoPlayerNameTxt.setText(jugadorSeleccionado.getInformacionPersonal().getNombre());
            ValoPlayerSurnameTxt.setText(jugadorSeleccionado.getInformacionPersonal().getApellidos());
            ValoPlayerNickTxt.setText(jugadorSeleccionado.getNickname());
            ValoPlayerCountryCmb.setValue(jugadorSeleccionado.getInformacionPersonal().getPais());
            ValoPlayerTeamCmb.setValue(jugadorSeleccionado.getEquipo());
            ValoPlayerRolCmb.setValue(jugadorSeleccionado.getRol());
            ValoPlayerNickTxt.setText(jugadorSeleccionado.getNickname());
            ValoPlayerAgentCmb.setValue(jugadorSeleccionado.getAgente());
            IGLCheckbox.setSelected(jugadorSeleccionado.isIGL());
        }
    }


}
