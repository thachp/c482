package inventory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class PartController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RadioButton selectedRadioButton = (RadioButton) partRadioGroup.getSelectedToggle();
        this.toggleGroupValue = selectedRadioButton.getText();

        handleSourcePartToggle();
        handleMachine();
        restrictInput(txtPartMin);
        restrictInput(txtPartMax);
        restrictInput(txtPartInventory);
        handlePrice();

    }

    public void handleEditPart(Part part) {

        txtPartID.setText(Integer.toString(part.getPartId()));
        txtPartName.setText(part.getName());
        txtPartInventory.setText(Integer.toString(part.getInStock()));
        txtPartMin.setText(Integer.toString(part.getMin()));
        txtPartMax.setText(Integer.toString(part.getMax()));
        txtPartPrice.setText(Double.toString(part.getPrice()));


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

        if (partName.length() == 0 || inventoryLevel.length() == 0 ||
                partSource.length() == 0 || partMin.length() == 0 ||
                partPrice.length() == 0 || partMax.length() == 0) {
            alertInfo("Please complete all fields!");
        } else {

            // update
            updatePart(partName, inventoryLevel, partSource, partMin, partMax, partPrice);
            insertPart(partName, inventoryLevel, partSource, partMin, partMax, partPrice);

            // submit part data to inventory and return
            Main.getInstance().gotoMain(Main.APP_WIDTH, Main.APP_HEIGHT);
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


        if (toggleGroupValue.contains("In-House") && !txtPartID.getText().isEmpty()) {

            int partId = Integer.parseInt(txtPartID.getText());
            Part thePart = Inventory.getInstance().lookupPart(partId);
            Inventory.getInstance().deletePart(thePart);

            Inhouse newPart = new Inhouse(partId, partName, inventoryLevel,
                    partSource, partMin, partMax, partPrice);
            Inventory.getInstance().addPart(newPart);
        }

        if (toggleGroupValue.contains("Outsourced") && !txtPartID.getText().isEmpty()) {

            int partId = Integer.parseInt(txtPartID.getText());
            Part thePart = Inventory.getInstance().lookupPart(partId);
            Inventory.getInstance().deletePart(thePart);

            Outsourced newPart = new Outsourced(partId, partName, inventoryLevel,
                    partSource, partMin, partMax, partPrice);
            Inventory.getInstance().addPart(newPart);
        }

    }


    private void alertInfo(String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Information");
        alert.setContentText(body);
        alert.showAndWait();

    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Main.getInstance().gotoMain(Main.APP_WIDTH, Main.APP_HEIGHT);
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

    private void restrictInput(TextField tf) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                tf.setText(oldValue);
            }
        });
    }

    private void handlePrice() {
        txtPartPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([0-9.]*)")) {
                txtPartPrice.setText(oldValue);
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