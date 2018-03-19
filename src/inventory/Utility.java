package inventory;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.text.DecimalFormat;
import java.util.Random;

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

    default int generatePartId() {
        Random rand = new Random();
        int rand1 = rand.nextInt(100);
        int rand2 = rand.nextInt(100);
        return rand1 * rand2;
    }

    default int generateProdId() {
        Random rand = new Random();
        int rand1 = rand.nextInt(100);
        int rand2 = rand.nextInt(100);
        return (int) rand1 * rand2;
    }

    /**
     * Validate for errors before creating new product object
     *
     * @param productName
     * @param inventoryLevel
     * @param productMin
     * @param productMax
     * @param productPrice
     * @param parts
     * @return
     */

    default void validateProduct(String productName, String inventoryLevel, String productMin,
                                 String productMax, String productPrice,
                                 ObservableList<Part> parts) throws Exception {

        // ensuring that a product must have a name, price, and inventory level (default 0)
        if (productName.length() == 0) {
            throw new Exception("Product must have a name.");
        }


        if (inventoryLevel.length() == 0) {
            throw new Exception("Inventory level is not empty.");
        }

        if (productPrice.length() == 0) {
            throw new Exception("Product price is not empty.");
        }

        int invLevel = toInteger(inventoryLevel);
        int min = toInteger(productMin);
        int max = toInteger(productMax);
        double price = toDouble(productPrice);

        // entering an inventory value greater than the maximum value for a product,
        // or lower than the minimum value for a part or product

        if (invLevel > max) {
            throw new Exception("Inventory level is greater than maximum value.");
        }

        if (invLevel < min) {
            throw new Exception("Inventory level is lesser than minimum value.");
        }

        // preventing the minimum field from having a value above the maximum field
        if (min > max) {
            throw new Exception("Minimum value is greater than maximum value.");
        }

        // preventing the maximum field from having a value below the minimum field
        if (max < min) {
            throw new Exception("Maximum value is greater than minimum value.");
        }

        // ensuring that a product must always have at least one part
        if (parts.size() < 1) {
            throw new Exception("Product must always have at least one part.");
        }

        // ensuring that the price of a product cannot be less than the cost of the parts
        double partPrices = 0.00;

        for (Part part : parts) {
            partPrices += part.getPrice();
        }

        if (price < partPrices) {
            throw new Exception("Product price cannot be less than the cost of parts.");
        }
    }

    /**
     * Validate for errors before creating new part object
     *
     * @param partName
     * @param inventoryLevel
     * @param partMin
     * @param partMax
     * @param partPrice
     */

    default void validatePart(String partName, String inventoryLevel, String partMin,
                              String partMax, String partPrice) throws Exception {


        // ensuring that a product must have a name, price, and inventory level (default 0)
        if (partName.length() == 0) {
            throw new Exception("Part must have a name.");
        }

        if (inventoryLevel.length() == 0) {
            throw new Exception("Inventory level is not empty.");
        }

        if (partPrice.length() == 0) {
            throw new Exception("Part price is not empty.");
        }

        int invLevel = toInteger(inventoryLevel);
        int min = toInteger(partMin);
        int max = toInteger(partMax);

        if (invLevel > max) {
            throw new Exception("Inventory level is greater than maximum value.");
        }

        if (invLevel < min) {
            throw new Exception("Inventory level is lesser than minimum value.");
        }

        // preventing the minimum field from having a value above the maximum field
        if (min > max) {
            throw new Exception("Minimum value is greater than maximum value.");
        }

        // preventing the maximum field from having a value below the minimum field
        if (max < min) {
            throw new Exception("Maximum value is greater than minimum value.");
        }
    }

    // preventing the user from deleting a product that has a part assigned to it
    default void validateDeleteProduct(Product product) throws Exception {
        if (product.getAssociatedParts().size() > 0) {
            throw new Exception("Can not delete a product that has a part assigned.");
        }
    }

    default void dialog(String messages) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Errors Found");
        alert.setHeaderText(null);
        alert.setContentText(messages);
        alert.showAndWait();
    }

}