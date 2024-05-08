module main.practicasempresarialessafa {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens pages to javafx.fxml;
    exports core.apps;
    opens core.apps to javafx.fxml;
    exports core.controllers;
    opens core.controllers to javafx.fxml;
}