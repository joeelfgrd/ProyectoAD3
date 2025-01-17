package edu.badpals.proyectoad3.controller;

import edu.badpals.proyectoad3.DAO.ConnectionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {


    @FXML
    private Button createDBBtn;

    @FXML
    private Button toMainMenuBtn;


    @FXML
    protected void onHelloButtonClick() throws Exception {
        ConnectionDAO.resetDatabase();
    }

    @FXML
    public void toMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/badpals/proyectoad3/MainView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}