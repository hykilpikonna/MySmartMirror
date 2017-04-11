package cc.moecraft.products.smartmirror;

import javafx.animation.*;
import javafx.event.*;
import javafx.scene.control.Label;
import javafx.util.Duration;
import cc.moecraft.products.smartmirror.Essentials.StringUtils;

import java.util.Calendar;

import static cc.moecraft.products.smartmirror.Main.config;
import static cc.moecraft.products.smartmirror.Main.exoThinFontCSS;
import static cc.moecraft.products.smartmirror.logger.Debug;

public class DigitalClock extends Label
{
    public DigitalClock() {
        bindToTime();
    }

    private void bindToTime()
    {
        Calendar time = Calendar.getInstance();
        Debug("[数字表][配置] DigitalClock.Time.UseTwentyFourHours = " + config.getBoolean("DigitalClock.Time.UseTwentyFourHours"));
        EventHandler e = (EventHandler<ActionEvent>) actionEvent -> {
            if (config.getBoolean("DigitalClock.Time.UseTwentyFourHours"))
            {
                String hourString = StringUtils.pad(2, '0', time.get(Calendar.HOUR_OF_DAY) + "");
                String minuteString = StringUtils.pad(2, '0', time.get(Calendar.MINUTE) + "");
                setText(hourString + ":" + minuteString);
            }
            else
            {
                String hourString = StringUtils.pad(2, '0', time.get(Calendar.HOUR) == 0 ? "12" : time.get(Calendar.HOUR) + "");
                String minuteString = StringUtils.pad(2, '0', time.get(Calendar.MINUTE) + "");
                if (time.get(Calendar.AM_PM) == 1)
                {
                    setText(hourString + ":" + minuteString + " PM");
                }
                else
                {
                    setText(hourString + ":" + minuteString + "AM");
                }
            }
        };

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), e), new KeyFrame(Duration.seconds(1)));
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        setStyle(
                " -fx-font: "      + config.getInt("DigitalClock.Font.Size") + "pt \"" + config.getString("DigitalClock.Font.Name") + "\";" +
                " -fx-text-fill: rgb(" + config.getInt("DigitalClock.Font.Color.Red") + ", " + config.getInt("DigitalClock.Font.Color.Green") + ", " + config.getInt("DigitalClock.Font.Color.Blue") + ");"
        );
        getStylesheets().add(exoThinFontCSS);
        Debug("[数字表][坐标] X = " + config.getInt("DigitalClock.Position.X") + "; Y = " + config.getInt("DigitalClock.Position.Y"));
        setLayoutX(config.getInt("DigitalClock.Position.X"));
        setLayoutY(config.getInt("DigitalClock.Position.Y"));
    }
}
