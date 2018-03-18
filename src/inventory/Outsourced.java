package inventory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Outsourced extends Part implements Utility{

    private SimpleStringProperty companyName;

    public Outsourced(String partName, String inventoryLevel,
                   String companyName, String partMin, String partMax, String partPrice) {

        this.partId = new SimpleIntegerProperty(this.generatePartId());
        this.name = new SimpleStringProperty(partName);
        this.inStock = new SimpleIntegerProperty(toInteger(inventoryLevel));
        this.companyName = new SimpleStringProperty(companyName);
        this.min = new SimpleIntegerProperty(toInteger(partMin));
        this.max = new SimpleIntegerProperty(toInteger(partMax));
        this.price = new SimpleDoubleProperty(toDouble(partPrice));
    }

    public void setCompanyName(String name) {
        this.companyName.set(name);
    }

    public String getCompanyName() {
        return this.companyName.get();
    }
}
