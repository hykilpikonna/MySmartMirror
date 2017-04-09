package cc.moecraft.products.smartmirror;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    public static boolean debug = true;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("SmartMirror.fxml"));
        root.setStyle("-fx-background-color: #000000;");

        primaryStage.setTitle("Smart Mirror Snapshot");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setFullScreen(true);
        primaryStage.setAlwaysOnTop(true);

        primaryStage.setFullScreenExitHint("");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
