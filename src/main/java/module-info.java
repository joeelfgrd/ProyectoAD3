module edu.badpals.proyectoad3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;


    opens edu.badpals.proyectoad3 to javafx.fxml;
    exports edu.badpals.proyectoad3;
    exports edu.badpals.proyectoad3.model;
    opens edu.badpals.proyectoad3.model to javafx.fxml;
}