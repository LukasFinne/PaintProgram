package se.iths.javafx.colorpicker.colorpicker;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Model {

    private final BooleanProperty inColor;
    private final ObjectProperty<Color> color;
    private final DoubleProperty size;


    List<Shape> shapes = new ArrayList<>();
    ToggleGroup toggleGroup = new ToggleGroup();
    Deque<Command> deque = new ArrayDeque<>();

    public Model() {
        this.inColor = new SimpleBooleanProperty();
        this.color = new SimpleObjectProperty<>(Color.BLACK);
        this.size = new SimpleDoubleProperty();
    }



    public DoubleProperty sizeProperty(){
        return size;
    }

    public Color getColor() {
        return color.get();
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public void setColor(Color color) {
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