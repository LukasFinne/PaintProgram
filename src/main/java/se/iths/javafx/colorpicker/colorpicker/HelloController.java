package se.iths.javafx.colorpicker.colorpicker;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloController {

    private Double size;

    Model model;

    @FXML
    public Label currentTool;

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
        var gc = canvas.getGraphicsContext2D();
        if(square.isSelected() && !event.getButton().name().equals("SECONDARY"))
            model.shapes.add(Shapes.rectangleOf(  event.getX(), event.getY(), size,model.getColor()));
        if (circle.isSelected() && !event.getButton().name().equals("SECONDARY") )
            model.shapes.add(Shapes.circleOf( event.getX(), event.getY(), size, model.getColor() ));
        if(event.getButton().name().equals("SECONDARY")){
           model.shapes.stream()
                    .filter(shape -> shape.isInside(event.getX(),event.getY()))
                    .findFirst().ifPresent(shape -> shape.setColor(Color.RED));
        }
        draw();
    }

    public void newWindow(){
        Stage newWindow = new Stage();
        newWindow.setTitle("Shape");

        Label title = new Label("Select what size of shape you want! ");
        TextField size = new TextField("Enter your desired size!");
        Button button = new Button("OK");
        button.setOnAction(event -> {
           this.size = Double.parseDouble(size.getText());
           newWindow.close();
        });
        VBox container = new VBox(title, size, button);

        container.setSpacing(15);
        container.setPadding(new Insets(25));
        container.setAlignment(Pos.CENTER);

        newWindow.setScene(new Scene(container));

        newWindow.show();
    }


}