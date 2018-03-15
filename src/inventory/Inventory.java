package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;

public class Inventory {

    private ObservableList<Product> products = FXCollections.observableArrayList();

    private ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static final Inventory INVENTORY = new Inventory();

    public static Inventory getInstance() {
        return INVENTORY;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }


    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }


    public boolean removeProduct(int productId) {
        boolean deleted = false;
        Iterator<Product> it = this.products.iterator();
        while (it.hasNext()) {
            if (it.next().getProductId() == productId) {
                it.remove();
                deleted = true;
                break;
            }
        }
        return deleted;
    }

    public Product lookupProduct(int productId) {
        // a part has been removed from arraylist
        Product product = null;

        Iterator<Product> it = this.products.iterator();
        while (it.hasNext()) {
            if (it.next().getProductId() == productId) {
                product = it.next();
                break;
            }
        }
        return product;
    }

    public void updateProduct(int productId) {
        // not sure what to do
    }

    public void addPart(Part part) {
        this.allParts.add(part);
    }

    public boolean deletePart(Part part) {
        return this.allParts.remove(part);
    }

    public Part lookupPart(int partId) {
        // a part has been removed from arraylist
        Part part = null;

        for (Part p : allParts){

            if(p.getPartId() == partId) {
                part = p;
                break;
            }
            // Do whatever you want to do with tabPane
        }

        return part;
    }

    public void updatePart(int partId) {
        // not sure what // TODO: 3/3/18
    }

}
