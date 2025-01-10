module edu.badpals.proyectoad3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.checkerframework.checker.qual;
    requires annotations;

    // Model package
    opens edu.badpals.proyectoad3.model to javafx.fxml, org.hibernate.orm.core;
    exports edu.badpals.proyectoad3.model;

    // Controller package
    opens edu.badpals.proyectoad3.controller to javafx.fxml;
    exports edu.badpals.proyectoad3.controller;

    // DAO package
    opens edu.badpals.proyectoad3.DAO to org.hibernate.orm.core;
    exports edu.badpals.proyectoad3.DAO;

    // Main package
    opens edu.badpals.proyectoad3 to javafx.fxml, org.hibernate.orm.core;
    exports edu.badpals.proyectoad3;
}
