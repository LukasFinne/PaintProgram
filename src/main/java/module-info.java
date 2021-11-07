module se.iths.javafx.colorpicker.colorpicker {
    requires javafx.controls;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;


    opens se.iths.javafx.colorpicker.colorpicker to javafx.fxml;
    exports se.iths.javafx.colorpicker.colorpicker;
    opens se.iths.javafx.colorpicker.colorpicker.Shapes to javafx.fxml;
    exports se.iths.javafx.colorpicker.colorpicker.Shapes;

}