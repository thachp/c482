package inventory;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *  An application that manage products and parts inventory.
 *
 *  PT
 * @author pthach2@wgu.edu
 */


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
            Platform.setImplicitExit(false);
            gotoMain(APP_WIDTH, APP_HEIGHT);
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

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                if (!fxml.equals("main_screen.fxml")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        gotoMain(APP_WIDTH, APP_HEIGHT);
                        event.consume();
                    }
                }
            }
        });

        return root;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
