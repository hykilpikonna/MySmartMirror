package cc.moecraft.products.smartmirror;

import javafx.animation.*;
import javafx.event.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import cc.moecraft.products.smartmirror.Essentials.StringUtils;

import java.util.Calendar;

import static cc.moecraft.products.smartmirror.Main.config;
import static cc.moecraft.products.smartmirror.Main.exoThinFontCSS;
import static cc.moecraft.products.smartmirror.Main.getLang;
import static cc.moecraft.products.smartmirror.logger.Debug;

public class DigitalClock extends Text
{
    public DigitalClock() {
        bindToTime();
    }

    private void bindToTime()
    {
        Calendar time = Calendar.getInstance();
        Debug("[数字表][配置] DigitalClock.Clock.Time.UseTwentyFourHours = " + config.getBoolean("DigitalClock.Clock.Time.UseTwentyFourHours"));
        EventHandler e = (EventHandler<ActionEvent>) actionEvent -> {
            if (config.getBoolean("DigitalClock.Clock.Time.UseTwentyFourHours"))
            {
                String hourString = StringUtils.pad(2, '0', time.get(Calendar.HOUR_OF_DAY) + "");
                String minuteString = StringUtils.pad(2, '0', time.get(Calendar.MINUTE) + "");
                setText(String.format(getLang("DigitalClock.Clock.Text.TwentyFourHours"), hourString, minuteString));
            }
            else
            {
                String hourString = StringUtils.pad(2, '0', time.get(Calendar.HOUR) == 0 ? "12" : time.get(Calendar.HOUR) + "");
                String minuteString = StringUtils.pad(2, '0', time.get(Calendar.MINUTE) + "");
                if (time.get(Calendar.AM_PM) == 1)
                {
                    setText(String.format(getLang("DigitalClock.Clock.Text.AMPM"), hourString, minuteString, "PM"));
                }
                else
                {
                    setText(String.format(getLang("DigitalClock.Clock.Text.AMPM"), hourString, minuteString, "AM"));
                }
            }
        };

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), e), new KeyFrame(Duration.seconds(1)));
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        setStyle(
                " -fx-font: "      + config.getInt("DigitalClock.Clock.Font.Size") + "pt \"" + config.getString("DigitalClock.Clock.Font.Name") + "\";"
        );
        setFill(Paint.valueOf("rgb(" + config.getInt("DigitalClock.Clock.Font.Color.Red") + ", " + config.getInt("DigitalClock.Clock.Font.Color.Green") + ", " + config.getInt("DigitalClock.Clock.Font.Color.Blue") + ");"));

        Debug("[数字表][坐标] X = " + config.getInt("DigitalClock.Position.X") + "; Y = " + config.getInt("DigitalClock.Position.Y"));
        setTextAlignment(TextAlignment.JUSTIFY);
        setLayoutX(config.getInt("DigitalClock.Position.X"));
        setLayoutY(config.getInt("DigitalClock.Position.Y"));

    }
}
