package edu.badpals.proyectoad3.controller;

import edu.badpals.proyectoad3.model.Conection_App;
import edu.badpals.proyectoad3.model.entities.Equipo;
import edu.badpals.proyectoad3.model.entities.InformacionPersonal;
import edu.badpals.proyectoad3.model.entities.LolPlayer;
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
        colName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("pais"));
        colTeam.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("posicion"));
        colNickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        colEarlyShotCaller.setCellValueFactory(new PropertyValueFactory<>("earlyShotCaller"));
        colLateShotCaller.setCellValueFactory(new PropertyValueFactory<>("lateShotCaller"));
    }

    @FXML
    public void loadLolData() {
        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            List<LolPlayer> LolPlayer = Conection_App.getLolPlayers(connection);
            ObservableList<LolPlayer> leaguePlayerObservableList = FXCollections.observableArrayList(LolPlayer);
            LolPlayerTableView.setItems(leaguePlayerObservableList);
            Conection_App.cerrarConexion(connection);
        } else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void initialize() {
        setCells();
        loadLolData();
        LeaguePlayerPositionCmb.getItems().addAll("Top", "Jungle", "Mid", "ADC", "Support");
        LeaguePlayerCountryCmb.getItems().addAll("Afghanistan", "Albania", "Argentina", "Australia", "Brazil", "Canada", "China", "France", "Germany", "India", "Japan", "Korea (South)", "Mexico", "Spain", "United Kingdom", "United States", "Vietnam");
        LeaguePlayerTeamCmb.getItems().addAll(Conection_App.returnAllTeams(conectionApp.crearConexion()));
        LolPlayerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        LolPlayerTableView.setOnMouseClicked(event -> {
            if (!LolPlayerTableView.getSelectionModel().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                cargarDatosJugador();
            }
        });
    }

    private void cargarDatosJugador() {
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

    @FXML
    private void createLolPlayer(ActionEvent event) {
        Connection connection = conectionApp.crearConexion();
        if (LeaguePlayerNameTxt.getText().isEmpty() ||
                LeaguePlayerSurnameTxt.getText().isEmpty() ||
                LeaguePlayerNickTxt.getText().isEmpty() ||
                LeaguePlayerCountryCmb.getValue() == null ||
                LeaguePlayerTeamCmb.getValue() == null ||
                LeaguePlayerPositionCmb.getValue() == null) {
            System.out.println("Por favor, completa todos los campos obligatorios.");
            return;
        }

        if (connection != null) {
            LolPlayer player = new LolPlayer();
            if (player.getInformacionPersonal() == null) {
                player.setInformacionPersonal(new InformacionPersonal());
            }

            player.getInformacionPersonal().setNombre(LeaguePlayerNameTxt.getText());
            player.getInformacionPersonal().setApellidos(LeaguePlayerSurnameTxt.getText());
            player.getInformacionPersonal().setPais(LeaguePlayerCountryCmb.getValue());
            player.setNickname(LeaguePlayerNickTxt.getText());
            player.setPosicion(LeaguePlayerPositionCmb.getValue());
            player.setEarlyShotcaller(LeaguePlayerEarlyShtCllrChk.isSelected());
            player.setLateShotcaller(LeaguePlayerLateShtCllrChk.isSelected());

            List<Equipo> equipos = Conection_App.getEquipos(connection);
            for (Equipo equipo : equipos) {
                if (equipo.getNombre().equals(LeaguePlayerTeamCmb.getValue())) {
                    player.setEquipo(equipo);
                    break;
                }
            }

            Conection_App.addLolPlayer(player);
            Conection_App.cerrarConexion(connection);
            loadLolData();
            limpiarCamposLeaguePlayer();
            System.out.println("Jugador creado exitosamente.");
        } else {
            System.out.println("No se pudo establecer la conexión a la base de datos.");
        }
    }


    @FXML
    private void updateLeaguePlayer(ActionEvent event) {
        LolPlayer selectedPlayer = LolPlayerTableView.getSelectionModel().getSelectedItem();
        if (selectedPlayer == null) {
            System.out.println("Selecciona un jugador para actualizar.");
            return;
        }

        if (LeaguePlayerNameTxt.getText().isEmpty() ||
                LeaguePlayerSurnameTxt.getText().isEmpty() ||
                LeaguePlayerNickTxt.getText().isEmpty() ||
                LeaguePlayerCountryCmb.getValue() == null ||
                LeaguePlayerTeamCmb.getValue() == null ||
                LeaguePlayerPositionCmb.getValue() == null) {
            System.out.println("Por favor, completa todos los campos obligatorios.");
            return;
        }

        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            selectedPlayer.getInformacionPersonal().setNombre(LeaguePlayerNameTxt.getText());
            selectedPlayer.getInformacionPersonal().setApellidos(LeaguePlayerSurnameTxt.getText());
            selectedPlayer.getInformacionPersonal().setPais(LeaguePlayerCountryCmb.getValue());
            selectedPlayer.setNickname(LeaguePlayerNickTxt.getText());
            selectedPlayer.setPosicion(LeaguePlayerPositionCmb.getValue());
            selectedPlayer.setEarlyShotcaller(LeaguePlayerEarlyShtCllrChk.isSelected());
            selectedPlayer.setLateShotcaller(LeaguePlayerLateShtCllrChk.isSelected());

            List<Equipo> equipos = Conection_App.getEquipos(connection);
            for (Equipo equipo : equipos) {
                if (equipo.getNombre().equals(LeaguePlayerTeamCmb.getValue())) {
                    selectedPlayer.setEquipo(equipo);
                    break;
                }
            }

            Conection_App.updateLolPlayer(selectedPlayer);
            Conection_App.cerrarConexion(connection);
            loadLolData();
            limpiarCamposLeaguePlayer();
            System.out.println("Jugador actualizado correctamente.");
        } else {
            System.out.println("No se pudo establecer la conexión a la base de datos.");
        }
    }


    @FXML
    private void deleteLolPlayer(ActionEvent event) {
        LolPlayer selectedPlayer = LolPlayerTableView.getSelectionModel().getSelectedItem();
        if (selectedPlayer == null) {
            System.out.println("Selecciona un jugador para eliminar.");
            return;
        }

        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            Conection_App.deleteLolPlayerForID(selectedPlayer.getId_jugador());
            Conection_App.cerrarConexion(connection);
            loadLolData();
            limpiarCamposLeaguePlayer();
            System.out.println("Jugador eliminado correctamente.");
        } else {
            System.out.println("No se pudo establecer la conexión a la base de datos.");
        }
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
