package cc.moecraft.products.smartmirror;

import cc.moecraft.products.smartmirror.configuration.file.YamlConfiguration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static cc.moecraft.products.smartmirror.logger.Debug;
import static cc.moecraft.products.smartmirror.logger.log;

public class Main extends Application
{
    public static boolean debug = true;
    private static Main instance = null;

    private java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
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
    public static String exoThinFontCSS;
    @Override
    public void start(Stage stage) throws Exception
    {
        instance = this;

        checkConfig();

        exoThinFontCSS = Main.class.getResource("css/exothin.css").toExternalForm();

        root = FXMLLoader.load(getClass().getResource("SmartMirror.fxml"));
        root.setStyle("-fx-background-color: #000000");

        Pane digitalClockPane = new Pane();
        digitalClockPane.getStylesheets().add(exoThinFontCSS);
        digitalClockPane.getChildren().add(new DigitalClock());

        root.getChildren().add(digitalClockPane);
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

    /**
     * 检查配置文件
     * Check config file
     * @return 是不是新的, 如果是返回False, 如果不是返回True
     */
    public boolean checkConfig()
    {
        log("[配置] 正在检查配置文件");
        configFile = new File(this.getDataFolder() + "Config.yml");
        log("[配置] DataFolder = " + getDataFolder());
        config = YamlConfiguration.loadConfiguration(configFile);
        config.options().copyDefaults(true);

        if (!(config.contains("Generated")))
        {
            log("[配置] 检测到配置文件是新的, 正在储存默认配置");
            config.addDefault("Generated", true);
            //时钟
            config.addDefault("DigitalClock.Position.X", 40);
            config.addDefault("DigitalClock.Position.Y", 120);
            config.addDefault("DigitalClock.Font.Size", 76);
            config.addDefault("DigitalClock.Font.Name", "Exo Thin");
            config.addDefault("DigitalClock.Font.Color.Red", 255);
            config.addDefault("DigitalClock.Font.Color.Green", 255);
            config.addDefault("DigitalClock.Font.Color.Blue", 255);
            config.addDefault("DigitalClock.Time.UseTwentyFourHours", true);

            saveConfig();
            return false;
        }
        else
        {
            log("[配置] 检测到配置文件不是新的");
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
        Debug("[辅助方法] Got data folder: " + path);
        return path;
    }
}
