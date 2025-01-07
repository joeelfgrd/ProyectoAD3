package edu.badpals.proyectoad3.controller;

import edu.badpals.proyectoad3.model.Conection_App;
import edu.badpals.proyectoad3.model.entities.Equipo;
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

public class EquiposViewController {

    @FXML
    private TableView<Equipo> tableEquipos;

    @FXML
    private TableColumn<Equipo, String> colNombre;

    @FXML
    private TableColumn<Equipo, String> colRegion;

    @FXML
    private TableColumn<Equipo, String> colTier;

    @FXML
    private TableColumn<Equipo, String> colFechaCreacion;

    @FXML
    private Button VolverTeamBtn;

    @FXML
    private TextField TeamNameTxt;

    @FXML
    private TextField TeamDateTxt;

    @FXML
    private ComboBox TeamRegionCmb;

    @FXML
    private ComboBox TeamTierCmb;

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
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
        colTier.setCellValueFactory(new PropertyValueFactory<>("tier"));
        colFechaCreacion.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
    }

    @FXML
    public void loadData() {
        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            List<Equipo> equipos = Conection_App.getEquipos(connection);
            ObservableList<Equipo> equiposObservableList = FXCollections.observableArrayList(equipos);
            tableEquipos.setItems(equiposObservableList);
            conectionApp.cerrarConexion(connection);
        } else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void initialize() {
        setCells();
        loadData();
        tableEquipos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableEquipos.setOnMouseClicked(event -> {
            if (!tableEquipos.getSelectionModel().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                cargarDatosEquipo();
            }
        });
        TeamRegionCmb.getItems().addAll("NA", "EU", "KR", "CN", "BR", "LATAM", "OCE", "SEA", "JP", "TR", "CIS");
        TeamTierCmb.getItems().addAll("1", "2", "3", "4", "5");

    }

    private void cargarDatosEquipo() {
        Equipo equipoSeleccionado = tableEquipos.getSelectionModel().getSelectedItem();

        if (equipoSeleccionado != null) {
            TeamNameTxt.setText(equipoSeleccionado.getNombre());
            TeamDateTxt.setText(String.valueOf(equipoSeleccionado.getFechaCreacion()));
            TeamRegionCmb.setValue(equipoSeleccionado.getRegion());
            TeamTierCmb.setValue(equipoSeleccionado.getTier());
        }
    }




}
