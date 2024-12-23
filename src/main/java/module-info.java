module edu.badpals.proyectoad3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.badpals.proyectoad3 to javafx.fxml;
    exports edu.badpals.proyectoad3;
}