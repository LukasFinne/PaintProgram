module se.iths.javafx.colorpicker.colorpicker {
    requires javafx.controls;
    requires javafx.fxml;

    opens se.iths.javafx.colorpicker.colorpicker to javafx.fxml;
    exports se.iths.javafx.colorpicker.colorpicker;
    exports se.iths.javafx.colorpicker.colorpicker.Shapes;
    opens se.iths.javafx.colorpicker.colorpicker.Shapes to javafx.fxml;
}