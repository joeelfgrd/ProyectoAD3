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
import java.sql.Date;
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
    private DatePicker TeamDateTxt;

    @FXML
    private ComboBox TeamRegionCmb;

    @FXML
    private ComboBox TeamTierCmb;

    private final Conection_App conectionApp = new Conection_App();

    @FXML
    public void toMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/badpals/proyectoad3/MainView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
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
            TeamDateTxt.setValue(equipoSeleccionado.getFechaCreacion());
            TeamRegionCmb.setValue(equipoSeleccionado.getRegion());
            TeamTierCmb.setValue(equipoSeleccionado.getTier());
        }
    }

    @FXML
    public void crearEquipo() {
        if (TeamNameTxt.getText().isEmpty() || TeamDateTxt.getValue() == null ||
                TeamRegionCmb.getValue() == null || TeamTierCmb.getValue() == null) {
            System.out.println("Todos los campos deben estar completos.");
            return;
        }
        try {
            Equipo equipo = new Equipo();
            equipo.setNombre(TeamNameTxt.getText());
            equipo.setFechaCreacion(TeamDateTxt.getValue());
            equipo.setRegion(TeamRegionCmb.getValue().toString());
            equipo.setTier(TeamTierCmb.getValue().toString());
            Conection_App.addTeam(equipo);
            loadData();
            TeamNameTxt.clear();
            TeamDateTxt.setValue(null);
            TeamRegionCmb.setValue(null);
            TeamTierCmb.setValue(null);

            System.out.println("Equipo creado exitosamente.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error al crear el equipo: " + e.getMessage());
        }
    }

    @FXML
    public void actualizarEquipo() {
        Equipo equipoSeleccionado = tableEquipos.getSelectionModel().getSelectedItem();
        if (equipoSeleccionado == null) {
            System.out.println("Debe seleccionar un equipo para actualizar.");
            return;
        }
        if (TeamNameTxt.getText().isEmpty() || TeamDateTxt.getValue() == null ||
                TeamRegionCmb.getValue() == null || TeamTierCmb.getValue() == null) {
            System.out.println("Todos los campos deben estar completos.");
            return;
        }

        try {
            equipoSeleccionado.setNombre(TeamNameTxt.getText());
            equipoSeleccionado.setFechaCreacion(TeamDateTxt.getValue());
            equipoSeleccionado.setRegion(TeamRegionCmb.getValue().toString());
            equipoSeleccionado.setTier(TeamTierCmb.getValue().toString());
            Conection_App.updateTeam(equipoSeleccionado);
            loadData();
            TeamNameTxt.clear();
            TeamDateTxt.setValue(null);
            TeamRegionCmb.setValue(null);
            TeamTierCmb.setValue(null);

            System.out.println("Equipo actualizado exitosamente.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error al actualizar el equipo: " + e.getMessage());
        }
    }


    @FXML
    public void eliminarEquipo() {
        Equipo equipoSeleccionado = tableEquipos.getSelectionModel().getSelectedItem();
        if (equipoSeleccionado == null) {
            System.out.println("Debe seleccionar un equipo para eliminar.");
            return;
        }

        try {
            Conection_App.deleteTeam(equipoSeleccionado);
            loadData();
            TeamNameTxt.clear();
            TeamDateTxt.setValue(null);
            TeamRegionCmb.setValue(null);
            TeamTierCmb.setValue(null);

            System.out.println("Equipo eliminado exitosamente.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error al eliminar el equipo: " + e.getMessage());
        }
    }
}