package se.iths.javafx.colorpicker.colorpicker;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class HelloController {
    Model model;


    public Canvas canvas;
    @FXML
    private Button circle;
    @FXML
    private Button rectangle;
    @FXML
    private Button square;
    @FXML
    public ColorPicker colorPicker;

    public HelloController() {
    }

    public HelloController(Model model) {
        this.model = model;
    }

    public void initialize() {
        model = new Model();
        model.setColor(Color.BLACK);
        colorPicker.valueProperty().bindBidirectional(model.colorProperty());
        canvas.widthProperty().addListener(observable -> draw());
        canvas.heightProperty().addListener(observable -> draw());
    }

    private void draw() {
        var gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (var shape : model.shapes) {
            gc.setFill(shape.getColor());
            gc.fillRect(shape.getX(), shape.getY(), 25, 25);
        }
    }

    public void canvasClicked(MouseEvent event) {
        model.shapes.add(new Shape(model.getColor(), event.getX(), event.getY()));
        draw();
    }

    public void rectangle(MouseEvent event) {
        model.shapes.add(new Rectangle(model.getColor(), event.getX(), event.getY()));
        draw();
    }
}