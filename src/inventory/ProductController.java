package inventory;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class ProductController implements Initializable {

    final static int PRODUCT_WIDTH = 900;
    final static int PRODUCT_HEIGHT = 600;

    @FXML
    private Text txtProductScene;

    @FXML
    private TextField txtSearchProduct;

    private static ProductController instance;

    public ProductController() {
        instance = this;
    }

    public static ProductController getInstance() {
        return instance;
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    private void handleSave(ActionEvent event) {
        Main.getInstance().gotoMain(Main.APP_WIDTH, Main.APP_HEIGHT);
    }

    public void setSceneName(String name) {
        txtProductScene.setText(name);
    }


    @FXML
    private void handleCancel(ActionEvent event) {
        Main.getInstance().gotoMain(Main.APP_WIDTH, Main.APP_HEIGHT);
    }

}