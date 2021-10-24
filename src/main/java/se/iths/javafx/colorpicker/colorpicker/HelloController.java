package se.iths.javafx.colorpicker.colorpicker;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class HelloController {
    Model model;


    public Canvas canvas;
    @FXML
    private Button circle;
    @FXML
    private ToggleButton rectangle;
    @FXML
    private Button square;
    @FXML
    public ColorPicker colorPicker;

    private static boolean rectangleShape;

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
                if(shape.getName().equals("C"))
                    gc.fillOval(shape.getX(),shape.getY(), 25 ,25);
                if(shape.getName().equals("R"))
                    gc.fillRect(shape.getX(),shape.getY(), 25 ,25);
            }
    }

    public void canvasClicked(MouseEvent event) {
        var gc = canvas.getGraphicsContext2D();
        if(rectangle.isSelected())
            model.shapes.add(new Rectangle(model.getColor(), event.getX(), event.getY(), "R"));
        else
            model.shapes.add(new Circle(model.getColor(), event.getX(), event.getY(), "C"));
        draw();
    }

    public void rectangle(MouseEvent event) {
        rectangleShape = true;
        System.out.println("test");
    }

    public void release(MouseEvent event) {
        rectangleShape = false;
    }
}