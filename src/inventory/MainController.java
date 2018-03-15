package inventory;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

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
                Bindings.format("%.2f", cellData.getValue().priceProperty()));
        tblParts.setItems(Inventory.getInstance().getAllParts());
    }

    private void initializeProducts() {
        productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        productInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        partPriceColumn.setCellValueFactory(cellData ->
                Bindings.format("%.2f", cellData.getValue().priceProperty()));
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
        Main.getInstance().gotoProductEditView(ProductController.PRODUCT_WIDTH, ProductController.PRODUCT_HEIGHT);
        ProductController.getInstance().setSceneName("Modify Product");
    }

    @FXML
    private void handleSearchProduct(ActionEvent event) {
        FilteredList<Product> filteredData = new FilteredList<>(Inventory.getInstance().getProducts(), p -> true);


        filteredData.setPredicate(product -> {
            // If filter text is empty, display all persons.
            if (txtSearchProduct.getText() == null || txtSearchProduct.getLength() == 0) {
                return true;
            }

            // Compare first name and last name of every person with filter text.
            String lowerCaseFilter = txtSearchProduct.getText().toLowerCase();

            if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches first name.
            }
            return false; // Does not match.
        });

        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblProducts.comparatorProperty());
        tblProducts.setItems(sortedData);
    }

    @FXML
    private void handleSearchPart(ActionEvent event) {
        FilteredList<Part> filteredData = new FilteredList<>(Inventory.getInstance().getAllParts(), p -> true);


        filteredData.setPredicate(part -> {
            // If filter text is empty, display all persons.
            if (txtSearchPart.getText() == null || txtSearchPart.getLength() == 0) {
                return true;
            }

            // Compare first name and last name of every person with filter text.
            String lowerCaseFilter = txtSearchPart.getText().toLowerCase();

            if (part.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches first name.
            }
            return false; // Does not match.
        });

        SortedList<Part> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblParts.comparatorProperty());
        tblParts.setItems(sortedData);
    }

    @FXML
    private void handleDeletePart(ActionEvent event) {

        int selectedIndex = tblParts.getSelectionModel().getSelectedIndex();

        if (selectedIndex != -1) {
            tblParts.getItems().remove(selectedIndex);
        }

        disableButtons();

    }

    @FXML
    private void handleDeleteProduct(ActionEvent event) {
        //
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Platform.exit();
    }


}