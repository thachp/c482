package inventory;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private SimpleIntegerProperty productId;
    private SimpleStringProperty name;
    private SimpleIntegerProperty inStock;
    private SimpleIntegerProperty min;
    private SimpleIntegerProperty max;
    private SimpleDoubleProperty price;

    public Product() {
        this.productId = new SimpleIntegerProperty(this.generateProdId());
    }

    public Product(String partName, String  inventoryLevel, String partMin,
                   String partMax, String partPrice) {
        this.productId = new SimpleIntegerProperty(this.generateProdId());
        this.name = new SimpleStringProperty(partName);
        this.inStock = new SimpleIntegerProperty(Integer.parseInt(inventoryLevel));
        this.min = new SimpleIntegerProperty(Integer.parseInt(partMin));
        this.max = new SimpleIntegerProperty(Integer.parseInt(partMax));
        this.price = new SimpleDoubleProperty(Double.parseDouble(partPrice));

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
        this.associatedParts.add(part);
    }

    public ObservableList<Part> getAssociatedParts() {
        return this.associatedParts;
    }

    public boolean removeAssociatedPart(int partId) {

        // a part has been removed from arraylist
        boolean deleted = false;
        Iterator<Part> it = this.associatedParts.iterator();
        while (it.hasNext()) {
            if (it.next().getPartId() == partId) {
                it.remove();
                deleted = true;
                break;
            }
        }

        return deleted;
     }

    public Part lookupAssociatedPart(int partId) {
        // a part has been removed from arraylist
        Part part = null;

        Iterator<Part> it = this.associatedParts.iterator();
        while (it.hasNext()) {
            if (it.next().getPartId() == partId) {
                part = it.next();
                break;
            }
        }
        return part;
    }

    public int generateProdId() {
        Random rand = new Random();
        int rand1 = rand.nextInt(100);
        int rand2 = rand.nextInt(100);
        return (int) rand1 * rand2;
    }

}
