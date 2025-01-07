package edu.badpals.proyectoad3.controller;

import edu.badpals.proyectoad3.model.Conection_App;
import edu.badpals.proyectoad3.model.entities.Equipo;
import edu.badpals.proyectoad3.model.entities.EquipoLiga;
import edu.badpals.proyectoad3.model.entities.Liga;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class RegisterViewController {

    @FXML
    private ComboBox<Equipo> SelectTeamCmb;

    @FXML
    private ComboBox<Liga> SelectLeagueCmb;

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
    public void initialize() {
        Connection connection = conectionApp.crearConexion();
        if (connection != null) {
            List<Equipo> equipos = Conection_App.returnAllTeams(connection);
            List<Liga> ligas = Conection_App.returnAllLeagues(connection);

            SelectTeamCmb.getItems().addAll(equipos);
            SelectLeagueCmb.getItems().addAll(ligas);

            conectionApp.cerrarConexion(connection);
        } else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
        }
    }

    @FXML
    public void createRegister() {
        Equipo selectedTeam = SelectTeamCmb.getSelectionModel().getSelectedItem();
        Liga selectedLeague = SelectLeagueCmb.getSelectionModel().getSelectedItem();
        String priceText = PriceRegisterTxt.getText();
        String dateText = DateRegisterTxt.getText();

        if (selectedTeam != null && selectedLeague != null && !priceText.isEmpty() && !dateText.isEmpty()) {
            try {
                LocalDate date = LocalDate.parse(dateText);
                Double price = Double.parseDouble(priceText);

                EquipoLiga newRegister = new EquipoLiga(null, selectedTeam, selectedLeague, date, price);

                Connection connection = conectionApp.crearConexion();
                if (connection != null) {
                    Conection_App.saveEquipoLiga(connection, newRegister);
                    conectionApp.cerrarConexion(connection);
                    System.out.println("Registro creado correctamente.");
                } else {
                    System.out.println("Error al conectar con la base de datos.");
                }
            } catch (Exception e) {
                System.out.println("Error al crear el registro: " + e.getMessage());
            }
        } else {
            System.out.println("Todos los campos deben estar llenos para crear un registro.");
        }
    }

    @FXML
    public void deleteRegister() {
        Equipo selectedTeam = SelectTeamCmb.getSelectionModel().getSelectedItem();
        Liga selectedLeague = SelectLeagueCmb.getSelectionModel().getSelectedItem();

        if (selectedTeam != null && selectedLeague != null) {
            Connection connection = conectionApp.crearConexion();
            if (connection != null) {
                boolean success = Conection_App.deleteEquipoLiga(connection, selectedTeam.getIdEquipo(), selectedLeague.getIdLiga());
                conectionApp.cerrarConexion(connection);

                if (success) {
                    System.out.println("Registro eliminado correctamente.");
                } else {
                    System.out.println("No se encontró el registro a eliminar.");
                }
            } else {
                System.out.println("Error al conectar con la base de datos.");
            }
        } else {
            System.out.println("Debe seleccionar un equipo y una liga para eliminar un registro.");
        }
    }

    @FXML
    public void updateRegister() {
        Equipo selectedTeam = SelectTeamCmb.getSelectionModel().getSelectedItem();
        Liga selectedLeague = SelectLeagueCmb.getSelectionModel().getSelectedItem();
        String priceText = PriceRegisterTxt.getText();
        String dateText = DateRegisterTxt.getText();

        if (selectedTeam != null && selectedLeague != null && !priceText.isEmpty() && !dateText.isEmpty()) {
            try {
                LocalDate date = LocalDate.parse(dateText);
                Double price = Double.parseDouble(priceText);

                Connection connection = conectionApp.crearConexion();
                if (connection != null) {
                    boolean success = Conection_App.updateEquipoLiga(connection, selectedTeam.getIdEquipo(), selectedLeague.getIdLiga(), date, price);
                    conectionApp.cerrarConexion(connection);

                    if (success) {
                        System.out.println("Registro actualizado correctamente.");
                    } else {
                        System.out.println("No se encontró el registro a actualizar.");
                    }
                } else {
                    System.out.println("Error al conectar con la base de datos.");
                }
            } catch (Exception e) {
                System.out.println("Error al actualizar el registro: " + e.getMessage());
            }
        } else {
            System.out.println("Todos los campos deben estar llenos para actualizar un registro.");
        }
    }
}
