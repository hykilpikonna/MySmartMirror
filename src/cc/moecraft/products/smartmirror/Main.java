package cc.moecraft.products.smartmirror;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.util.SimpleTimeZone;
import java.util.logging.*;

public class Main extends Application {

    private final static Logger logger = Logger.getLogger(Main.class.getName());
    public static boolean debug = true;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Logger rootLogger = logger;
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.INFO);
        FileHandler fileTxt = new FileHandler("Logging.txt");

        // create a TXT formatter
        SimpleFormatter formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

        Parent root = FXMLLoader.load(getClass().getResource("SmartMirror.fxml"));
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
