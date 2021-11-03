package se.iths.javafx.colorpicker.colorpicker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    private Color color;
    private double x;
    private double y;
    private double size;
    private double prevSize;


    public Shape(Color color, double x, double y, double size) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public abstract void draw(GraphicsContext gc);

    public abstract boolean isInside(double x, double y);


    public double getPrevSize() {
        return prevSize;
    }

    public double getSize() {
        return size;
    }

    public Shape setSize(double size) {
        prevSize = this.size;
        this.size = size;
        return this;
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
