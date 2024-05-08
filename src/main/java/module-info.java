module main.practicasempresarialessafa {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens main.practicasempresarialessafa to javafx.fxml;
    exports main.practicasempresarialessafa;
}