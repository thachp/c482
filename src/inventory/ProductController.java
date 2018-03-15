package inventory;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class ProductController implements Initializable {

    final static int PRODUCT_WIDTH = 900;
    final static int PRODUCT_HEIGHT = 600;


    @FXML
    private TableView<Part> tblAllParts;

    @FXML
    private TableView<Part> tblProductParts;

    @FXML
    private TableColumn<Part, Number> allPartIdColumn;

    @FXML
    private TableColumn<Part, Number> prodPartIdColumn;

    @FXML
    private TableColumn<Part, String> allPartNameColumn;

    @FXML
    private TableColumn<Part, String> prodPartNameColumn;

    @FXML
    private TableColumn<Part, Number> allPartInventoryLevelColumn;

    @FXML
    private TableColumn<Part, Number> prodPartInventoryLevelColumn;

    @FXML
    private TableColumn<Part, String> allPartPriceColumn;

    @FXML
    private TableColumn<Part, String> prodPartPriceColumn;

    @FXML
    private Text txtProductScene;
    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtProductInventory;

    @FXML
    private TextField txtProductMaximum;

    @FXML
    private TextField txtProductMinimum;

    @FXML
    private TextField txtProductPrice;

    private static ProductController instance;

    public ProductController() {
        instance = this;
    }

    public static ProductController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initializeAllParts();
        restrictInput(txtProductMinimum);
        restrictInput(txtProductMaximum);
        restrictInput(txtProductInventory);
        handlePrice();

    }

    private void initializeAllParts() {
        allPartIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIdProperty());
        allPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        allPartInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        allPartPriceColumn.setCellValueFactory(cellData ->
                Bindings.format("%.2f", cellData.getValue().priceProperty()));
        tblAllParts.setItems(Inventory.getInstance().getAllParts());
    }

    private void initializeProdParts() {
        allPartIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIdProperty());
        allPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        allPartInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        allPartPriceColumn.setCellValueFactory(cellData ->
                Bindings.format("%.2f", cellData.getValue().priceProperty()));
        tblProductParts.setItems(Inventory.getInstance().getAllParts());
    }


    @FXML
    private void handleSearchProduct(ActionEvent event) {

    }

    @FXML
    private void handleSearchPart(ActionEvent event) {

    }

    @FXML
    private void handleDeletePart(ActionEvent event) {

    }

    @FXML
    private void handleDeleteProduct(ActionEvent event) {
        //
    }

    private void handlePrice() {
        txtProductPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([0-9.]*)")) {
                txtProductPrice.setText(oldValue);
            }
        });
    }

    private void handleSave(ActionEvent event) {

        String productName = txtProductName.getText();
        String productInventory = txtProductInventory.getText();
        String productMin = txtProductMinimum.getText();
        String productMax = txtProductMaximum.getText();
        String productPrice = txtProductPrice.getText();

        // submit part data to inventory and return
        Main.getInstance().gotoMain(Main.APP_WIDTH, Main.APP_HEIGHT);
    }

    public void setSceneName(String name) {
        txtProductScene.setText(name);
    }


    private void restrictInput(TextField tf) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                tf.setText(oldValue);
            }
        });
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Main.getInstance().gotoMain(Main.APP_WIDTH, Main.APP_HEIGHT);
    }

}