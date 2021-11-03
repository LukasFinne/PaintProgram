package se.iths.javafx.colorpicker.colorpicker;

import javafx.beans.property.*;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Model {

    private final BooleanProperty inColor;
    private final ObjectProperty<Color> color;
    private ObjectProperty<Color> prevColor;
    private final DoubleProperty size;
    private DoubleProperty prevSize;


    List<Shape> shapes = new ArrayList<>();
    ToggleGroup toggleGroup = new ToggleGroup();
    Deque<Command> undo = new ArrayDeque<>();

    //redo code
    Deque<Command> redo = new ArrayDeque<>();

    public Model() {
        this.inColor = new SimpleBooleanProperty();
        this.color = new SimpleObjectProperty<>(Color.BLACK);
        this.prevColor = new SimpleObjectProperty<>();
        this.size = new SimpleDoubleProperty();
        this.prevSize = new SimpleDoubleProperty();
    }


    public DoubleProperty sizeProperty() {
        return size;
    }

    public Double getPrevSize() {
        return prevSize.get();
    }

    public Double getSize() {
        return size.get();
    }

    public void setSize(double size) {
        prevSize = this.size;
        this.size.set(size);
    }

    public void undo() {
        if (undo.isEmpty()) {
            return;
        }

        Command command = undo.removeLast();
        command.execute();
    }

    //redo code
    public void redo() {
        if (redo.isEmpty()) {
            return;
        }
        Command command = redo.removeFirst();
        command.execute();
    }


    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public Color getPrevColor() {
        return prevColor.get();
    }

    public Color getColor() {
        return color.get();
    }

    public void setColor(Color color) {
        prevColor = this.color;
        this.color.set(color);
    }

    public boolean isInColor() {
        return inColor.get();
    }

    public BooleanProperty inColorProperty() {
        return inColor;
    }

    public void setInColor(boolean inColor) {
        this.inColor.set(inColor);
    }



}