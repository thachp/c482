package inventory;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    private TableView<Part> tblParts;

    @FXML
    private TableColumn<Part, Number> partIdColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Number> partInventoryLevelColumn;

    @FXML
    private TableColumn<Part, String> partPriceColumn;

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
        disableButtons();
    }

    private void initializeParts() {
        partIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIdProperty());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        partPriceColumn.setCellValueFactory(cellData ->
                Bindings.format("%.2f", cellData.getValue().priceProperty()));
        tblParts.setItems(Inventory.getInstance().getAllParts());
    }


    private void disableButtons() {
        if(Inventory.getInstance().getAllParts().isEmpty()) {
            btnDeletePart.setDisable(true);
            btnModifyPart.setDisable(true);
            btnSearchParts.setDisable(true);
            txtSearchPart.setDisable(true);
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

    }

    @FXML
    private void handleSearchPart(ActionEvent event) {
        System.out.println("testing testing");
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