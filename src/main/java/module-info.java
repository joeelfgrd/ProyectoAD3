module edu.badpals.proyectoad3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;


    opens edu.badpals.proyectoad3 to javafx.fxml;
    exports edu.badpals.proyectoad3;
}