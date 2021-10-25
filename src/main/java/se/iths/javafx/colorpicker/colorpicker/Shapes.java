package se.iths.javafx.colorpicker.colorpicker;

import javafx.scene.paint.Color;

public class Shapes {
    public static Shape circleOf(double x, double y, double radius, Color color) {
        return new Circle(color, x, y, radius);
    }

    public static Shape rectangleOf(double x, double y, double size, Color color) {
        return new Square(color, x, y, size);
    }
}
