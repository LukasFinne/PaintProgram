package se.iths.javafx.colorpicker.colorpicker;

import javafx.scene.paint.Color;

public class Shape {
    private Color color;
    private String name;
    private int height;
    private int width;
    private double x;


    private double y;

    public Shape(Color color, double x, double y, String name, int height, int width) {
        this.color = color;
        this.x = x;
        this.name = name;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public Color getColor() {
        return color;
    }
    public Shape setColor(Color color) {
        this.color = color;
        return this;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
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
