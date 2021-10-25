package se.iths.javafx.colorpicker.colorpicker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract sealed class Shape permits Circle, Square {
    private Color color;
    private String name;
    private int height;
    private int width;
    private double x;


    private double y;

    public Shape(Color color, double x, double y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public abstract void draw(GraphicsContext gc);

    public abstract boolean isInside(double x, double y);

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
