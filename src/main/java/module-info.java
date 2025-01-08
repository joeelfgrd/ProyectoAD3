module edu.badpals.proyectoad3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;

    opens edu.badpals.proyectoad3 to javafx.fxml;
    exports edu.badpals.proyectoad3;
    exports edu.badpals.proyectoad3.model;
    opens edu.badpals.proyectoad3.model to javafx.fxml, org.hibernate.orm.core;
    exports edu.badpals.proyectoad3.model.entities;
    opens edu.badpals.proyectoad3.model.entities to javafx.fxml, org.hibernate.orm.core;
    exports edu.badpals.proyectoad3.controller;
    opens edu.badpals.proyectoad3.controller to javafx.fxml;
}