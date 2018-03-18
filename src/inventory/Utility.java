package inventory;

import javafx.scene.control.TextField;

import java.text.DecimalFormat;

public interface Utility {

    default int toInteger(String price) {
        return Integer.parseInt(price);
    }

    default Double toDouble(String price) {
        return Double.parseDouble(price);
    }

    default String toPriceFormat(Double price) {
        String pattern = "############.##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(price);
    }

    default void restrictInput(TextField tf, String regex) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(regex)) {
                tf.setText(oldValue);
            }
        });
    }

}