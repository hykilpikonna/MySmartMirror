package cc.moecraft.products.smartmirror;

import cc.moecraft.products.smartmirror.configuration.file.YamlConfiguration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static cc.moecraft.products.smartmirror.logger.Debug;

public class Main extends Application
{
    public static boolean debug = true;
    private static Main instance = null;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double screenSizeWidth = screenSize.getWidth();
    private double screenSizeHeight = screenSize.getHeight();
    private double screenSizeMidX = screenSizeWidth / 2;
    private double screenSizeMidY = screenSizeHeight / 2;

    public static Main getInstance()
    {
        return instance;
    }

    public static Pane root;
    public static Stage stage;
    public static Scene scene;

    @Override
    public void start(Stage stage) throws Exception
    {
        instance = this;

        checkConfig();

        root = FXMLLoader.load(getClass().getResource("SmartMirror.fxml"));
        root.setStyle("-fx-background-color: #000000");
        root.getChildren().add(new DigitalClock());
        root.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        scene = new Scene(root, screenSizeWidth, screenSizeHeight);

        Main.stage = stage;
        Main.stage.setTitle("Smart Mirror Snapshot");
        Main.stage.setScene(scene);
        Main.stage.setFullScreen(true);
        Main.stage.setAlwaysOnTop(true);

        Main.stage.setFullScreenExitHint("");

        Main.stage.show();
    }

    public static YamlConfiguration config;
    public static File configFile;
    public boolean checkConfig()
    {
        configFile = new File(this.getDataFolder() + "Config.yml");
        Debug(getDataFolder());
        config = YamlConfiguration.loadConfiguration(configFile);
        config.options().copyDefaults(true);
        if (!(config.contains("Generated")))
        {
            config.addDefault("Generated", true);
            saveConfig();
            return false;
        }
        else
        {
            return true;
        }
    }

    public static boolean saveConfig()
    {
        try
        {
            config.save(configFile);
            return true;
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public String getDataFolder()
    {
        String path = System.getProperty("user.dir");
        Debug("Got data folder: " + path);
        return path;
    }
}
