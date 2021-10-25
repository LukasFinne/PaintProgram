package se.iths.javafx.colorpicker.colorpicker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class Circle extends Shape{

    private double radius;
    public Circle(Color color, double x, double y, double radius) {
        super(color, x, y);
        this.radius = radius;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(this.getColor());
        gc.fillOval(getX()-radius, getY()-radius, 2*radius, 2*radius);
    }

    @Override
    public boolean isInside(double x, double y) {
        double dx = x - getX();
        double dy = y - getY();

        double distanceFromCircleCenterSquared = dx * dx + dy * dy;

        return distanceFromCircleCenterSquared < radius*radius;
    }

}
