package se.iths.javafx.colorpicker.colorpicker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class Square extends Shape{

    private double size;

    public Square(Color color, double x, double y, double size) {
        super(color, x, y);
        this.size = size;
    }

    @Override
    public void draw(GraphicsContext gc) {
        double halfSize = size * 0.5;
        gc.setFill(this.getColor());
        gc.fillRect(getX() - halfSize, getY() - halfSize, size, size);
    }

    @Override
    public boolean isInside(double x, double y) {
        double halfSize = size * 0.5;
        if (x >= getX()- halfSize &&         // right of the left edge AND
                x <= (getX()- halfSize) + size &&    // left of the right edge AND
                y >= getY() - halfSize &&         // below the top AND
                y <= (getY() - halfSize)+ size) {    // above the bottom
            return true;
        }
        return false;
    }
}
