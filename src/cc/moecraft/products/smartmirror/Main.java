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
    //TODO: 日期
    public void start(Stage stage) throws Exception
    {
        instance = this;

        checkConfig();
        checkLang();

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
        configFile = new File(this.getDataFolder() + "/Config.yml");
        log("[配置] DataFolder = " + getDataFolder());
        config = YamlConfiguration.loadConfiguration(configFile);
        config.options().copyDefaults(true);

        if (!(config.contains("Generated")))
        {
            log("[配置] 检测到配置文件是新的, 正在储存默认配置");
            config.addDefault("Generated", true);
            //通用设置
            config.addDefault("General.Language", "zh_CN");
            //时钟
            config.addDefault("DigitalClock.Position.X", 40);
            config.addDefault("DigitalClock.Position.Y", 120);
            config.addDefault("DigitalClock.Clock.Font.Size", 76);
            config.addDefault("DigitalClock.Clock.Font.Name", "Exo Thin");
            config.addDefault("DigitalClock.Clock.Font.Color.Red", 255);
            config.addDefault("DigitalClock.Clock.Font.Color.Green", 255);
            config.addDefault("DigitalClock.Clock.Font.Color.Blue", 255);
            config.addDefault("DigitalClock.Clock.Time.UseTwentyFourHours", true);
            //时钟下面的日期
            config.addDefault("DigitalClock.Date.Show", true);
            config.addDefault("DigitalClock.Date.Font.Size", 40);
            config.addDefault("DigitalClock.Date.Font.Name", "Exo Thin");
            config.addDefault("DigitalClock.Date.Font.Color.Red", 255);
            config.addDefault("DigitalClock.Date.Font.Color.Green", 255);
            config.addDefault("DigitalClock.Date.Font.Color.Blue", 255);

            saveConfig();
            return false;
        }
        else
        {
            log("[配置] 检测到配置文件不是新的");
            return true;
        }
    }

    public static YamlConfiguration lang;
    public static File langFile;

    public boolean checkLang()
    {
        log("[语言] 正在检查语言文件");
        langFile = new File(this.getDataFolder() + "/Language.yml");
        log("[语言] DataFolder = " + getDataFolder());
        lang = YamlConfiguration.loadConfiguration(langFile);
        lang.options().copyDefaults(true);

        if (!(lang.contains("Generated")))
        {
            log("[语言] 检测到语言文件是新的, 正在储存默认配置");
            lang.addDefault("Generated", true);
            //时钟
            lang.addDefault("zh_CN.DigitalClock.Clock.Text.TwentyFourHours", "%s:%s");
            lang.addDefault("en_US.DigitalClock.Clock.Text.TwentyFourHours", "%s:%s");
            lang.addDefault("zh_CN.DigitalClock.Clock.Text.AMPM", "%s:%s %s");
            lang.addDefault("en_US.DigitalClock.Clock.Text.AMPM", "%s:%s %s");

            saveLang();
            return false;
        }
        else
        {
            log("[语言] 检测到语言文件不是新的");
            return true;
        }
    }

    public static String getLang(String s)
    {
        return lang.getString(config.getString("General.Language") + "." + s);
    }

    /**
     * 保存配置文件
     * Save config file
     * @return 是否保存成功
     */
    public static boolean saveConfig()
    {
        try
        {
            config.save(configFile);
            Debug("[配置] 已保存配置");
            return true;
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
            Debug("[配置] 配置文件保存失败");
            return false;
        }
    }

    public static boolean saveLang()
    {
        try
        {
            lang.save(langFile);
            Debug("[语言] 已保存语言文件");
            return true;
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
            Debug("[语言] 语言文件保存失败");
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
