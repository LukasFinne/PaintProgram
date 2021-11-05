package se.iths.javafx.colorpicker.colorpicker;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
            model.sendToServer(shape);
        }
    }

    private void circleSelected(MouseEvent event) {
        if (circle.isSelected() && !event.getButton().name().equals("SECONDARY") && !event.getButton().name().equals("MIDDLE")) {
            Shape shape = Shapes.circleOf(event.getX(), event.getY(), model.getSize(), model.getColor());

            // model.setX(Math.random()*100);
            //  model.setY(Math.random()*100);
            model.shapes.add(shape);
            model.sendToServer(shape);
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

    private void testRedo() {

    }

    //kasnke inte behöver lägga till något i redo stacken när man klickar istället lägg till när jag tar bort
    private void testUndo() {
        if (model.shapes.size() - 1 >= 0)
            model.undo.addLast(() -> model.shapes.remove(model.shapes.size() - 1));

        Shape redo = model.shapes.get(model.shapes.size() - 1);
        model.redo.addLast(() -> model.shapes.add(redo));

    }

    public void redo() {

        model.redo();
        draw();
    }


    private void numericOnly(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("\\d*")) {
            size.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }

    @FXML
    public void connect() {
        model.connect();
    }

    private String format(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    public String toHexString(Color value) {
        return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()) + format(value.getOpacity()))
                .toUpperCase();
    }

    public void SaveToSVG(ActionEvent actionEvent) throws IOException {

        String homePath = System.getProperty("user.home");
        Path svgPath = Path.of(homePath, "circle.SVG");
        StringBuilder svg = new StringBuilder("<svg version=\"");
        svg.append(1.1).append("\" xmlns=\"http://www.w3.org/2000/svg\"").append(" height=\"").append(canvas.getHeight()).append("\" width=\"").append(canvas.getWidth()).append("\">");
        svgShapes(svg);
        svg.append("</svg>");
        Files.writeString(svgPath, svg.toString());
    }

    private void svgShapes(StringBuilder svg) {
        for (var shape : model.shapes) {
            if (shape instanceof Square) {
                svg.append("<rect x=\"").append(shape.getX()).append("\" y=\"")
                        .append(shape.getY())
                        .append("\" width=\"").append(model.getSize()).append("\" height=\"").append(model.getSize()).append("\" fill=\"").append(toHexString(shape.getColor())).append("\" />");
            } else {
                svg.append("<circle cx=\"").append(shape.getX()).append("\" cy=\"")
                        .append(shape.getY())
                        .append("\" r=\"").append(model.getSize()).append("\" fill=\"").append(toHexString(shape.getColor())).append("\" />");
            }
        }
    }

}