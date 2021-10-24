package se.iths.javafx.colorpicker.colorpicker;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloController {

    private int height;
    private int width;

    Model model;

    @FXML
    public Label currentTool;

    public Canvas canvas;
    @FXML
    private RadioButton circle;
    @FXML
    private RadioButton rectangle;
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
        rectangle.setToggleGroup(model.toggleGroup);
        circle.setToggleGroup(model.toggleGroup);
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
                    gc.fillOval(shape.getX(),shape.getY(), shape.getHeight(), shape.getWidth());
                if(shape.getName().equals("R"))
                    gc.fillRect(shape.getX(),shape.getY(), shape.getHeight(), shape.getWidth());
            }
    }

    public void canvasClicked(MouseEvent event) {
        var gc = canvas.getGraphicsContext2D();
        if(rectangle.isSelected())
            model.shapes.add(new Rectangle(model.getColor(), event.getX(), event.getY(), "R", height, width));
        if(circle.isSelected())
            model.shapes.add(new Circle(model.getColor(), event.getX(), event.getY(), "C", height, width));
        draw();
    }

    public void newWindow(){
        Stage newWindow = new Stage();
        newWindow.setTitle("Shape");

        Label title = new Label("Select what size of shape you want! ");
        TextField height = new TextField("Enter the width");
        TextField width = new TextField("Enter the height");
        Button button = new Button("OK");
        button.setOnAction(event -> {
           this.width = Integer.parseInt(width.getText());
           this.height = Integer.parseInt(height.getText());
           newWindow.close();
        });
        VBox container = new VBox(title, height, width, button);

        container.setSpacing(15);
        container.setPadding(new Insets(25));
        container.setAlignment(Pos.CENTER);

        newWindow.setScene(new Scene(container));

        newWindow.show();
    }


}