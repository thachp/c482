package inventory;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product implements Utility {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private SimpleIntegerProperty productId;
    private SimpleStringProperty name;
    private SimpleIntegerProperty inStock;
    private SimpleIntegerProperty min;
    private SimpleIntegerProperty max;
    private SimpleDoubleProperty price;

    public Product(String partName, String inventoryLevel, String partMin,
                   String partMax, String partPrice) {

        this.productId = new SimpleIntegerProperty(generateProdId());
        this.name = new SimpleStringProperty(partName);
        this.inStock = new SimpleIntegerProperty(toInteger(inventoryLevel));
        this.min = new SimpleIntegerProperty(toInteger(partMin));
        this.max = new SimpleIntegerProperty(toInteger(partMax));
        this.price = new SimpleDoubleProperty(toDouble(partPrice));

    }

    public void setProductId(int productId) {
        this.productId.set(productId);
    }

    public int getProductId() {
        return this.productId.get();
    }

    public IntegerProperty productIdProperty() {
        return productId;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return this.name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public double getPrice() {
        return this.price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }

    public int getInStock() {
        return inStock.get();
    }

    public IntegerProperty inStockProperty() {
        return inStock;
    }

    public void setMax(int max) {
        this.max.set(max);
    }

    public int getMax() {
        return max.get();
    }

    public int getMin() {
        return min.get();
    }

    public void setMin(int min) {
        this.min.set(min);
    }

    public void addAssociatedPart(Part part) {
        // make sure it does not exist
        if(lookupAssociatedPart(part.getPartId()) == null) {
            this.associatedParts.add(part);
        }
    }

    public ObservableList<Part> getAssociatedParts() {
        return this.associatedParts;
    }

    public boolean removeAssociatedPart(int partId) {
        return this.associatedParts.remove(lookupAssociatedPart(partId));
    }

    public Part lookupAssociatedPart(int partId) {
        Part part = null;
        for (Part p : this.associatedParts) {
            if (p.getPartId() == partId) {
                part = p;
                break;
            }
        }
        return part;
    }

}
