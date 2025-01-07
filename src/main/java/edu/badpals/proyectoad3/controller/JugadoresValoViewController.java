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
    public Button CreateValoPlayerBtn;
    @FXML
    public Button UpdateValoPlayerBtn;
    @FXML
    public Button DeleteValoPlayerBtn;
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
    private ComboBox<String> ValoPlayerRolCmb;

    @FXML
    private ComboBox<String> ValoPlayerTeamCmb;

    @FXML
    private ComboBox<String> ValoPlayerCountryCmb;

    @FXML
    private ComboBox<String> ValoPlayerAgentCmb;

    @FXML
    private TextField ValoPlayerNickTxt;

    @FXML
    private CheckBox IGLCheckbox;

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
        colName.setCellValueFactory(new PropertyValueFactory<>("nombre")); // Se refiere a getNombre() en ValorantPlayer
        colSurname.setCellValueFactory(new PropertyValueFactory<>("apellidos")); // Se refiere a getApellidos() en ValorantPlayer
        colCountry.setCellValueFactory(new PropertyValueFactory<>("pais")); // Se refiere a getPais() en ValorantPlayer
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
            Conection_App.cerrarConexion(connection);
        } else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void initialize() {
        setCells();
        loadData();
        List<String> lista = Conection_App.returnAllTeams(conectionApp.crearConexion());
        ValoPlayerRolCmb.getItems().addAll("Duelista", "Iniciador", "Controlador", "Centinela");
        ValoPlayerCountryCmb.getItems().addAll("Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo (Congo-Brazzaville)", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czechia (Czech Republic)", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini (fmr. Swaziland)", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Holy See", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea (North)", "Korea (South)", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar (Burma)", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau", "Palestine State", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States of America", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe");
        ValoPlayerTeamCmb.getItems().addAll(lista);
        ValoPlayerAgentCmb.getItems().addAll("Astra", "Breach", "Brimstone", "Chamber", "Cypher", "Fade", "Gekko", "Harbor", "Jett", "Killjoy", "KAY/O", "Neon", "Omen", "Phoenix", "Raze", "Reyna", "Sage", "Skye", "Sova", "Tejo", "Viper", "Vyse", "Yoru");

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

            if (jugadorSeleccionado.getEquipo() != null) {
                ValoPlayerTeamCmb.setValue(jugadorSeleccionado.getEquipoNombre());
            } else {
                ValoPlayerTeamCmb.setValue("No asignado");
            }

            ValoPlayerRolCmb.setValue(jugadorSeleccionado.getRol());
            ValoPlayerNickTxt.setText(jugadorSeleccionado.getNickname());
            ValoPlayerAgentCmb.setValue(jugadorSeleccionado.getAgente());
            IGLCheckbox.setSelected(jugadorSeleccionado.isIGL());
        }
    }

}