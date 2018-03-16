package inventory;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class ProductController implements Initializable {

    final static int PRODUCT_WIDTH = 900;
    final static int PRODUCT_HEIGHT = 625;

    private Product myProduct;

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
    private Button btnAddPart;

    @FXML
    private Button btnSearchPart;

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

        myProduct = new Product();
        initializeAllParts();
        initializeProdParts();
        restrictInput(txtProductMinimum);
        restrictInput(txtProductMaximum);
        restrictInput(txtProductInventory);
        handlePrice();
        disableButtons();
    }

    private void initializeAllParts() {

        // get parts from inventory
        this.allParts.addAll(Inventory.getInstance().getAllParts());

        allPartIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIdProperty());
        allPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        allPartInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        allPartPriceColumn.setCellValueFactory(cellData ->
                Bindings.format("%.2f", cellData.getValue().priceProperty()));
        tblAllParts.setItems(this.allParts);
    }

    private void initializeProdParts() {
        prodPartIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIdProperty());
        prodPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        prodPartInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        prodPartPriceColumn.setCellValueFactory(cellData ->
                Bindings.format("%.2f", cellData.getValue().priceProperty()));
        tblProductParts.setItems(myProduct.getAssociatedParts());
    }

    public void handleEditProduct(Product product) {
        this.myProduct = product;
        txtProductId.setText(Integer.toString(myProduct.getProductId()));
        txtProductName.setText(myProduct.getName());
        txtProductInventory.setText(Integer.toString(myProduct.getInStock()));
        txtProductMinimum.setText(Integer.toString(myProduct.getMin()));
        txtProductMaximum.setText(Integer.toString(myProduct.getMax()));
        txtProductPrice.setText(Double.toString(myProduct.getPrice()));
        tblProductParts.setItems(myProduct.getAssociatedParts());
    }

    @FXML
    private void addPart(ActionEvent event) {
        Part selectedPart = tblAllParts.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            tblAllParts.getItems().remove(selectedPart);
            tblProductParts.getItems().add(selectedPart);
            disableButtons();
        }

    }

    @FXML
    private void deletePart(ActionEvent event) {
        Part selectedPart = tblProductParts.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            tblAllParts.getItems().add(selectedPart);
            tblProductParts.getItems().remove(selectedPart);
            disableButtons();
        }

    }

    private void handlePrice() {
        txtProductPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([0-9.]*)")) {
                txtProductPrice.setText(oldValue);
            }
        });
    }


    @FXML
    private void handleSave(ActionEvent event) {

        String productName = txtProductName.getText();
        String productInventory = txtProductInventory.getText();
        String productMin = txtProductMinimum.getText();
        String productMax = txtProductMaximum.getText();
        String productPrice = txtProductPrice.getText();

        // submit part data to inventory and return
        addProduct(productName, productInventory, productMin, productMax, productPrice);
        editProduct(productName, productInventory, productMin, productMax, productPrice);

        // add product
        Main.getInstance().gotoMain(Main.APP_WIDTH, Main.APP_HEIGHT);
    }

    private void editProduct(String productName, String productInventory,
                             String productMin, String productMax, String productPrice) {

        if(!txtProductId.getText().isEmpty()) {
            myProduct.setName(productName);
            myProduct.setInStock(Integer.parseInt(productInventory));
            myProduct.setMin(Integer.parseInt(productMin));
            myProduct.setMax(Integer.parseInt(productMax));
            myProduct.setPrice(Double.parseDouble(productPrice));
        }
    }

    private void addProduct(String productName, String productInventory,
                            String productMin, String productMax, String productPrice) {

        if(txtProductId.getText().isEmpty()) {
            Product theProduct = new Product(productName, productInventory, productMin, productMax, productPrice);

            ObservableList<Part> parts = tblProductParts.getItems();

            for (Part p : parts){
                theProduct.addAssociatedPart(p);
            }

            // add product
            Inventory.getInstance().addProduct(theProduct);
        }
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


    @FXML
    private void searchPart(ActionEvent event) {
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
        sortedData.comparatorProperty().bind(tblAllParts.comparatorProperty());
        tblAllParts.setItems(sortedData);
    }


    private void disableButtons() {
        if (tblAllParts.getItems().isEmpty()) {
            btnAddPart.setDisable(true);
            btnSearchPart.setDisable(true);
            txtSearchPart.setDisable(true);
        }  else {
            btnAddPart.setDisable(false);
            btnSearchPart.setDisable(false);
            txtSearchPart.setDisable(false);
        }

        if(myProduct.getAssociatedParts().isEmpty()) {
            btnDeletePart.setDisable(true);
        } else {
            btnDeletePart.setDisable(false);

        }
    }


}