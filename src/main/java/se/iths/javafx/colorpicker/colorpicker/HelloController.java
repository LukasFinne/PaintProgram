package se.iths.javafx.colorpicker.colorpicker;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HelloController {

    Model model;

    @FXML
    public Button undo;
    @FXML
    public Button redo;
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

        model.shapes.addListener((ListChangeListener<Shape>) change -> {
            draw();
        });


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
        squareSelected(event);
        circleSelected(event);
        testUndo();
        rightMouseButtonClicked(event);
        middleButtonClicked(event);
        draw();
    }

    private void squareSelected(MouseEvent event) {
        if (square.isSelected() && !event.getButton().name().equals("SECONDARY") && !event.getButton().name().equals("MIDDLE")) {
            Shape shape = Shapes.rectangleOf(event.getX(), event.getY(), model.getSize(), model.getColor());
            model.shapes.add(shape);
        }
    }

    private void circleSelected(MouseEvent event) {
        if (circle.isSelected() && !event.getButton().name().equals("SECONDARY") && !event.getButton().name().equals("MIDDLE")) {
            Shape shape = Shapes.circleOf(event.getX(), event.getY(), model.getSize(), model.getColor());
            model.shapes.add(shape);
        }
    }

    private void rightMouseButtonClicked(MouseEvent event) {
        if (event.getButton().name().equals("SECONDARY")) {
            model.shapes.stream()
                    .filter(shape -> shape.isInside(event.getX(), event.getY()))
                    .findFirst().ifPresent(shape -> shape.setColor(model.getColor()));

            model.undo.addLast(() -> model.shapes.stream()
                    .filter(shape -> shape.isInside(event.getX(), event.getY()))
                    .findFirst().ifPresent(shape -> shape.setColor(model.getPrevColor())));
        }
    }

    private void middleButtonClicked(MouseEvent event) {
        if (event.getButton().name().equals("MIDDLE")) {
            model.shapes.stream()
                    .filter(shape -> shape.isInside(event.getX(), event.getY()))
                    .findFirst().ifPresent(shape -> shape.setSize(model.getSize()));
            model.undo.addLast(() -> model.shapes.stream()
                    .filter(shape -> shape.isInside(event.getX(), event.getY()))
                    .findFirst().ifPresent(shape -> shape.setSize(shape.getPrevSize())));
        }

    }

    public void undo() {
        model.undo();
        draw();
    }

    private void testUndo() {
        if (model.shapes.size() - 1 >= 0)
            model.undo.addLast(() -> model.shapes.remove(model.shapes.size() - 1));
    }

    private void numericOnly(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("\\d*")) {
            size.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }

    public void SaveToSVG() throws IOException {

        String homePath = System.getProperty("user.home");
        Path svgPath = Path.of(homePath, "shapes.SVG");
        StringBuilder svg = new StringBuilder();
        svg.append("<svg version=\"").append(1.1).append("\" xmlns=\"http://www.w3.org/2000/svg\"").append(" height=\"").append(canvas.getHeight()).append("\" width=\"").append(canvas.getWidth()).append("\">");
        model.svgShapes(svg);
        svg.append("</svg>");
        Files.writeString(svgPath, svg.toString());
    }



}