package se.iths.javafx.colorpicker.colorpicker;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloControllerTest {

    private HelloController helloController;
    static int total = 0;

    @BeforeEach
    void setUp() {
        helloController = new HelloController(new Model());
    }

    @Test
    void setColorTest() {
        helloController.model.setColor(Color.BLACK);

        assertEquals(Color.BLACK, helloController.model.getColor());
    }


    @Test
    void setSizeOnShapesTest() {
        helloController.model.setSize(40);

        int expectedResult = 40;

        assertEquals(expectedResult,helloController.model.getSize());
    }

    @Test
    void undoTest() {
        total += 1;
        helloController.model.undo.addLast(() -> total -= 1);
        helloController.model.undo();
        assertEquals(0, total);

    }


}