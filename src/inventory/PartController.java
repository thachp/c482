package inventory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class PartController implements Initializable, Utility {

    final static int PART_WIDTH = 500;
    final static int PART_HEIGHT = 450;

    @FXML
    private TextField txtPartID;

    @FXML
    private TextField txtPartName;

    @FXML
    private TextField txtPartInventory;

    @FXML
    private Text txtPartScene;

    @FXML
    private Label labelPartSource;

    @FXML
    private TextField txtPartSource;

    @FXML
    private TextField txtPartMin;

    @FXML
    private TextField txtPartMax;

    @FXML
    private TextField txtPartPrice;

    @FXML
    private RadioButton radInHouse;

    @FXML
    private RadioButton radOutsourced;


    @FXML
    ToggleGroup partRadioGroup;
    private String toggleGroupValue;

    private static PartController instance;

    public PartController() {
        instance = this;
    }

    public static PartController getInstance() {
        return instance;
    }


    /**
     * Initialize form, restrict integer input only accept Integer using REGEX
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        RadioButton selectedRadioButton = (RadioButton) partRadioGroup.getSelectedToggle();
        this.toggleGroupValue = selectedRadioButton.getText();

        handleSourcePartToggle();

        // switch company name and machine id dependent radiogroup solution
        handleMachine();
        restrictInput(txtPartMin, "[0-9]*");
        restrictInput(txtPartMax, "[0-9]*");
        restrictInput(txtPartInventory, "[0-9]*");
        restrictInput(txtPartPrice, "[0-9\\.]*");

    }

    public void handleEditPart(Part part) {

        txtPartID.setText(Integer.toString(part.getPartId()));
        txtPartName.setText(part.getName());
        txtPartInventory.setText(Integer.toString(part.getInStock()));
        txtPartMin.setText(Integer.toString(part.getMin()));
        txtPartMax.setText(Integer.toString(part.getMax()));
        txtPartPrice.setText(toPriceFormat(part.getPrice()));


        if (part.getClass() == Inhouse.class) {
            this.radInHouse.setSelected(true);
            this.radOutsourced.setSelected(false);
            Inhouse thePart = (Inhouse) part;
            txtPartSource.setText(Integer.toString(thePart.getMachineId()));

        }

        if (part.getClass() == Outsourced.class) {
            this.radInHouse.setSelected(false);
            this.radOutsourced.setSelected(true);
            Outsourced thePart = (Outsourced) part;
            txtPartSource.setText(thePart.getCompanyName());
        }

    }

    @FXML
    private void handleSave(ActionEvent event) {

        String partName = txtPartName.getText();
        String inventoryLevel = txtPartInventory.getText();
        String partSource = txtPartSource.getText();
        String partMin = txtPartMin.getText();
        String partMax = txtPartMax.getText();
        String partPrice = txtPartPrice.getText();

        // validate form
        try {
            validatePart(partName, inventoryLevel, partMin, partMax, partPrice);
            updatePart(partName, inventoryLevel, partSource, partMin, partMax, partPrice);
            insertPart(partName, inventoryLevel, partSource, partMin, partMax, partPrice);

            // submit part data to inventory and return
            Main.getInstance().gotoMain(Main.APP_WIDTH, Main.APP_HEIGHT);
        } catch (Exception e) {
            dialog(e.getMessage());
        }
    }

    // insert
    private void insertPart(String partName,
                            String inventoryLevel,
                            String partSource,
                            String partMin,
                            String partMax,
                            String partPrice) {

        if (toggleGroupValue.contains("In-House") && txtPartID.getText().isEmpty()) {

            Inhouse thePart = new Inhouse(partName, inventoryLevel,
                    partSource, partMin, partMax, partPrice);
            Inventory.getInstance().addPart(thePart);
        }

        if (toggleGroupValue.contains("Outsourced") && txtPartID.getText().isEmpty()) {
            Outsourced thePart = new Outsourced(partName, inventoryLevel,
                    partSource, partMin, partMax, partPrice);
            Inventory.getInstance().addPart(thePart);
        }

    }

    private void updatePart(String partName,
                            String inventoryLevel,
                            String partSource,
                            String partMin,
                            String partMax,
                            String partPrice) {

        // Okay update if Id exist.
        if (toggleGroupValue.contains("In-House") && !txtPartID.getText().isEmpty()) {

            int partId = Integer.parseInt(txtPartID.getText());
            Inhouse thePart = (Inhouse) Inventory.getInstance().lookupPart(partId);
            thePart.setName(partName);
            thePart.setInStock(toInteger(inventoryLevel));
            thePart.setMachineId(toInteger(partSource));
            thePart.setMin(toInteger(partMin));
            thePart.setMax(toInteger(partMax));
            thePart.setPrice(toDouble(partPrice));
        }

        if (toggleGroupValue.contains("Outsourced") && !txtPartID.getText().isEmpty()) {

            int partId = Integer.parseInt(txtPartID.getText());
            Outsourced thePart = (Outsourced) Inventory.getInstance().lookupPart(partId);

            thePart.setName(partName);
            thePart.setInStock(toInteger(inventoryLevel));
            thePart.setCompanyName(partSource);
            thePart.setMin(toInteger(partMin));
            thePart.setMax(toInteger(partMax));
            thePart.setPrice(toDouble(partPrice));
        }

    }

    @FXML
    private void handleCancel(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Main.getInstance().gotoMain(Main.APP_WIDTH, Main.APP_HEIGHT);
        }

    }

    public void setSceneName(String name) {
        txtPartScene.setText(name);
    }

    private void handleMachine() {
        txtPartSource.textProperty().addListener((observable, oldValue, newValue) -> {
            if (toggleGroupValue.contains("In-House") && !newValue.matches("[0-9]*")) {
                txtPartSource.setText(oldValue);
            }
        });
    }

    private void handleSourcePartToggle() {
        //
        RadioButton selectedRadioButton = (RadioButton) partRadioGroup.getSelectedToggle();
        toggleGroupValue = selectedRadioButton.getText();

        String inHouse = "In-House";
        String machineId = "Machine ID";
        String companyName = "Company Name";

        if (toggleGroupValue.contains(inHouse)) {
            labelPartSource.setText(machineId);
            txtPartSource.setPromptText(machineId);
        } else {
            labelPartSource.setText(companyName);
            txtPartSource.setPromptText(companyName);
        }


        partRadioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                RadioButton selectedRadioButton = (RadioButton) partRadioGroup.getSelectedToggle();

                if (selectedRadioButton != null) {
                    toggleGroupValue = selectedRadioButton.getText();
                    if (toggleGroupValue.contains(inHouse)) {
                        labelPartSource.setText(machineId);
                        txtPartSource.setPromptText(machineId);
                    } else {
                        labelPartSource.setText(companyName);
                        txtPartSource.setPromptText(companyName);
                    }
                    txtPartSource.setText("");
                }

            }
        });

    }

}