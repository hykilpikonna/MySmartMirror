package cc.moecraft.products.smartmirror.elements.digitalclock;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.Calendar;

import static cc.moecraft.products.smartmirror.Main.config;
import static cc.moecraft.products.smartmirror.Main.getLang;
import static cc.moecraft.products.smartmirror.Main.lang;
import static cc.moecraft.products.smartmirror.logger.Debug;

public class DDate extends Text
{
    public DDate() {
        bindToTime();
    }

    private void bindToTime()
    {
        Calendar time = Calendar.getInstance();
        EventHandler e = (EventHandler<ActionEvent>) actionEvent -> {
            String tempStirng = getLang("DClock.DDate.Text.Format")
                    .replace("%DATE%", "" + time.get(Calendar.DATE))
                    .replace("%MONTH%", getLang("DClock.DDate.Text.Month." + time.get(Calendar.MONTH)))
                    .replace("%YEAR%", "" + time.get(Calendar.YEAR))
                    .replace("%DIW%", getLang("DClock.DDate.Text.DayInWeek." + time.get(Calendar.DAY_OF_WEEK)));
            //Debug(tempStirng);
            setText(tempStirng);
        };

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), e), new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        setStyle(
                " -fx-font: "      + config.getInt("DClock.DDate.Font.Size") + "pt \"" + config.getString("DClock.DDate.Font.Name") + "\";"
        );
        setFill(Paint.valueOf("rgb(" + config.getInt("DClock.DDate.Font.Color.Red") + ", " + config.getInt("DClock.DDate.Font.Color.Green") + ", " + config.getInt("DClock.DDate.Font.Color.Blue") + ");"));

        Debug("[数字表][日期][坐标] X = " + config.getInt("DClock.Position.X") + "; Y = " + (config.getInt("DClock.Position.Y") - 50));
        switch (config.getString("DClock.DDate.Font.Layout"))
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
        setLayoutX(config.getInt("DClock.Position.X") + config.getInt("DClock.DDate.Position.Offset.X"));
        setLayoutY(config.getInt("DClock.Position.Y") + config.getInt("DClock.DDate.Position.Offset.Y"));

    }
}
