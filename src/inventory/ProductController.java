package inventory;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class ProductController implements Initializable, Utility {

    final static int PRODUCT_WIDTH = 900;
    final static int PRODUCT_HEIGHT = 625;

    private ObservableList<Part> allParts = FXCollections.observableArrayList();

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

    @FXML
    private TextField txtSearchPart;

    @FXML
    private Button btnDeletePart;

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
        initializeProdParts();
        restrictInput(txtProductMinimum, "[0-9]*");
        restrictInput(txtProductMaximum, "[0-9]*");
        restrictInput(txtProductInventory, "[0-9]*");
        restrictInput(txtProductPrice, "[0-9\\.]*");
    }

    /**
     * Initialize tableview with inventory data
     */
    private void initializeAllParts() {

        // get parts from inventory
        // clone it so we ll be working on a different database
        this.allParts.addAll(Inventory.getInstance().getAllParts());

        allPartIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIdProperty());
        allPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        allPartInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());

        // format price as in dollar format
        allPartPriceColumn.setCellValueFactory(cellData ->
                Bindings.format("$%.2f", cellData.getValue().priceProperty()));

        // set items in tableview
        tblAllParts.setItems(this.allParts);
    }

    /**
     * Initialize tableview for parts belonging to a product in context.
     */
    private void initializeProdParts() {
        prodPartIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIdProperty());
        prodPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        prodPartInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        prodPartPriceColumn.setCellValueFactory(cellData ->
                Bindings.format("$%.2f", cellData.getValue().priceProperty()));
    }

    /**
     * Get product metadata ie parts and display them in the form as field values.
     *
     * @param product
     */
    public void handleEditProduct(Product product) {
        txtProductId.setText(Integer.toString(product.getProductId()));
        txtProductName.setText(product.getName());
        txtProductInventory.setText(Integer.toString(product.getInStock()));
        txtProductMinimum.setText(Integer.toString(product.getMin()));
        txtProductMaximum.setText(Integer.toString(product.getMax()));
        txtProductPrice.setText(toPriceFormat(product.getPrice()));
        tblProductParts.setItems(product.getAssociatedParts());

        // only show parts that don't exist in the productList
        // complexity: N^2

        ObservableList<Part> allParts = Inventory.getInstance().getAllParts();
        tblAllParts.setItems(null);

        ObservableList<Part> newParts = FXCollections.observableArrayList();
        ;
        for (Part p : allParts) {
            if (product.lookupAssociatedPart(p.getPartId()) == null) {
                newParts.add(p);
            }
        }
        tblAllParts.setItems(newParts);
    }


    /**
     * Move selected parts from Parts inventory and displa them in product tables.
     * Lower tableview all associated parts belong to a product.
     * ONE product MANY parts relationship.
     *
     * @param event
     */
    @FXML
    private void addPart(ActionEvent event) {
        Part selectedPart = tblAllParts.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            tblAllParts.getItems().remove(selectedPart);
            tblProductParts.getItems().add(selectedPart);
        }
    }

    @FXML
    private void deletePart(ActionEvent event) {
        Part selectedPart = tblProductParts.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){

                tblAllParts.getItems().add(selectedPart);
                tblProductParts.getItems().remove(selectedPart);
            }
        }
    }

    @FXML
    private void handleSave(ActionEvent event) {

        String productName = txtProductName.getText();
        String productInventory = txtProductInventory.getText();
        String productMin = txtProductMinimum.getText();
        String productMax = txtProductMaximum.getText();
        String productPrice = txtProductPrice.getText();

        // validate form
        try {

            // testing
            ObservableList<Part> parts = tblProductParts.getItems();
            validateProduct(productName, productInventory, productMin, productMax, productPrice, parts);

            addProduct(productName, productInventory, productMin, productMax, productPrice);
            editProduct(productName, productInventory, productMin, productMax, productPrice);

            // submit part data to inventory and return
            Main.getInstance().gotoMain(Main.APP_WIDTH, Main.APP_HEIGHT);
        } catch (Exception e) {
            dialog(e.getMessage());
        }

    }

    private void editProduct(String productName, String productInventory,
                             String productMin, String productMax, String productPrice) {

        if (!txtProductId.getText().isEmpty()) {

            int productId = Integer.parseInt(txtProductId.getText());
            Product theProduct = Inventory.getInstance().lookupProduct(productId);

            theProduct.setName(productName);
            theProduct.setInStock(toInteger(productInventory));
            theProduct.setMin(toInteger(productMin));
            theProduct.setMax(toInteger(productMax));
            theProduct.setPrice(toDouble(productPrice));

            ObservableList<Part> parts = tblProductParts.getItems();

            for (Part p : parts) {
                theProduct.addAssociatedPart(p);
            }

        }
    }

    private void addProduct(String productName, String productInventory,
                            String productMin, String productMax, String productPrice) {

        if (txtProductId.getText().isEmpty()) {
            Product theProduct = new Product(productName, productInventory, productMin, productMax, productPrice);

            ObservableList<Part> parts = tblProductParts.getItems();

            for (Part p : parts) {
                theProduct.addAssociatedPart(p);
            }

            // add product
            Inventory.getInstance().addProduct(theProduct);
        }
    }


    // Set label scene
    // @TODOs find better option?
    public void setSceneName(String name) {
        txtProductScene.setText(name);
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

    @FXML
    private void searchPart(ActionEvent event) {
        FilteredList<Part> filteredData = new FilteredList<>(Inventory.getInstance().getAllParts(), p -> true);

        filteredData.setPredicate(part -> {
            // If filter text is empty, display all parts.
            if (txtSearchPart.getText() == null || txtSearchPart.getLength() == 0) {
                return true;
            }
            // Compare part name of every part with filter text.
            String lowerCaseFilter = txtSearchPart.getText().toLowerCase();
            return part.getName().toLowerCase().contains(lowerCaseFilter);
        });

        SortedList<Part> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblAllParts.comparatorProperty());
        tblAllParts.setItems(sortedData);
    }

}