package inventory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Outsourced extends Part{

    private SimpleStringProperty companyName;

    public Outsourced(String partName, String inventoryLevel,
                   String companyName, String partMin, String partMax, String partPrice) {

        this.partId = new SimpleIntegerProperty(this.generatePartId());
        this.name = new SimpleStringProperty(partName);
        this.inStock = new SimpleIntegerProperty(Integer.parseInt(inventoryLevel));
        this.companyName = new SimpleStringProperty(companyName);
        this.min = new SimpleIntegerProperty(Integer.parseInt(partMin));
        this.max = new SimpleIntegerProperty(Integer.parseInt(partMax));
        this.price = new SimpleDoubleProperty(Double.parseDouble(partPrice));
    }

    public void setCompanyName(String name) {
        this.companyName.set(name);
    }

    public String getCompanyName() {
        return this.companyName.get();
    }
}
