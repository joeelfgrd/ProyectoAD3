package edu.badpals.proyectoad3.controller;

import edu.badpals.proyectoad3.model.Conection_App;
import edu.badpals.proyectoad3.model.entities.Liga;
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
    private TextField LigaDateTxt;

    @FXML
    private ComboBox LigaRegionCmb;

    @FXML
    private ComboBox LigaTierCmb;

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
            List<Liga> ligas = Conection_App.getLigas(connection);  // Asegúrate de tener este método en tu clase Conection_App
            ObservableList<Liga> ligasObservableList = FXCollections.observableArrayList(ligas);
            tableLigas.setItems(ligasObservableList);
            Conection_App.cerrarConexion(connection);
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
    }

    private void cargarDatosLiga() {
        Liga ligaSeleccionada = tableLigas.getSelectionModel().getSelectedItem();

        if (ligaSeleccionada != null) {
            LigaNameTxt.setText(ligaSeleccionada.getNombre());
            LigaDateTxt.setText(String.valueOf(ligaSeleccionada.getFechaCreacion()));
            LigaRegionCmb.setValue(ligaSeleccionada.getRegion());
            LigaTierCmb.setValue(ligaSeleccionada.getTier());
        }
    }
}
