package edu.badpals.proyectoad3.controller;

import edu.badpals.proyectoad3.model.Conection_App;
import edu.badpals.proyectoad3.model.entities.Equipo;
import edu.badpals.proyectoad3.model.entities.EquipoLiga;
import edu.badpals.proyectoad3.model.entities.Liga;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.beans.property.SimpleStringProperty;
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
import java.time.LocalDate;
import java.util.List;

public class RegisterViewController {

    @FXML
    private ComboBox<String> SelectTeamCmb;

    @FXML
    private ComboBox<String> SelectLeagueCmb;

    @FXML
    private TableView<EquipoLiga> tableRegister;

    @FXML
    private TableColumn<EquipoLiga, String> colEquipo;

    @FXML
    private TableColumn<EquipoLiga, String> colLiga;

    @FXML
    private TableColumn<EquipoLiga, Double> colPrecio;

    @FXML
    private TableColumn<EquipoLiga, LocalDate> colFechaInscripcion;

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

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    private EntityManager em;

    @FXML
    public void initialize() {
        em = emf.createEntityManager();
        setCells();
        loadData();
        loadComboBoxes();
        tableRegister.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableRegister.setOnMouseClicked(event -> {
            if (!tableRegister.getSelectionModel().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                cargarDatosRegistro();
            }
        });
    }

    private void setCells() {
        colEquipo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEquipo().getNombre()));
        colLiga.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getLiga().getNombre()));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioPlaza"));
        colFechaInscripcion.setCellValueFactory(new PropertyValueFactory<>("fechaInscripcion"));
    }

    private void loadData() {
        List<EquipoLiga> registros = em.createQuery("SELECT e FROM EquipoLiga e", EquipoLiga.class).getResultList();
        ObservableList<EquipoLiga> registroObservableList = FXCollections.observableArrayList(registros);
        tableRegister.setItems(registroObservableList);
    }

    private void loadComboBoxes() {
        Connection connection = new Conection_App().crearConexion();
        if (connection != null) {
            List<Equipo> equipos = Conection_App.getEquipos(connection);
            equipos.forEach(equipo -> SelectTeamCmb.getItems().add(equipo.getNombre()));

            List<Liga> ligas = Conection_App.getLigas(connection);
            ligas.forEach(liga -> SelectLeagueCmb.getItems().add(liga.getNombre()));



            new Conection_App().cerrarConexion(connection);
        }
    }

    private void cargarDatosRegistro() {
        EquipoLiga registroSeleccionado = tableRegister.getSelectionModel().getSelectedItem();

        if (registroSeleccionado != null) {
            SelectLeagueCmb.setValue(registroSeleccionado.getLiga().getNombre());
            SelectTeamCmb.setValue(registroSeleccionado.getEquipo().getNombre());


            DateRegisterTxt.setText(String.valueOf(registroSeleccionado.getFechaInscripcion()));
            PriceRegisterTxt.setText(String.valueOf(registroSeleccionado.getPrecioPlaza()));
        }
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






}
