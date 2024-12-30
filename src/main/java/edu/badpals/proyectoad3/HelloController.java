package edu.badpals.proyectoad3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import edu.badpals.proyectoad3.model.Main;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        Main.generateDB();
    }
}