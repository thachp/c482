package inventory;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable, Utility {

    @FXML
    private TableView<Part> tblParts;

    @FXML
    private TableView<Product> tblProducts;

    @FXML
    private TableColumn<Part, Number> partIdColumn;

    @FXML
    private TableColumn<Product, Number> productIdColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Part, Number> partInventoryLevelColumn;

    @FXML
    private TableColumn<Product, Number> productInventoryLevelColumn;

    @FXML
    private TableColumn<Part, String> partPriceColumn;

    @FXML
    private TableColumn<Product, String> productPriceColumn;

    @FXML
    private TextField txtSearchPart;

    @FXML
    private TextField txtSearchProduct;

    @FXML
    private Button btnDeletePart;

    @FXML
    private Button btnModifyPart;

    @FXML
    private Button btnSearchParts;

    @FXML
    private Button btnDeleteProduct;

    @FXML
    private Button btnModifyProduct;

    @FXML
    private Button btnSearchProducts;

    @FXML
    private Button btnAddProduct;

    private static MainController instance;

    public MainController() {
        instance = this;
    }

    public static MainController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeParts();
        initializeProducts();
        disableButtons();
    }

    private void initializeParts() {
        partIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIdProperty());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        productPriceColumn.setCellValueFactory(cellData ->
                Bindings.format("$%.2f", cellData.getValue().priceProperty()));
        tblParts.setItems(Inventory.getInstance().getAllParts());
    }

    private void initializeProducts() {
        productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        productInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        partPriceColumn.setCellValueFactory(cellData ->
                Bindings.format("$%.2f", cellData.getValue().priceProperty()));
        tblProducts.setItems(Inventory.getInstance().getProducts());

    }

    /**
     * Disable buttons parts * product inventory are empty.
     */

    private void disableButtons() {
        if (Inventory.getInstance().getAllParts().isEmpty()) {
            btnDeletePart.setDisable(true);
            btnModifyPart.setDisable(true);
            btnSearchParts.setDisable(true);
            txtSearchPart.setDisable(true);
            btnAddProduct.setDisable(true);
        }

        if(Inventory.getInstance().getProducts().isEmpty()) {
            btnDeleteProduct.setDisable(true);
            btnModifyProduct.setDisable(true);
            btnSearchProducts.setDisable(true);
            txtSearchProduct.setDisable(true);
        }
    }

    @FXML
    private void handleModifyPart(ActionEvent event) {
        Part selectedPart = tblParts.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Main.getInstance().gotoPartEditView(PartController.PART_WIDTH, PartController.PART_HEIGHT);
            PartController.getInstance().setSceneName("Modify Part");
            PartController.getInstance().handleEditPart(selectedPart);
        }
    }

    @FXML
    private void handleAddPart(ActionEvent event) {
        Main.getInstance().gotoPartEditView(PartController.PART_WIDTH, PartController.PART_HEIGHT);
        PartController.getInstance().setSceneName("Add Part");

    }

    @FXML
    private void handleAddProduct(ActionEvent event) {
        Main.getInstance().gotoProductEditView(ProductController.PRODUCT_WIDTH, ProductController.PRODUCT_HEIGHT);
        ProductController.getInstance().setSceneName("Add Product");
    }

    @FXML
    private void handleModifyProduct(ActionEvent event) {

        Product selectedProduct = tblProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Main.getInstance().gotoProductEditView(ProductController.PRODUCT_WIDTH, ProductController.PRODUCT_HEIGHT);
            ProductController.getInstance().setSceneName("Modify Product");
            ProductController.getInstance().handleEditProduct(selectedProduct);
        }

    }

    @FXML
    private void handleSearchProduct(ActionEvent event) {
        FilteredList<Product> filteredData = new FilteredList<>(Inventory.getInstance().getProducts(), p -> true);
        filteredData.setPredicate(product -> {
            // If filter text is empty, display all products.
            if (txtSearchProduct.getText() == null || txtSearchProduct.getLength() == 0) {
                return true;
            }

            // Compare name of every products with filter text.
            String lowerCaseFilter = txtSearchProduct.getText().toLowerCase();
            return product.getName().toLowerCase().contains(lowerCaseFilter);

        });
        
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblProducts.comparatorProperty());
        tblProducts.setItems(sortedData);
    }

    @FXML
    private void handleSearchPart(ActionEvent event) {
        FilteredList<Part> filteredData = new FilteredList<>(Inventory.getInstance().getAllParts(), p -> true);


        filteredData.setPredicate(part -> {
            // If filter text is empty, display all parts.
            if (txtSearchPart.getText() == null || txtSearchPart.getLength() == 0) {
                return true;
            }

            // Compare part name of every parts with filter text.
            String lowerCaseFilter = txtSearchPart.getText().toLowerCase();
            return part.getName().toLowerCase().contains(lowerCaseFilter);
        });

        SortedList<Part> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblParts.comparatorProperty());
        tblParts.setItems(sortedData);
    }

    /**
     * Delete a part. Require confirmation
     * @param event
     */

    @FXML
    private void handleDeletePart(ActionEvent event) {

        Part selectedPart = tblParts.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Inventory.getInstance().deletePart(selectedPart);
            }
            disableButtons();
        }

    }

    /**
     * Delete a product. Require confirmation
     * Exception -- preventing the user from deleting a product that has a part assigned to it
     * @param event
     */

    @FXML
    private void handleDeleteProduct(ActionEvent event) {
        Product selectedProduct = tblProducts.getSelectionModel().getSelectedItem();

        if(selectedProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                try {
                    validateDeleteProduct(selectedProduct);
                    Inventory.getInstance().removeProduct(selectedProduct.getProductId());
                    disableButtons();
                } catch (Exception e) {
                    dialog(e.getMessage());
                }
            }
        }
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Platform.exit();
        System.exit(0);

    }


}