package cc.moecraft.products.smartmirror;

import cc.moecraft.products.smartmirror.configuration.file.YamlConfiguration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.DataInput;
import java.io.File;
import java.io.IOException;

import static cc.moecraft.products.smartmirror.logger.Debug;
import static cc.moecraft.products.smartmirror.logger.log;

public class Main extends Application
{
    public static boolean debug = true;
    private static Main instance = null;

    public static Main getInstance()
    {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        instance = this;

        checkConfig();

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
