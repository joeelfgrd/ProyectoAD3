package edu.badpals.proyectoad3.controller;

import edu.badpals.proyectoad3.DAO.EquipoDAO;
import edu.badpals.proyectoad3.DAO.JugadorLolDAO;
import edu.badpals.proyectoad3.DAO.ConnectionDAO;
import edu.badpals.proyectoad3.model.Equipo;
import edu.badpals.proyectoad3.model.InformacionPersonal;
import edu.badpals.proyectoad3.model.LolPlayer;
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

public class JugadoresLolViewController {

    @FXML
    public CheckBox LeaguePlayerEarlyShtCllrChk;
    @FXML
    public CheckBox LeaguePlayerLateShtCllrChk;
    @FXML
    public Button CreateLeaguePlayerBtn;
    @FXML
    public Button UpdateLeaguePlayerBtn;
    @FXML
    public Button DeleteLeaguePlayerBtn;
    @FXML
    public TextField LeaguePlayerNameTxt;
    @FXML
    public TextField LeaguePlayerSurnameTxt;
    @FXML
    public TextField LeaguePlayerNickTxt;
    @FXML
    public Button VolverLeaguePlayerBtn;
    @FXML
    public TableView<LolPlayer> LolPlayerTableView;
    @FXML
    public TableColumn<LolPlayer, String> colName;
    @FXML
    public TableColumn<LolPlayer, String> colSurname;
    @FXML
    public TableColumn<LolPlayer, String> colCountry;
    @FXML
    public TableColumn<LolPlayer, String> colTeam;
    @FXML
    public TableColumn<LolPlayer, String> colPosition;
    @FXML
    public TableColumn<LolPlayer, String> colNickname;
    @FXML
    public TableColumn<LolPlayer, Boolean> colEarlyShotCaller;
    @FXML
    public TableColumn<LolPlayer, Boolean> colLateShotCaller;
    @FXML
    private ComboBox<String> LeaguePlayerPositionCmb;
    @FXML
    private ComboBox<String> LeaguePlayerTeamCmb;
    @FXML
    private ComboBox<String> LeaguePlayerCountryCmb;

    private final ConnectionDAO conectionApp = new ConnectionDAO();

//  Metodos iniciales para cargar los datos de la tabla y los combobox y la tabla

    @FXML
    public void initialize() {
        setCells();
        loadLolDataToTable();
        LeaguePlayerPositionCmb.getItems().addAll("Top", "Jungle", "Mid", "ADC", "Support");
        LeaguePlayerCountryCmb.getItems().addAll("Afghanistan", "Albania", "Argentina", "Australia", "Brazil", "Canada", "China", "France", "Germany", "India", "Japan", "Korea (South)", "Mexico", "Spain", "United Kingdom", "United States", "Vietnam");
        LeaguePlayerTeamCmb.getItems().addAll(EquipoDAO.returnAllTeams(conectionApp.crearConexion()));
        LolPlayerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        LolPlayerTableView.setOnMouseClicked(event -> {
            if (!LolPlayerTableView.getSelectionModel().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                cargarDatosJugadorDeTablaAceldas();
            }
        });
    }

    @FXML
    public void setCells() {
        colName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("pais"));
        colTeam.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("posicion"));
        colNickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        colEarlyShotCaller.setCellValueFactory(new PropertyValueFactory<>("earlyShotCaller"));
        colLateShotCaller.setCellValueFactory(new PropertyValueFactory<>("lateShotCaller"));
    }

    //  Metodo para moverse entre ventanas

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

    // -----CRUD----------

    @FXML
    private void createLolPlayer(ActionEvent event) {
        if (checkCamposVaciosLolPlayer()) {
            AlertasController.mostrarAdvertencia("Campos incompletos", "Por favor, completa todos los campos obligatorios.");
            return;
        }
        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            LolPlayer player = new LolPlayer();
            if (player.getInformacionPersonal() == null) {
                player.setInformacionPersonal(new InformacionPersonal());
            }

            obtenerDatosCamposLolPlayer(player, connection);

            JugadorLolDAO.addLolPlayer(player);
            ConnectionDAO.cerrarConexion(connection);
            loadLolDataToTable();
            limpiarCamposLeaguePlayer();
            AlertasController.mostrarInformacion("Éxito", "Jugador creado exitosamente.");
        } else {
            AlertasController.mostrarError("Error de conexión", "No se pudo establecer la conexión a la base de datos.");
        }
    }

    @FXML
    private void updateLeaguePlayer(ActionEvent event) {
        LolPlayer selectedPlayer = LolPlayerTableView.getSelectionModel().getSelectedItem();
        if (selectedPlayer == null) {
            AlertasController.mostrarAdvertencia("Selección necesaria", "Selecciona un jugador para actualizar.");
            return;
        }

        if (checkCamposVaciosLolPlayer()) {
            AlertasController.mostrarAdvertencia("Campos incompletos", "Por favor, completa todos los campos obligatorios.");
            return;
        }

        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            obtenerDatosCamposLolPlayer(selectedPlayer, connection);
            JugadorLolDAO.updateLolPlayer(selectedPlayer);
            ConnectionDAO.cerrarConexion(connection);
            loadLolDataToTable();
            limpiarCamposLeaguePlayer();
            AlertasController.mostrarInformacion("Éxito", "Jugador actualizado correctamente.");
        } else {
            AlertasController.mostrarError("Error de conexión", "No se pudo establecer la conexión a la base de datos.");
        }
    }

    @FXML
    private void deleteLolPlayer(ActionEvent event) {
        LolPlayer selectedPlayer = LolPlayerTableView.getSelectionModel().getSelectedItem();
        if (selectedPlayer == null) {
            AlertasController.mostrarAdvertencia("Selección necesaria", "Selecciona un jugador para eliminar.");
            return;
        }

        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            JugadorLolDAO.deleteLolPlayerForID(selectedPlayer.getId_jugador());
            ConnectionDAO.cerrarConexion(connection);
            loadLolDataToTable();
            limpiarCamposLeaguePlayer();
            AlertasController.mostrarInformacion("Éxito", "Jugador eliminado correctamente.");
        } else {
            AlertasController.mostrarError("Error de conexión", "No se pudo establecer la conexión a la base de datos.");
        }
    }

    // -----Metodos auxiliares----------

    @FXML
    public void loadLolDataToTable() {
        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            List<LolPlayer> LolPlayer = JugadorLolDAO.getLolPlayers(connection);
            ObservableList<LolPlayer> leaguePlayerObservableList = FXCollections.observableArrayList(LolPlayer);
            LolPlayerTableView.setItems(leaguePlayerObservableList);
            ConnectionDAO.cerrarConexion(connection);
        } else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
        }
    }

    private void obtenerDatosCamposLolPlayer(LolPlayer selectedPlayer, Connection connection) {
        selectedPlayer.getInformacionPersonal().setNombre(LeaguePlayerNameTxt.getText());
        selectedPlayer.getInformacionPersonal().setApellidos(LeaguePlayerSurnameTxt.getText());
        selectedPlayer.getInformacionPersonal().setPais(LeaguePlayerCountryCmb.getValue());
        selectedPlayer.setNickname(LeaguePlayerNickTxt.getText());
        selectedPlayer.setPosicion(LeaguePlayerPositionCmb.getValue());
        selectedPlayer.setEarlyShotcaller(LeaguePlayerEarlyShtCllrChk.isSelected());
        selectedPlayer.setLateShotcaller(LeaguePlayerLateShtCllrChk.isSelected());

        List<Equipo> equipos = EquipoDAO.getEquipos(connection);
        for (Equipo equipo : equipos) {
            if (equipo.getNombre().equals(LeaguePlayerTeamCmb.getValue())) {
                selectedPlayer.setEquipo(equipo);
                break;
            }
        }
    }

    private void cargarDatosJugadorDeTablaAceldas() {
        LolPlayer jugadorSeleccionado = LolPlayerTableView.getSelectionModel().getSelectedItem();
        if (jugadorSeleccionado != null) {
            LeaguePlayerNameTxt.setText(jugadorSeleccionado.getInformacionPersonal().getNombre());
            LeaguePlayerSurnameTxt.setText(jugadorSeleccionado.getInformacionPersonal().getApellidos());
            LeaguePlayerNickTxt.setText(jugadorSeleccionado.getNickname());
            LeaguePlayerCountryCmb.setValue(jugadorSeleccionado.getInformacionPersonal().getPais());
            if (jugadorSeleccionado.getEquipo() != null) {
                LeaguePlayerTeamCmb.setValue(jugadorSeleccionado.getEquipoNombre());
            } else {
                LeaguePlayerTeamCmb.setValue("No asignado");
            }
            LeaguePlayerPositionCmb.setValue(jugadorSeleccionado.getPosicion());
            LeaguePlayerEarlyShtCllrChk.setSelected(jugadorSeleccionado.isEarlyShotCaller());
            LeaguePlayerLateShtCllrChk.setSelected(jugadorSeleccionado.isLateShotCaller());
        }
    }

    private boolean checkCamposVaciosLolPlayer() {
        return LeaguePlayerNameTxt.getText().isEmpty() ||
                LeaguePlayerSurnameTxt.getText().isEmpty() ||
                LeaguePlayerNickTxt.getText().isEmpty() ||
                LeaguePlayerCountryCmb.getValue() == null ||
                LeaguePlayerTeamCmb.getValue() == null ||
                LeaguePlayerPositionCmb.getValue() == null;
    }

    private void limpiarCamposLeaguePlayer() {
        LeaguePlayerNameTxt.clear();
        LeaguePlayerSurnameTxt.clear();
        LeaguePlayerNickTxt.clear();
        LeaguePlayerCountryCmb.setValue(null);
        LeaguePlayerTeamCmb.setValue(null);
        LeaguePlayerPositionCmb.setValue(null);
        LeaguePlayerEarlyShtCllrChk.setSelected(false);
        LeaguePlayerLateShtCllrChk.setSelected(false);
    }
}