package edu.badpals.proyectoad3.controller;

import edu.badpals.proyectoad3.DAO.EquipoDAO;
import edu.badpals.proyectoad3.DAO.EquipoLigaDAO;
import edu.badpals.proyectoad3.DAO.LigasDAO;
import edu.badpals.proyectoad3.DAO.ConnectionDAO;
import edu.badpals.proyectoad3.model.Equipo;
import edu.badpals.proyectoad3.model.EquipoLiga;
import edu.badpals.proyectoad3.model.Liga;
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
    private DatePicker DateRegisterTxt;

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    private EntityManager em;

    private final ConnectionDAO conectionApp = new ConnectionDAO();

    private final Connection connection = conectionApp.crearConexion();
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
        Connection connection = conectionApp.crearConexion();
        List<EquipoLiga> registros = EquipoLigaDAO.getEquipoLiga(connection, em);
        ObservableList<EquipoLiga> registroObservableList = FXCollections.observableArrayList(registros);
        tableRegister.setItems(registroObservableList);
    }

    private void loadComboBoxes() {
        Connection connection = new ConnectionDAO().crearConexion();
        if (connection != null) {
            List<Equipo> equipos = EquipoDAO.getEquipos(connection);
            equipos.forEach(equipo -> SelectTeamCmb.getItems().add(equipo.getNombre()));
            List<Liga> ligas = LigasDAO.getLigas(connection);
            ligas.forEach(liga -> SelectLeagueCmb.getItems().add(liga.getNombre()));
            new ConnectionDAO().cerrarConexion(connection);
        }
    }

    private void cargarDatosRegistro() {
        EquipoLiga registroSeleccionado = tableRegister.getSelectionModel().getSelectedItem();

        if (registroSeleccionado != null) {
            SelectLeagueCmb.setValue(registroSeleccionado.getLiga().getNombre());
            SelectTeamCmb.setValue(registroSeleccionado.getEquipo().getNombre());
            DateRegisterTxt.setValue(registroSeleccionado.getFechaInscripcion());
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

    @FXML
    public void registrarEquipoEnLiga() {
        if (SelectTeamCmb.getValue() == null ||
                SelectLeagueCmb.getValue() == null || PriceRegisterTxt.getText().isEmpty() || DateRegisterTxt.getValue() == null) {
            AlertasController.mostrarAdvertencia("Error", "Todos los campos deben estar completos.");
            return;
        }
        try {
            EquipoLiga equipoliga = new EquipoLiga();

            equipoliga.setPrecioPlaza(Double.valueOf(PriceRegisterTxt.getText()));
            equipoliga.setFechaInscripcion(DateRegisterTxt.getValue());

            if (SelectTeamCmb.getValue() != null) {
                List<Equipo> equipos = EquipoDAO.getEquipos(conectionApp.crearConexion());
                for (Equipo equipo : equipos) {
                    if (equipo.getNombre().equals(SelectTeamCmb.getValue())) {
                        equipoliga.setEquipo(equipo);
                        break;
                    }
                }
            }

            if (SelectLeagueCmb.getValue() != null) {
                List<Liga> ligas = LigasDAO.getLigas(conectionApp.crearConexion());
                for (Liga liga : ligas) {
                    if (liga.getNombre().equals(SelectLeagueCmb.getValue())) {
                        equipoliga.setLiga(liga);
                        break;
                    }
                }
            }

            if (equipoliga.getEquipo() == null || equipoliga.getLiga() == null) {
                AlertasController.mostrarAdvertencia("Error", "No se pudo encontrar el equipo o la liga seleccionada.");
                return;
            }

            if (checkPlazaExiste(connection)) {
                AlertasController.mostrarError("Error", "Ya existe ese registro en la base de datos");
                return;
            }
            EquipoLigaDAO.registerTeam(equipoliga);
            loadData();
            limpiarCamposRegisterEquipoLiga();
            AlertasController.mostrarInformacion("Éxito", "Plaza asignada exitosamente.");

        } catch (Exception e) {
            AlertasController.mostrarError("Error", "Ocurrió un error al crear la plaza" );
        }
    }




    @FXML
    public void actualizarEquipoEnLiga() {
        EquipoLiga equipoLigaSeleccionado = tableRegister.getSelectionModel().getSelectedItem();
        if (equipoLigaSeleccionado == null) {
            AlertasController.mostrarAdvertencia("Error", "Debe seleccionar una plaza para actualizar.");
            return;
        }
        if (PriceRegisterTxt.getText().isEmpty() || SelectTeamCmb.getValue() == null ||
                SelectLeagueCmb.getValue() == null || DateRegisterTxt.getValue() == null) {
            AlertasController.mostrarAdvertencia("Error", "Todos los campos deben estar completos.");
            return;
        }

        try {
            // Actualizamos los datos del objeto EquipoLiga con los nuevos valores
            equipoLigaSeleccionado.setPrecioPlaza(Double.valueOf(PriceRegisterTxt.getText()));
            equipoLigaSeleccionado.setFechaInscripcion(DateRegisterTxt.getValue());

            // Asignamos el equipo seleccionado
            if (SelectTeamCmb.getValue() != null) {
                List<Equipo> equipos = EquipoDAO.getEquipos(conectionApp.crearConexion());
                for (Equipo equipo : equipos) {
                    if (equipo.getNombre().equals(SelectTeamCmb.getValue())) {
                        equipoLigaSeleccionado.setEquipo(equipo);
                        break;
                    }
                }
            }

            // Asignamos la liga seleccionada
            if (SelectLeagueCmb.getValue() != null) {
                List<Liga> ligas = LigasDAO.getLigas(conectionApp.crearConexion());
                for (Liga liga : ligas) {
                    if (liga.getNombre().equals(SelectLeagueCmb.getValue())) {
                        equipoLigaSeleccionado.setLiga(liga);
                        break;
                    }
                }
            }

            // Verificamos si ya existe un registro con el mismo equipo y liga, pero con ID diferente
            if (checkPlazaYaExisteConDiferenteId(equipoLigaSeleccionado, connection)) {
                AlertasController.mostrarAdvertencia("Error", "Ya existe un registro con el mismo equipo y liga en la base de datos.");
                return;
            }

            // Si todo está correcto, actualizamos el registro
            EquipoLigaDAO.updateParticipation(equipoLigaSeleccionado);
            tableRegister.refresh();
            limpiarCamposRegisterEquipoLiga();
            loadData();

            AlertasController.mostrarInformacion("Éxito", "Plaza actualizado exitosamente.");
        } catch (Exception e) {
            AlertasController.mostrarError("Error", "Ocurrió un error al actualizar la plaza.");
        }
    }

    @FXML
    private void DeleteEquipoLiga(ActionEvent event) {
        EquipoLiga equipoLigaSeleccionado = tableRegister.getSelectionModel().getSelectedItem();
        if (equipoLigaSeleccionado == null) {
            AlertasController.mostrarAdvertencia("Error", "Debe seleccionar una plaza para eliminarla.");
            return;
        }

        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            EquipoLigaDAO.unregisterTeam(equipoLigaSeleccionado);
            ConnectionDAO.cerrarConexion(connection);
            loadData();
            AlertasController.mostrarInformacion("Éxito", "Plaza eliminado exitosamente.");
        } else {
            AlertasController.mostrarError("Error", "No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void mostrarEquipoLigaPorEquipo(ActionEvent event) {
        // Obtener el nombre del equipo seleccionado en el ComboBox
        String equipoNombre = SelectTeamCmb.getValue();

        if (equipoNombre != null) {
            // Obtener el equipo correspondiente a ese nombre
            Equipo equipo = null;
            List<Equipo> equipos = EquipoDAO.getEquipos(conectionApp.crearConexion());
            for (Equipo e : equipos) {
                if (e.getNombre().equals(equipoNombre)) {
                    equipo = e;
                    break;
                }
            }

            if (equipo != null) {
                // Obtener las ligas asociadas a este equipo
                List<EquipoLiga> equipoLigas = EquipoLigaDAO.getEquipoLigaPorEquipo(equipo.getIdEquipo());

                // Convertir la lista a ObservableList para mostrar en la TableView
                ObservableList<EquipoLiga> equipoLigasObservableList = FXCollections.observableArrayList(equipoLigas);

                // Asignar los elementos a la TableView para mostrar las ligas asociadas
                tableRegister.setItems(equipoLigasObservableList);
            } else {
                AlertasController.mostrarError("Error", "El equipo seleccionado no existe.");
            }
        } else {
            AlertasController.mostrarAdvertencia("Campo vacío", "Por favor, selecciona un equipo.");
        }
    }

    @FXML
    public void mostrarEquipoLigaPorLiga(ActionEvent event) {
        // Obtener el nombre de la liga seleccionada en el ComboBox
        String ligaNombre = SelectLeagueCmb.getValue();

        if (ligaNombre != null) {
            // Obtener la liga correspondiente a ese nombre
            Liga liga = null;
            List<Liga> ligas = LigasDAO.getLigas(conectionApp.crearConexion());
            for (Liga l : ligas) {
                if (l.getNombre().equals(ligaNombre)) {
                    liga = l;
                    break;
                }
            }

            if (liga != null) {
                // Obtener los equipos asociados a esta liga
                List<EquipoLiga> equipoLigas = EquipoLigaDAO.getEquipoLigaPorLiga(liga.getIdLiga());

                // Convertir la lista a ObservableList para mostrar en la TableView
                ObservableList<EquipoLiga> equipoLigasObservableList = FXCollections.observableArrayList(equipoLigas);

                // Asignar los elementos a la TableView para mostrar los equipos asociados
                tableRegister.setItems(equipoLigasObservableList);
            } else {
                AlertasController.mostrarError("Error", "La liga seleccionada no existe.");
            }
        } else {
            AlertasController.mostrarAdvertencia("Campo vacío", "Por favor, selecciona una liga.");
        }
    }


    private boolean checkPlazaExiste(Connection connection) {
        List<EquipoLiga> registros = EquipoLigaDAO.getEquipoLiga(connection, em);
        for (EquipoLiga registro : registros) {
            if (registro.getEquipo().getNombre().equalsIgnoreCase(SelectTeamCmb.getValue()) && registro.getLiga().getNombre().equalsIgnoreCase(SelectLeagueCmb.getValue()) ) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPlazaYaExisteConDiferenteId(EquipoLiga equipoLigaSeleccionado, Connection connection) {
        List<EquipoLiga> registros = EquipoLigaDAO.getEquipoLiga(connection, em);
        for (EquipoLiga registro : registros) {
            // Comprobamos si ya existe otro registro con el mismo equipo y liga pero con diferente ID
            if (registro.getEquipo().getNombre().equalsIgnoreCase(equipoLigaSeleccionado.getEquipo().getNombre()) &&
                    registro.getLiga().getNombre().equalsIgnoreCase(equipoLigaSeleccionado.getLiga().getNombre()) &&
                    !registro.getId().equals(equipoLigaSeleccionado.getId())) {
                return true;
            }
        }
        return false;
    }



    private void limpiarCamposRegisterEquipoLiga() {
        PriceRegisterTxt.clear();
        DateRegisterTxt.setValue(null);
        SelectTeamCmb.setValue(null);
        SelectLeagueCmb.setValue(null);
        tableRegister.getSelectionModel().clearSelection();
    }

    private Long getPlazaId(Connection connection) {
    List<EquipoLiga> registros = EquipoLigaDAO.getEquipoLiga(connection, em);
    for (EquipoLiga registro : registros) {
        if (registro.getEquipo().getNombre().equalsIgnoreCase(SelectTeamCmb.getValue()) &&
            registro.getLiga().getNombre().equalsIgnoreCase(SelectLeagueCmb.getValue())) {
            return registro.getId();
        }
    }
    return null;
}

    @FXML
    private void recargarCeldasYtabla() {
        limpiarCamposRegisterEquipoLiga();
        tableRegister.refresh();
        loadData();
    }

}