package inventory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Inhouse extends Part implements Utility {

    private SimpleIntegerProperty machineId;

    public Inhouse(String partName, String inventoryLevel,
                   String machineId, String partMin, String partMax, String partPrice) {

        this.partId = new SimpleIntegerProperty(this.generatePartId());
        this.name = new SimpleStringProperty(partName);
        this.inStock = new SimpleIntegerProperty(toInteger(inventoryLevel));
        this.machineId = new SimpleIntegerProperty(toInteger(machineId));
        this.min = new SimpleIntegerProperty(toInteger(partMin));
        this.max = new SimpleIntegerProperty(toInteger(partMax));
        this.price = new SimpleDoubleProperty(toDouble(partPrice));
    }

    public void setMachineId(int id) {
        this.machineId.set(id);
    }

    public int getMachineId() {
        return this.machineId.get();
    }
}
