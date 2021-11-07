package se.iths.javafx.colorpicker.colorpicker;

@FunctionalInterface
public interface ConvertToSVG {
    void convert(StringBuilder svg);
}
