package inventory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Inhouse extends Part {

    private SimpleIntegerProperty machineId;

    public Inhouse(String partName, String inventoryLevel,
                   String machineId, String partMin, String partMax, String partPrice) {

        this.partId = new SimpleIntegerProperty(this.generatePartId());
        this.name = new SimpleStringProperty(partName);
        this.inStock = new SimpleIntegerProperty(Integer.parseInt(inventoryLevel));
        this.machineId = new SimpleIntegerProperty(Integer.parseInt(machineId));
        this.min = new SimpleIntegerProperty(Integer.parseInt(partMin));
        this.max = new SimpleIntegerProperty(Integer.parseInt(partMax));
        this.price = new SimpleDoubleProperty(Double.parseDouble(partPrice));
    }

    public Inhouse(int partId, String partName, String inventoryLevel,
                   String machineId, String partMin, String partMax, String partPrice) {

        this.partId = new SimpleIntegerProperty(partId);
        this.name = new SimpleStringProperty(partName);
        this.inStock = new SimpleIntegerProperty(Integer.parseInt(inventoryLevel));
        this.machineId = new SimpleIntegerProperty(Integer.parseInt(machineId));
        this.min = new SimpleIntegerProperty(Integer.parseInt(partMin));
        this.max = new SimpleIntegerProperty(Integer.parseInt(partMax));
        this.price = new SimpleDoubleProperty(Double.parseDouble(partPrice));
    }

    public void setMachineId(int id) {
        this.machineId.set(id);
    }

    public int getMachineId() {
        return this.machineId.get();
    }
}
