package se.iths.javafx.colorpicker.colorpicker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class Square extends Shape{



    public Square(Color color, double x, double y, double size) {
        super(color, x, y, size);
    }

    @Override
    public void draw(GraphicsContext gc) {
        double halfSize = this.getSize() * 0.5;
        gc.setFill(this.getColor());
        gc.fillRect(getX() - halfSize, getY() - halfSize, this.getSize(), this.getSize());
    }

    @Override
    public boolean isInside(double x, double y) {
        double halfSize = this.getSize() * 0.5;
        if (x >= getX()- halfSize &&         // right of the left edge AND
                x <= (getX()- halfSize) + this.getSize() &&    // left of the right edge AND
                y >= getY() - halfSize &&         // below the top AND
                y <= (getY() - halfSize)+ this.getSize()) {    // above the bottom
            return true;
        }
        return false;
    }
}
