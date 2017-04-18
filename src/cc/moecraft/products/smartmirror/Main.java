package cc.moecraft.products.smartmirror;

import cc.moecraft.products.smartmirror.configuration.file.YamlConfiguration;
import cc.moecraft.products.smartmirror.elements.digitalclock.DCalendar;
import cc.moecraft.products.smartmirror.elements.digitalclock.DClock;
import cc.moecraft.products.smartmirror.elements.digitalclock.DDate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

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
    public static String exoLightFontCSS;
    @Override
    public void start(Stage stage) throws Exception
    {
        instance = this;

        checkConfig();
        checkLang();

        exoThinFontCSS = Main.class.getResource("css/exothin/exothin.css").toExternalForm();
        exoLightFontCSS = Main.class.getResource("css/exolight/exolight.css").toExternalForm();

        root = FXMLLoader.load(getClass().getResource("SmartMirror.fxml"));
        root.setStyle("-fx-background-color: #000000");

        Pane digitalClockPane = new Pane();
        digitalClockPane.getStylesheets().add(exoThinFontCSS);
        digitalClockPane.getChildren().add(new DClock());
        digitalClockPane.getChildren().add(new DDate());

        Pane dCalendar = new Pane();
        dCalendar.getStylesheets().add(exoLightFontCSS);
        dCalendar.getChildren().add((new DCalendar()).getView());

        //digitalClockPane.getChildren().add(dCalendar.getView());

        root.getChildren().add(digitalClockPane);
        root.getChildren().add(dCalendar);
        scene = new Scene(root, screenSizeWidth, screenSizeHeight);

        Main.stage = stage;
        Main.stage.setTitle("Smart Mirror Snapshot");
        Main.stage.setScene(scene);
        Main.stage.setFullScreen(true);
        Main.stage.setAlwaysOnTop(true);

        Main.stage.setFullScreenExitHint("");

        Main.stage.show();
    }

    public static class LocaleCell extends ListCell<Locale> {
        @Override
        public void updateItem(Locale locale, boolean empty) {
            super.updateItem(locale, empty);
            setText(locale == null ? null : locale.getDisplayName(locale));
        }
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
            config.addDefault("General.Language", "en_US");
            //时钟
            config.addDefault("DClock.Position.X", 40);
            config.addDefault("DClock.Position.Y", 120);
            config.addDefault("DClock.Clock.Font.Size", 76);
            config.addDefault("DClock.Clock.Font.Name", "Exo Thin");
            config.addDefault("DClock.Clock.Font.Layout", "Left");
            config.addDefault("DClock.Clock.Font.Color.Red", 255);
            config.addDefault("DClock.Clock.Font.Color.Green", 255);
            config.addDefault("DClock.Clock.Font.Color.Blue", 255);
            config.addDefault("DClock.Clock.Time.UseTwentyFourHours", true);
            //时钟下面的日期
            config.addDefault("DClock.DDate.Position.Offset.X", 0);
            config.addDefault("DClock.DDate.Position.Offset.Y", 80);
            config.addDefault("DClock.DDate.Show", true);
            config.addDefault("DClock.DDate.Font.Size", 40);
            config.addDefault("DClock.DDate.Font.Name", "Exo Thin");
            config.addDefault("DClock.DDate.Font.Layout", "Left");
            config.addDefault("DClock.DDate.Font.Color.Red", 255);
            config.addDefault("DClock.DDate.Font.Color.Green", 255);
            config.addDefault("DClock.DDate.Font.Color.Blue", 255);
            //日期下面的日历
            config.addDefault("DCalendar.Position.Offset.X", 0);
            config.addDefault("DCalendar.Position.Offset.Y", 180);
            config.addDefault("DCalendar.Font.Name", "Exo Light");
            config.addDefault("DCalendar.Font.Size", 18);
            config.addDefault("DCalendar.Headers.Font.Color.Red", 255);
            config.addDefault("DCalendar.Headers.Font.Color.Green", 255);
            config.addDefault("DCalendar.Headers.Font.Color.Blue", 255);
            config.addDefault("DCalendar.Dates.InMonth.Font.Color.Red", 255);
            config.addDefault("DCalendar.Dates.InMonth.Font.Color.Green", 255);
            config.addDefault("DCalendar.Dates.InMonth.Font.Color.Blue", 255);
            config.addDefault("DCalendar.Dates.NotInMonth.Font.Color.Red", 255);
            config.addDefault("DCalendar.Dates.NotInMonth.Font.Color.Green", 255);
            config.addDefault("DCalendar.Dates.NotInMonth.Font.Color.Blue", 255);


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
            lang.addDefault("zh_CN.DClock.Clock.Text.TwentyFourHours", "%s:%s");
            lang.addDefault("en_US.DClock.Clock.Text.TwentyFourHours", "%s:%s");
            lang.addDefault("zh_CN.DClock.Clock.Text.AMPM", "%s:%s %s");
            lang.addDefault("en_US.DClock.Clock.Text.AMPM", "%s:%s %s");
            //日期
            lang.addDefault("zh_CN.DClock.DDate.Text.Format", "%YEAR%年 %MONTH%月 %DATE%日\n星期%DIW%");
            lang.addDefault("en_US.DClock.DDate.Text.Format", "%DATE% %MONTH% %YEAR%\n%DIW%");
            lang.addDefault("zh_CN.DClock.DDate.Text.Month.1", "一");
            lang.addDefault("zh_CN.DClock.DDate.Text.Month.2", "二");
            lang.addDefault("zh_CN.DClock.DDate.Text.Month.3", "三");
            lang.addDefault("zh_CN.DClock.DDate.Text.Month.4", "四");
            lang.addDefault("zh_CN.DClock.DDate.Text.Month.5", "五");
            lang.addDefault("zh_CN.DClock.DDate.Text.Month.6", "六");
            lang.addDefault("zh_CN.DClock.DDate.Text.Month.7", "七");
            lang.addDefault("zh_CN.DClock.DDate.Text.Month.8", "八");
            lang.addDefault("zh_CN.DClock.DDate.Text.Month.9", "九");
            lang.addDefault("zh_CN.DClock.DDate.Text.Month.10", "十");
            lang.addDefault("zh_CN.DClock.DDate.Text.Month.11", "十一");
            lang.addDefault("zh_CN.DClock.DDate.Text.Month.12", "十二");
            lang.addDefault("en_US.DClock.DDate.Text.Month.1", "January");
            lang.addDefault("en_US.DClock.DDate.Text.Month.2", "February");
            lang.addDefault("en_US.DClock.DDate.Text.Month.3", "March");
            lang.addDefault("en_US.DClock.DDate.Text.Month.4", "April");
            lang.addDefault("en_US.DClock.DDate.Text.Month.5", "May");
            lang.addDefault("en_US.DClock.DDate.Text.Month.6", "June");
            lang.addDefault("en_US.DClock.DDate.Text.Month.7", "July");
            lang.addDefault("en_US.DClock.DDate.Text.Month.8", "August");
            lang.addDefault("en_US.DClock.DDate.Text.Month.9", "September");
            lang.addDefault("en_US.DClock.DDate.Text.Month.10", "October");
            lang.addDefault("en_US.DClock.DDate.Text.Month.11", "November");
            lang.addDefault("en_US.DClock.DDate.Text.Month.12", "December");
            lang.addDefault("zh_CN.DClock.DDate.Text.DayInWeek.1", "一");
            lang.addDefault("zh_CN.DClock.DDate.Text.DayInWeek.2", "二");
            lang.addDefault("zh_CN.DClock.DDate.Text.DayInWeek.3", "三");
            lang.addDefault("zh_CN.DClock.DDate.Text.DayInWeek.4", "四");
            lang.addDefault("zh_CN.DClock.DDate.Text.DayInWeek.5", "五");
            lang.addDefault("zh_CN.DClock.DDate.Text.DayInWeek.6", "六");
            lang.addDefault("zh_CN.DClock.DDate.Text.DayInWeek.7", "日");
            lang.addDefault("en_US.DClock.DDate.Text.DayInWeek.1", "Monday");
            lang.addDefault("en_US.DClock.DDate.Text.DayInWeek.2", "Tuesday");
            lang.addDefault("en_US.DClock.DDate.Text.DayInWeek.3", "Wednesday");
            lang.addDefault("en_US.DClock.DDate.Text.DayInWeek.4", "Thursday");
            lang.addDefault("en_US.DClock.DDate.Text.DayInWeek.5", "Friday");
            lang.addDefault("en_US.DClock.DDate.Text.DayInWeek.6", "Saturday");
            lang.addDefault("en_US.DClock.DDate.Text.DayInWeek.7", "Sunday");
            //TODO: 特殊节日
            //日历
            lang.addDefault("zh_CN.DCalendar.Text.Format", "周%DIW%");
            lang.addDefault("en_US.DCalendar.Text.Format", "%DIW%");
            lang.addDefault("zh_CN.DCalendar.Text.DayInWeek.1", "日");
            lang.addDefault("zh_CN.DCalendar.Text.DayInWeek.2", "一");
            lang.addDefault("zh_CN.DCalendar.Text.DayInWeek.3", "二");
            lang.addDefault("zh_CN.DCalendar.Text.DayInWeek.4", "三");
            lang.addDefault("zh_CN.DCalendar.Text.DayInWeek.5", "四");
            lang.addDefault("zh_CN.DCalendar.Text.DayInWeek.6", "五");
            lang.addDefault("zh_CN.DCalendar.Text.DayInWeek.7", "六");
            lang.addDefault("en_US.DCalendar.Text.DayInWeek.1", "Sun");
            lang.addDefault("en_US.DCalendar.Text.DayInWeek.2", "Mon");
            lang.addDefault("en_US.DCalendar.Text.DayInWeek.3", "Tue");
            lang.addDefault("en_US.DCalendar.Text.DayInWeek.4", "Wed");
            lang.addDefault("en_US.DCalendar.Text.DayInWeek.5", "Thu");
            lang.addDefault("en_US.DCalendar.Text.DayInWeek.6", "Fri");
            lang.addDefault("en_US.DCalendar.Text.DayInWeek.7", "Sat");

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
