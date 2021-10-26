package se.iths.javafx.colorpicker.colorpicker;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;

public class HelloController {

    Model model;

    @FXML
    public Button button;
    @FXML
    public TextField size;
    public Canvas canvas;
    @FXML
    private RadioButton circle;
    @FXML
    private RadioButton square;
    @FXML
    public ColorPicker colorPicker;

    public HelloController() {
    }

    public HelloController(Model model) {
        this.model = model;
    }

    public void initialize() {
        model = new Model();

        square.setToggleGroup(model.toggleGroup);
        circle.setToggleGroup(model.toggleGroup);
        colorPicker.valueProperty().bindBidirectional(model.colorProperty());
        size.textProperty().bindBidirectional(model.sizeProperty(), new NumberStringConverter());
        size.textProperty().addListener(this::numericOnly);
        canvas.widthProperty().addListener(observable -> draw());
        canvas.heightProperty().addListener(observable -> draw());
    }

    private void draw() {
        var gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (var shape : model.shapes) {
            gc.setFill(shape.getColor());
            shape.draw(gc);
        }
    }

    public void canvasClicked(MouseEvent event) {
        if (square.isSelected() && !event.getButton().name().equals("SECONDARY")) {
            model.shapes.add(Shapes.rectangleOf(event.getX(), event.getY(), model.sizeProperty().getValue(), model.getColor()));
            model.deque.addLast(() -> model.shapes.remove(model.shapes.size() -1));
        }
        if (circle.isSelected() && !event.getButton().name().equals("SECONDARY")) {
            model.shapes.add(Shapes.circleOf(event.getX(), event.getY(), model.sizeProperty().getValue(), model.getColor()));
            model.deque.addLast(() -> model.shapes.remove(model.shapes.size() -1));
        }

        if (event.getButton().name().equals("SECONDARY")) {
            model.shapes.stream()
                    .filter(shape -> shape.isInside(event.getX(), event.getY()))
                    .findFirst().ifPresent(shape -> shape.setColor(Color.RED));
        }
        draw();
    }

    public void undo(ActionEvent actionEvent) {
        if(model.deque.isEmpty())
            return;
        Command command = model.deque.removeLast();
        command.execute();
        draw();
    }

    private void numericOnly(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("\\d*")) {
            size.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }
}