package inventory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Iterator;

public class Product {

    private ArrayList<Part> associatedParts;
    private SimpleIntegerProperty productId;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private SimpleIntegerProperty inStock;
    private SimpleIntegerProperty min;
    private SimpleIntegerProperty max;


    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return this.name.get();
    }


    public void setPrice(double price) {
        this.price.set(price);
    }

    public double getPrice() {
        return this.price.get();
    }

    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }

    public int getInStock() {
        return inStock.get();
    }

    public void setProductId(int productId) {
        this.productId.set(productId);
    }

    public int getProductId() {
        return productId.get();
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
}
