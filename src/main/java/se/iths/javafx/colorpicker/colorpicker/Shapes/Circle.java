package se.iths.javafx.colorpicker.colorpicker.Shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.iths.javafx.colorpicker.colorpicker.Shape;

public final class Circle extends Shape {


    public Circle(Color color, double x, double y, double radius) {
        super(color, x, y, radius);

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(this.getColor());
        gc.fillOval(getX() - this.getSize(), getY() - this.getSize(), 2 * this.getSize(), 2 * this.getSize());
    }

    @Override
    public boolean isInside(double x, double y) {
        double dx = x - getX();
        double dy = y - getY();

        double distanceFromCircleCenterSquared = dx * dx + dy * dy;

        return distanceFromCircleCenterSquared < this.getSize() * this.getSize();
    }

    @Override
    public void convert(StringBuilder svg) {
        svg.append("<circle cx=\"").append(getX()).append("\" cy=\"")
                .append(getY())
                .append("\" r=\"").append(getSize()).append("\" fill=\"").append(toHexString(getColor())).append("\" />");
    }
}
