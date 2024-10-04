module lk.udu.ijse.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;

    opens lk.udu.ijse.demo.dto.tm to javafx.base;
    opens lk.udu.ijse.demo.controller to javafx.fxml;
    exports lk.udu.ijse.demo;
}