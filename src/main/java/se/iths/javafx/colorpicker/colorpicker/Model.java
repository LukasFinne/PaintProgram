package se.iths.javafx.colorpicker.colorpicker;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {

    private final BooleanProperty inColor;
    private final ObjectProperty<Color> color;
    private ObjectProperty<Color> prevColor;
    private final DoubleProperty size;
    private DoubleProperty prevSize;



    ObservableList<Shape> shapes = FXCollections.observableArrayList();
    ToggleGroup toggleGroup = new ToggleGroup();
    Deque<Command> undo = new ArrayDeque<>();

    //redo code
    Deque<Command> redo = new ArrayDeque<>();


    public Model() {
        this.inColor = new SimpleBooleanProperty();
        this.color = new SimpleObjectProperty<>(Color.BLACK);
        this.prevColor = new SimpleObjectProperty<>();
        this.size = new SimpleDoubleProperty(20);
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
        if (undo.isEmpty())
            return;

        redo.addLast(redo.getLast());
        Command command = undo.removeLast();
        command.execute();
    }


    public void redo() {
        if (redo.isEmpty())
            return;

        undo.addLast(undo.getLast());
        Command command = redo.removeLast();
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



    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    public BooleanProperty connected = new SimpleBooleanProperty();
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void connect() {
        if (connected.get())
            return;
        try {
            socket = new Socket("localhost", 8000);
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);


            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            connected.set(true);
            System.out.println("Connected to server");

            executorService.submit(this::networkHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToServer(Shape shape) {
        if (connected.get()) {
            writer.println("Created a new shape with coords, x:" + shape.getX() + " y:" + shape.getY());
        }
    }

    private void networkHandler() {
        try {
            while (true) {
                String line = reader.readLine();    // reads a line of text
                System.out.println(line);
                Platform.runLater(() -> {
                    Shape shape = Shapes.circleOf(10,20,  getSize(), Color.AQUA);
                    shapes.add(shape);
                });
            }
        } catch (IOException e) {
            System.out.println("I/O error. Disconnected.");
            Platform.runLater(() -> connected.set(false));
        }
    }
}





