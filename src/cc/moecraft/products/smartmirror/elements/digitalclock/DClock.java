package cc.moecraft.products.smartmirror.elements.digitalclock;

import javafx.animation.*;
import javafx.event.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import cc.moecraft.products.smartmirror.essentials.StringUtils;

import java.util.Calendar;

import static cc.moecraft.products.smartmirror.Main.config;
import static cc.moecraft.products.smartmirror.Main.getLang;
import static cc.moecraft.products.smartmirror.logger.Debug;

public class DClock extends Text
{
    public DClock() {
        bindToTime();
    }

    private void bindToTime()
    {
        Calendar time = Calendar.getInstance();
        Debug("[数字表][配置] DClock.Clock.Time.UseTwentyFourHours = " + config.getBoolean("DClock.Clock.Time.UseTwentyFourHours"));
        EventHandler e = (EventHandler<ActionEvent>) actionEvent -> {
            if (config.getBoolean("DClock.Clock.Time.UseTwentyFourHours"))
            {
                String hourString = StringUtils.pad(2, '0', time.get(Calendar.HOUR_OF_DAY) + "");
                String minuteString = StringUtils.pad(2, '0', time.get(Calendar.MINUTE) + "");
                setText(String.format(getLang("DClock.Clock.Text.TwentyFourHours"), hourString, minuteString));
            }
            else
            {
                String hourString = StringUtils.pad(2, '0', time.get(Calendar.HOUR) == 0 ? "12" : time.get(Calendar.HOUR) + "");
                String minuteString = StringUtils.pad(2, '0', time.get(Calendar.MINUTE) + "");
                if (time.get(Calendar.AM_PM) == 1)
                {
                    setText(String.format(getLang("DClock.Clock.Text.AMPM"), hourString, minuteString, "PM"));
                }
                else
                {
                    setText(String.format(getLang("DClock.Clock.Text.AMPM"), hourString, minuteString, "AM"));
                }
            }
        };


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), e), new KeyFrame(Duration.seconds(1)));
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        setStyle(
                " -fx-font: "      + config.getInt("DClock.Clock.Font.Size") + "pt \"" + config.getString("DClock.Clock.Font.Name") + "\";"
        );
        setFill(Paint.valueOf("rgb(" + config.getInt("DClock.Clock.Font.Color.Red") + ", " + config.getInt("DClock.Clock.Font.Color.Green") + ", " + config.getInt("DClock.Clock.Font.Color.Blue") + ");"));

        Debug("[数字表][坐标] X = " + config.getInt("DClock.Position.X") + "; Y = " + config.getInt("DClock.Position.Y"));
        switch (config.getString("DClock.Clock.Font.Layout"))
        {
            case "Left":
                setTextAlignment(TextAlignment.LEFT);
                break;
            case "Right":
                setTextAlignment(TextAlignment.RIGHT);
                break;
            case "Middle":
                setTextAlignment(TextAlignment.CENTER);
                break;
            default:
                setTextAlignment(TextAlignment.LEFT);
                break;
        }
        setLayoutX(config.getInt("DClock.Position.X"));
        setLayoutY(config.getInt("DClock.Position.Y"));
    }
}
