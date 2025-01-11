package edu.badpals.proyectoad3.controller;

import edu.badpals.proyectoad3.DAO.EquipoDAO;
import edu.badpals.proyectoad3.DAO.JugadorValoDAO;
import edu.badpals.proyectoad3.DAO.ConnectionDAO;
import edu.badpals.proyectoad3.model.Equipo;
import edu.badpals.proyectoad3.model.InformacionPersonal;
import edu.badpals.proyectoad3.model.ValorantPlayer;
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

    private final ConnectionDAO conectionApp = new ConnectionDAO();

    //  Metodos iniciales para cargar los datos de la tabla y los combobox y la tabla

    @FXML
    public void initialize() {
        setCells();
        loadValoDataToTable();
        List<String> lista = EquipoDAO.returnAllTeams(conectionApp.crearConexion());
        ValoPlayerRolCmb.getItems().addAll("Duelista", "Iniciador", "Controlador", "Centinela");
        ValoPlayerCountryCmb.getItems().addAll("Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo (Congo-Brazzaville)", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czechia (Czech Republic)", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini (fmr. Swaziland)", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Holy See", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea (North)", "Korea (South)", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar (Burma)", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau", "Palestine State", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States of America", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe");
        ValoPlayerTeamCmb.getItems().addAll(lista);
        ValoPlayerAgentCmb.getItems().addAll("Astra", "Breach", "Brimstone", "Chamber", "Cypher", "Fade", "Gekko", "Harbor", "Jett", "Killjoy", "KAY/O", "Neon", "Omen", "Phoenix", "Raze", "Reyna", "Sage", "Skye", "Sova", "Tejo", "Viper", "Vyse", "Yoru");
        ValoPlayerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ValoPlayerTableView.setOnMouseClicked(event -> {
            if (!ValoPlayerTableView.getSelectionModel().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                cargarDatosJugadorDeTablaAceldas();
            }
        });
        if (IGLCheckbox != null) {
            IGLCheckbox.setSelected(true);
        } else {
            System.out.println("CheckBox no está inicializado");
        }
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

    //--------CRUD ValorantPlayer--------

    @FXML
    private void CreateValorantPlayer(ActionEvent event) {
        Connection connection = conectionApp.crearConexion();
        if (checkCamposVaciosValoPlayer()) {
            AlertasController.mostrarAdvertencia("Error", "Todos los campos deben estar completos.");
            return;
        }

        if (connection != null) {
            ValorantPlayer player = new ValorantPlayer();
            if (player.getInformacionPersonal() == null) {
                player.setInformacionPersonal(new InformacionPersonal());
            }
            cargarDatosJugadorDeTablaAceldas(player, connection);
            JugadorValoDAO.addValoPlayer(player);
            ConnectionDAO.cerrarConexion(connection);
            loadValoDataToTable();
            limpiarCamposValorantPlayer();
            AlertasController.mostrarInformacion("Éxito", "Jugador creado correctamente.");
        } else {
            AlertasController.mostrarError("Error", "No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void actualizarValorantPlayer() {
        ValorantPlayer playerSeleccionado = ValoPlayerTableView.getSelectionModel().getSelectedItem();
        if (playerSeleccionado == null) {
            AlertasController.mostrarAdvertencia("Error", "Debe seleccionar un jugador disponible en la base de datos.");
            return;
        }
        if (checkCamposVaciosValoPlayer()) {
            AlertasController.mostrarAdvertencia("Error", "Todos los campos deben estar completos.");
            return;
        }

        try {
            cargarDatosJugadorDeTablaAceldas(playerSeleccionado, conectionApp.crearConexion());
            JugadorValoDAO.updateValoPlayer(playerSeleccionado);
            loadValoDataToTable();
            limpiarCamposValorantPlayer();

            AlertasController.mostrarInformacion("Éxito", "Jugador actualizado exitosamente.");
        } catch (Exception e) {
            AlertasController.mostrarError("Error", "No se ha podido actualizar al jugador en la base de datos");
        }
    }

    @FXML
    private void DeleteValorantPlayer(ActionEvent event) {
        ValorantPlayer playerSeleccionado = ValoPlayerTableView.getSelectionModel().getSelectedItem();
        if (playerSeleccionado == null) {
            AlertasController.mostrarAdvertencia("Error", "Debe seleccionar un jugador para eliminar." );
            return;
        }

        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            JugadorValoDAO.deleteValoPlayerForID(playerSeleccionado.getId_jugador());
            ConnectionDAO.cerrarConexion(connection);
            loadValoDataToTable();
            AlertasController.mostrarInformacion("Éxito", "Jugador eliminado exitosamente." );
        } else {
            AlertasController.mostrarError("Error", "No se pudo establecer la conexión con la base de datos");
        }
    }

    //----Metodos auxiliares----

    private void limpiarCamposValorantPlayer() {
        ValoPlayerNickTxt.clear();
        ValoPlayerRolCmb.setValue(null);
        ValoPlayerAgentCmb.setValue(null);
        IGLCheckbox.setSelected(false);
        ValoPlayerNameTxt.clear();
        ValoPlayerSurnameTxt.clear();
        ValoPlayerCountryCmb.setValue(null);
        ValoPlayerTeamCmb.setValue(null);
        ValoPlayerTableView.getSelectionModel().clearSelection();
    }

    private void cargarDatosJugadorDeTablaAceldas(ValorantPlayer player, Connection connection) {
        player.getInformacionPersonal().setNombre(ValoPlayerNameTxt.getText());
        player.getInformacionPersonal().setApellidos(ValoPlayerSurnameTxt.getText());
        player.getInformacionPersonal().setPais(ValoPlayerCountryCmb.getValue());
        player.setNickname(ValoPlayerNickTxt.getText());
        player.setRol(ValoPlayerRolCmb.getValue());
        player.setAgente(ValoPlayerAgentCmb.getValue());
        player.setIGL(IGLCheckbox.isSelected());

        if (ValoPlayerTeamCmb.getValue() != null) {
            List<Equipo> equipos = EquipoDAO.getEquipos(connection);
            for (Equipo equipo : equipos) {
                if (equipo.getNombre().equals(ValoPlayerTeamCmb.getValue())) {
                    player.setEquipo(equipo);
                    break;
                }
            }
        }
    }

    @FXML
    public void loadValoDataToTable() {
        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            List<ValorantPlayer> valorantPlayers = JugadorValoDAO.getJugadorValorant(connection);
            ObservableList<ValorantPlayer> valorantPlayerObservableList = FXCollections.observableArrayList(valorantPlayers);
            ValoPlayerTableView.setItems(valorantPlayerObservableList);
            ConnectionDAO.cerrarConexion(connection);
        } else {
            AlertasController.mostrarError("Error", "No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void mostrarJugadoresValoPorEquipo() {
        // Obtener el nombre del equipo seleccionado en el ComboBox
        String equipoSeleccionado = ValoPlayerTeamCmb.getValue();

        if (equipoSeleccionado == null || equipoSeleccionado.isEmpty()) {
            // Si no se ha seleccionado ningún equipo, mostrar un mensaje de advertencia
            AlertasController.mostrarAdvertencia("Selección de equipo", "Por favor, selecciona un equipo.");
            return;
        }

        // Obtener la lista de jugadores para el equipo seleccionado
        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            List<ValorantPlayer> jugadoresPorEquipo = JugadorValoDAO.getJugadorValorantPorEquipo(equipoSeleccionado);

            // Verificar si se obtuvieron jugadores
            if (jugadoresPorEquipo.isEmpty()) {
                AlertasController.mostrarAdvertencia("Sin jugadores", "No se encontraron jugadores para el equipo " + equipoSeleccionado);
            } else {
                // Convertir la lista de jugadores a una lista observable para actualizar la TableView
                ObservableList<ValorantPlayer> jugadoresObservableList = FXCollections.observableArrayList(jugadoresPorEquipo);
                ValoPlayerTableView.setItems(jugadoresObservableList);
            }

            // Cerrar la conexión después de obtener los datos
            ConnectionDAO.cerrarConexion(connection);
        } else {
            AlertasController.mostrarError("Error de conexión", "No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void mostrarJugadoresValoPorPais() {
        // Obtener el país seleccionado en el ComboBox
        String paisSeleccionado = ValoPlayerCountryCmb.getValue();

        if (paisSeleccionado == null || paisSeleccionado.isEmpty()) {
            // Si no se ha seleccionado ningún país, mostrar un mensaje de advertencia
            AlertasController.mostrarAdvertencia("Selección de país", "Por favor, selecciona un país.");
            return;
        }

        // Obtener la lista de jugadores para el país seleccionado
        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            List<ValorantPlayer> jugadoresPorPais = JugadorValoDAO.getJugadorValorantPorPais(paisSeleccionado);

            // Verificar si se obtuvieron jugadores
            if (jugadoresPorPais.isEmpty()) {
                AlertasController.mostrarAdvertencia("Sin jugadores", "No se encontraron jugadores para el país " + paisSeleccionado);
            } else {
                // Convertir la lista de jugadores a una lista observable para actualizar la TableView
                ObservableList<ValorantPlayer> jugadoresObservableList = FXCollections.observableArrayList(jugadoresPorPais);
                ValoPlayerTableView.setItems(jugadoresObservableList);
            }

            // Cerrar la conexión después de obtener los datos
            ConnectionDAO.cerrarConexion(connection);
        } else {
            AlertasController.mostrarError("Error de conexión", "No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void mostrarJugadoresValoPorRol() {
        // Obtener el rol seleccionado en el ComboBox
        String rolSeleccionado = ValoPlayerRolCmb.getValue();

        if (rolSeleccionado == null || rolSeleccionado.isEmpty()) {
            // Si no se ha seleccionado ningún rol, mostrar un mensaje de advertencia
            AlertasController.mostrarAdvertencia("Selección de rol", "Por favor, selecciona un rol.");
            return;
        }

        // Obtener la lista de jugadores para el rol seleccionado
        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            List<ValorantPlayer> jugadoresPorRol = JugadorValoDAO.getJugadorValorantPorRol(rolSeleccionado);

            // Verificar si se obtuvieron jugadores
            if (jugadoresPorRol.isEmpty()) {
                AlertasController.mostrarAdvertencia("Sin jugadores", "No se encontraron jugadores para el rol " + rolSeleccionado);
            } else {
                ObservableList<ValorantPlayer> jugadoresObservableList = FXCollections.observableArrayList(jugadoresPorRol);
                ValoPlayerTableView.setItems(jugadoresObservableList);
            }

            // Cerrar la conexión después de obtener los datos
            ConnectionDAO.cerrarConexion(connection);
        } else {
            AlertasController.mostrarError("Error de conexión", "No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void searchValoPlayerByName() {
        String nombreNickname = ValoPlayerNickTxt.getText().trim();

        if (!nombreNickname.isEmpty()) {
            List<ValorantPlayer> jugadores = JugadorValoDAO.getJugadorPorNickname(nombreNickname);
            ObservableList<ValorantPlayer> equipoObservableList = FXCollections.observableArrayList(jugadores);
            ValoPlayerTableView.setItems(equipoObservableList);
        } else {
            AlertasController.mostrarAdvertencia("Campo vacío", "Debe ingresar un nickname para buscar.");
        }
    }



    private boolean checkCamposVaciosValoPlayer() {
        return ValoPlayerNameTxt.getText().isEmpty() || ValoPlayerSurnameTxt.getText().isEmpty() ||
                ValoPlayerNickTxt.getText().isEmpty() || ValoPlayerRolCmb.getValue() == null ||
                ValoPlayerCountryCmb.getValue() == null || ValoPlayerAgentCmb.getValue() == null;
    }


    private void cargarDatosJugadorDeTablaAceldas() {
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


    @FXML
    private void recargarCeldasYtabla() {
        limpiarCamposValorantPlayer();
        ValoPlayerTableView.refresh();
        loadValoDataToTable();
    }




}