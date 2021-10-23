package se.iths.javafx.colorpicker.colorpicker;

import javafx.scene.paint.Color;

public class Shape {
    private Color color;
    private double x;
    private double y;

    public Shape(Color color, double x, double y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public Shape setColor(Color color) {
        this.color = color;
        return this;
    }

    public double getX() {
        return x;
    }

    public Shape setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return y;
    }

    public Shape setY(double y) {
        this.y = y;
        return this;
    }
}
