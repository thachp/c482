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
        Product theProduct = lookupProduct(productId);
        return this.products.remove(theProduct);
    }

    public Product lookupProduct(int productId) {

        Product product = null;

        for (Product p : products){

            if(p.getProductId() == productId) {
                product = p;
                break;
            }
        }

        return product;
    }

    public void addPart(Part part) {
        this.allParts.add(part);
    }

    public boolean deletePart(Part part) {
        return this.allParts.remove(part);
    }

    public Part lookupPart(int partId) {
        Part part = null;

        for (Part p : allParts){
            if(p.getPartId() == partId) {
                part = p;
                break;
            }
        }
        return part;
    }

    public void updateProduct(int productId) {
        // not sure what to do
    }

    public void updatePart(int partId) {
        // not sure what // TODO: 3/3/18
    }

}
