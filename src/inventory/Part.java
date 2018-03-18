package inventory;

import javafx.beans.property.*;

import java.util.Random;

abstract public class Part {

    protected SimpleIntegerProperty partId;
    protected SimpleStringProperty name;
    protected SimpleDoubleProperty price;
    protected SimpleIntegerProperty inStock;
    protected SimpleIntegerProperty min;
    protected SimpleIntegerProperty max;

    public void setPartId(int id) {
        this.partId.set(id);
    }

    public int getPartId() {
        return partId.get();
    }

    public IntegerProperty partIdProperty() {
        return partId;
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


    public void setMin(int min) {
        this.min.set(min);
    }

    public int getMin() {
        return this.min.get();
    }

    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }

    public int getInStock() {
        return this.inStock.get();
    }


    public IntegerProperty inStockProperty() {
        return inStock;
    }

    public void setMax(int max) {
        this.max.set(max);
    }

    public int getMax() {
        return this.max.get();
    }

    public int generatePartId() {
        Random rand = new Random();
        int rand1 = rand.nextInt(100);
        int rand2 = rand.nextInt(100);
        return rand1 * rand2;
    }

}
