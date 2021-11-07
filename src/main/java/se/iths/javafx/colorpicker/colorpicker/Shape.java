package se.iths.javafx.colorpicker.colorpicker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayDeque;

public abstract class Shape implements ConvertToSVG {
    private Color color;
    private double x;
    private double y;
    private double size;
    // private double prevSize;

    ArrayDeque<Color> prevColor = new ArrayDeque<>();
    ArrayDeque<Double> prevSize = new ArrayDeque<>();


    public Shape(Color color, double x, double y, double size) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public abstract void draw(GraphicsContext gc);

    public abstract boolean isInside(double x, double y);

    public double getPrevSize() {
        return prevSize.removeLast();
    }

    public double getSize() {
        return size;
    }

    public void setPrevSize(double size) {
        this.size = size;
    }

    public void setSize(double size) {
        prevSize.addLast(this.size);
        this.size = size;
    }

    public Color getPrevColor() {
        return prevColor.removeLast();
    }

    public Color getColor() {
        return color;
    }

    public void setPrevColor(Color color) {
        this.color = color;
    }

    public void setColor(Color color) {
        prevColor.addLast(this.color);
        this.color = color;
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

    private String format(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    public String toHexString(Color value) {
        return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()) + format(value.getOpacity()))
                .toUpperCase();
    }


}
