package inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    final static int APP_WIDTH = 900;
    final static int APP_HEIGHT = 450;

    private Stage stage;

    private static Main instance;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            gotoMain(APP_WIDTH, APP_HEIGHT);
            //primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoMain(int width, int height) {
        try {
            replaceScene("main_screen.fxml", width, height);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoPartEditView(int width, int height) {
        try {
            replaceScene("add_edit_part.fxml", width, height);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoProductEditView(int width, int height) {
        try {
            replaceScene("add_edit_product.fxml", width, height);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Parent replaceScene(String fxml, int width, int height) throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource(fxml), null, new JavaFXBuilderFactory());
        Scene scene = stage.getScene();

        if (scene == null) {
            scene = new Scene(root, width, height);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(root);
        }
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setResizable(false);

        return root;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
