package edu.badpals.proyectoad3.controller;

import edu.badpals.proyectoad3.DAO.EquipoDAO;
import edu.badpals.proyectoad3.DAO.LigasDAO;
import edu.badpals.proyectoad3.DAO.ConnectionDAO;
import edu.badpals.proyectoad3.model.Equipo;
import edu.badpals.proyectoad3.model.Liga;
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

public class LeaguesViewController {

    @FXML
    public Button DeleteLigaBtn;

    @FXML
    public Button UpdateLigaBtn;

    @FXML
    public Button CreateLigaBtn;

    @FXML
    private TableView<Liga> tableLigas;

    @FXML
    private TableColumn<Liga, String> colNombre;

    @FXML
    private TableColumn<Liga, String> colRegion;

    @FXML
    private TableColumn<Liga, String> colTier;

    @FXML
    private TableColumn<Liga, String> colFechaCreacion;

    @FXML
    private Button VolverLigaBtn;

    @FXML
    private TextField LigaNameTxt;

    @FXML
    private DatePicker LigaDateTxt;

    @FXML
    private ComboBox LigaRegionCmb;

    @FXML
    private ComboBox LigaTierCmb;

    private final ConnectionDAO conectionApp = new ConnectionDAO();

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
            List<Liga> ligas = LigasDAO.getLigas(connection);
            ObservableList<Liga> ligasObservableList = FXCollections.observableArrayList(ligas);
            tableLigas.setItems(ligasObservableList);
            ConnectionDAO.cerrarConexion(connection);
        } else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void initialize() {
        setCells();
        loadData();
        tableLigas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableLigas.setOnMouseClicked(event -> {
            if (!tableLigas.getSelectionModel().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                cargarDatosLiga();
            }
        });
        LigaRegionCmb.getItems().addAll("NA", "EU", "KR", "CN", "BR", "LATAM", "OCE", "SEA", "JP", "TR", "CIS");
        LigaTierCmb.getItems().addAll("1", "2", "3", "4", "5");


    }

    private void cargarDatosLiga() {
        Liga ligaSeleccionada = tableLigas.getSelectionModel().getSelectedItem();
        if (ligaSeleccionada != null) {
            LigaNameTxt.setText(ligaSeleccionada.getNombre());
            LigaDateTxt.setValue(ligaSeleccionada.getFechaCreacion());
            LigaRegionCmb.setValue(ligaSeleccionada.getRegion());
            LigaTierCmb.setValue(ligaSeleccionada.getTier());
        }
    }

    @FXML
    private void CreateLiga(ActionEvent event) {
        Connection connection = conectionApp.crearConexion();
        if (LigaNameTxt.getText().isEmpty() || LigaDateTxt.getValue() == null ||
                LigaRegionCmb.getValue() == null || LigaTierCmb.getValue() == null) {
            AlertasController.mostrarError("Error", "Todos los campos deben estar completos.");
            return;
        }
        List<Liga> ligas = LigasDAO.getLigas(connection);
        boolean ligaExiste = false;
        for (Liga liga : ligas) {
            if (liga.getNombre().equals(LigaNameTxt.getText())) {
                ligaExiste = true;
                break;
            }
        }

        if (ligaExiste) {
            AlertasController.mostrarError("Error", "Ya existe una liga con ese nombre.");
            return;
        }

        if (connection != null) {
            Liga liga = new Liga();
            liga.setNombre(LigaNameTxt.getText());
            liga.setFechaCreacion(LigaDateTxt.getValue());
            liga.setRegion(LigaRegionCmb.getValue().toString());
            liga.setTier(LigaTierCmb.getValue().toString());
            LigasDAO.addLeague(liga);
            ConnectionDAO.cerrarConexion(connection);
            loadData();
            limpiarCeldasLigas();
            AlertasController.mostrarInformacion("Éxito", "Liga creada correctamente.");
        } else {
            AlertasController.mostrarError("Error", "No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    private void UpdateLiga(ActionEvent event) {
        Liga ligaSeleccionada = tableLigas.getSelectionModel().getSelectedItem();
        if (ligaSeleccionada == null) {
            AlertasController.mostrarAdvertencia("Error", "Debe seleccionar una liga para actualizar.");
            return;
        }
        Connection connection = conectionApp.crearConexion();
        if (LigaNameTxt.getText().isEmpty() || LigaDateTxt.getValue() == null ||
                LigaRegionCmb.getValue() == null || LigaTierCmb.getValue() == null) {
            AlertasController.mostrarError("Error", "Todos los campos deben estar completos.");
            return;
        }
        if (connection != null) {
            Liga liga = tableLigas.getSelectionModel().getSelectedItem();
            liga.setNombre(LigaNameTxt.getText());
            liga.setFechaCreacion(LigaDateTxt.getValue());
            liga.setRegion(LigaRegionCmb.getValue().toString());
            liga.setTier(LigaTierCmb.getValue().toString());
            LigasDAO.updateLeague(liga);
            ConnectionDAO.cerrarConexion(connection);
            loadData();
            limpiarCeldasLigas();
            AlertasController.mostrarInformacion("Éxito", "Liga actualizada exitosamente.");
        } else {
            AlertasController.mostrarError("Error", "No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    private void DeleteLiga(ActionEvent event) {
        Liga ligaSeleccionada = tableLigas.getSelectionModel().getSelectedItem();
        if (ligaSeleccionada == null) {
            AlertasController.mostrarAdvertencia("Error", "Debe seleccionar una liga para eliminar.");
            return;
        }
        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            Liga liga = tableLigas.getSelectionModel().getSelectedItem();
            LigasDAO.deleteLeague(liga);
            ConnectionDAO.cerrarConexion(connection);
            loadData();
            limpiarCeldasLigas();
            AlertasController.mostrarInformacion("Éxito", "Liga eliminada exitosamente.");
        } else {
            AlertasController.mostrarError("Error", "No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void searchLigaByName() {
        String nombreLiga = LigaNameTxt.getText().trim();

        if (!nombreLiga.isEmpty()) {
            List<Liga> ligas = LigasDAO.getLigasPorNombre(nombreLiga);
            ObservableList<Liga> ligasObservableList = FXCollections.observableArrayList(ligas);
            tableLigas.setItems(ligasObservableList);
        } else {
            AlertasController.mostrarAdvertencia("Campo vacío", "Debe ingresar un nombre para buscar.");
        }
    }

    @FXML
    public void mostrarLigasPorRegion(ActionEvent event) {
        Connection connection = conectionApp.crearConexion();
        String region = LigaRegionCmb.getValue().toString();
        if (connection != null) {
            List<Liga> ligas = LigasDAO.getLigasPorRegion(region);
            ObservableList<Liga> ligasObservableList = FXCollections.observableArrayList(ligas);
            tableLigas.setItems(ligasObservableList);
            ConnectionDAO.cerrarConexion(connection);
        } else {
            AlertasController.mostrarError("Error de conexión", "No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void mostrarLigasPorTier(ActionEvent event) {
        Connection connection = conectionApp.crearConexion();
        String tier = LigaTierCmb.getValue().toString();  // Obtén el valor del tier seleccionado en el ComboBox
        if (connection != null) {
            // Llamamos a la función que obtiene las ligas por tier
            List<Liga> ligas = LigasDAO.getLigasPorTier(tier);
            ObservableList<Liga> ligasObservableList = FXCollections.observableArrayList(ligas);
            tableLigas.setItems(ligasObservableList);
            ConnectionDAO.cerrarConexion(connection);
        } else {
            AlertasController.mostrarError("Error de conexión", "No se pudo establecer la conexión con la base de datos.");
        }
    }


    @FXML
    private void recargarCeldasYtabla() {
        limpiarCeldasLigas();
        tableLigas.refresh();
        tableLigas.refresh();
        loadData();
    }

    @FXML
    private void limpiarCeldasLigas() {
        LigaNameTxt.clear();
        LigaDateTxt.setValue(null);
        LigaRegionCmb.setValue(null);
        LigaTierCmb.setValue(null);
        tableLigas.getSelectionModel().clearSelection();
    }


}