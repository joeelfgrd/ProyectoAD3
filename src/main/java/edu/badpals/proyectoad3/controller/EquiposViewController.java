package edu.badpals.proyectoad3.controller;

import edu.badpals.proyectoad3.DAO.EquipoDAO;
import edu.badpals.proyectoad3.DAO.ConnectionDAO;
import edu.badpals.proyectoad3.model.Equipo;
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
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
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
    private TextField TeamNameTxt;

    @FXML
    private DatePicker TeamDateTxt;

    @FXML
    private ComboBox<String> TeamRegionCmb;

    @FXML
    private ComboBox<String> TeamTierCmb;

    @FXML
    private Button LimpiarCamposEquiposBtn;

    private final ConnectionDAO conectionApp = new ConnectionDAO();

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

    @FXML
    public void setCells() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
        colTier.setCellValueFactory(new PropertyValueFactory<>("tier"));
        colFechaCreacion.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
    }

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
    public void loadData() {
        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            List<Equipo> equipos = EquipoDAO.getEquipos(connection);
            ObservableList<Equipo> equiposObservableList = FXCollections.observableArrayList(equipos);
            tableEquipos.setItems(equiposObservableList);
            conectionApp.cerrarConexion(connection);
        } else {
            AlertasController.mostrarError("Error de conexión", "No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void crearEquipo() {
        if (checkCamposVaciosEquipo()) return;
        try {
            Connection connection = conectionApp.crearConexion();
            if (connection != null) {
                List<Equipo> equipos = EquipoDAO.getEquipos(connection);
                boolean nombreExiste = false;
                for (Equipo equipo : equipos) {
                    if (equipo.getNombre().equalsIgnoreCase(TeamNameTxt.getText())) {
                        nombreExiste = true;
                        break;
                    }
                }

                if (nombreExiste) {
                    AlertasController.mostrarAdvertencia("Nombre duplicado", "Ya existe un equipo con ese nombre.");
                    conectionApp.cerrarConexion(connection);
                    return;
                }

                Equipo equipo = new Equipo();
                equipo.setNombre(TeamNameTxt.getText());
                equipo.setFechaCreacion(TeamDateTxt.getValue());
                equipo.setRegion(TeamRegionCmb.getValue().toString());
                equipo.setTier(TeamTierCmb.getValue().toString());

                EquipoDAO.addTeam(equipo);
                loadData();
                limpiarCeldasEquipos();

                AlertasController.mostrarInformacion("Éxito", "Equipo creado exitosamente.");
                conectionApp.cerrarConexion(connection);
            } else {
                AlertasController.mostrarError("Error de conexión", "No se pudo establecer la conexión con la base de datos.");
            }
        } catch (Exception e) {
            AlertasController.mostrarError("Error", "Ocurrió un error al crear el equipo: " + e.getMessage());
        }
    }

    @FXML
    public void actualizarEquipo(ActionEvent event) {
        Equipo equipoSeleccionado = tableEquipos.getSelectionModel().getSelectedItem();
        if (equipoSeleccionado == null) {
            AlertasController.mostrarAdvertencia("Selección requerida", "Debe seleccionar un equipo para actualizar.");
            return;
        }
        if (checkCamposVaciosEquipo()) return;

        try {
            equipoSeleccionado.setNombre(TeamNameTxt.getText());
            equipoSeleccionado.setFechaCreacion(TeamDateTxt.getValue());
            equipoSeleccionado.setRegion(TeamRegionCmb.getValue().toString());
            equipoSeleccionado.setTier(TeamTierCmb.getValue().toString());
            EquipoDAO.updateTeam(equipoSeleccionado);

            loadData();
            limpiarCeldasEquipos();

            AlertasController.mostrarInformacion("Éxito", "Equipo actualizado exitosamente.");
        } catch (Exception e) {
            AlertasController.mostrarError("Error", "Ocurrió un error al actualizar el equipo: " + e.getMessage());
        }
    }

    @FXML
    public void eliminarEquipo(ActionEvent event) {
        Equipo equipoSeleccionado = tableEquipos.getSelectionModel().getSelectedItem();
        if (equipoSeleccionado == null) {
            AlertasController.mostrarAdvertencia("Selección requerida", "Debe seleccionar un equipo para eliminar.");
            return;
        }
        try {
            if (EquipoDAO.equipoTieneJugadores(equipoSeleccionado)) {
                AlertasController.mostrarError("Error", "No se puede eliminar el equipo porque tiene jugadores asociados.");
                return;
            }
            EquipoDAO.deleteTeam(equipoSeleccionado);
            loadData();
            limpiarCeldasEquipos();

            AlertasController.mostrarInformacion("Éxito", "Equipo eliminado exitosamente.");
        } catch (Exception e) {
            AlertasController.mostrarError("Error", "Ocurrió un error al eliminar el equipo: " + e.getMessage());
        }
    }

    private boolean checkCamposVaciosEquipo() {
        if (TeamNameTxt.getText().isEmpty() || TeamDateTxt.getValue() == null ||
                TeamRegionCmb.getValue() == null || TeamTierCmb.getValue() == null) {
            AlertasController.mostrarAdvertencia("Campos incompletos", "Todos los campos deben estar completos.");
            return true;
        }
        return false;
    }

    @FXML
    public void mostrarEquiposPorRegion(ActionEvent event) {
        Connection connection = conectionApp.crearConexion();
        String region = TeamRegionCmb.getValue();
        if (connection != null) {
            List<Equipo> equipos = EquipoDAO.getEquiposPorRegion(region);
            ObservableList<Equipo> equiposObservableList = FXCollections.observableArrayList(equipos);
            tableEquipos.setItems(equiposObservableList);
            ConnectionDAO.cerrarConexion(connection);
        } else {
            AlertasController.mostrarError("Error de conexión", "No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public  void mostrarEquiposPorTier(ActionEvent event){
        Connection connection = conectionApp.crearConexion();
        String tier = TeamTierCmb.getValue();
        if (connection != null) {
            List<Equipo> equipos = EquipoDAO.getEquiposPorTier(tier);
            ObservableList<Equipo> equiposObservableList = FXCollections.observableArrayList(equipos);
            tableEquipos.setItems(equiposObservableList);
            ConnectionDAO.cerrarConexion(connection);
        } else {
            AlertasController.mostrarError("Error de conexión", "No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void searchEquipoByName() {
        String nombreEquipo = TeamNameTxt.getText().trim();

        if (!nombreEquipo.isEmpty()) {
            List<Equipo> equipos = EquipoDAO.getEquiposPorNombre(nombreEquipo);
            ObservableList<Equipo> equipoObservableList = FXCollections.observableArrayList(equipos);
            tableEquipos.setItems(equipoObservableList);
        } else {
            AlertasController.mostrarAdvertencia("Campo vacío", "Debe ingresar un nombre para buscar.");
        }
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
    private void limpiarCeldasEquipos() {
        TeamNameTxt.clear();
        TeamDateTxt.setValue(null);
        TeamRegionCmb.setValue(null);
        TeamTierCmb.setValue(null);
    }

    @FXML
    private void recargarCeldasYtabla() {
        limpiarCeldasEquipos();
        tableEquipos.refresh();
        loadData();
    }

}