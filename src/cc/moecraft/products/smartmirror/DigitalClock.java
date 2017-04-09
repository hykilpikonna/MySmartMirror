package cc.moecraft.products.smartmirror;

import javafx.animation.*;
import javafx.event.*;
import javafx.scene.control.Label;
import javafx.util.Duration;
import cc.moecraft.products.smartmirror.Essentials.StringUtils;

import java.util.Calendar;

public class DigitalClock extends Label
{
    public DigitalClock() {
        bindToTime();
    }

    private void bindToTime()
    {
        EventHandler e = (EventHandler<ActionEvent>) actionEvent -> {
            Calendar time = Calendar.getInstance();
            String hourString = StringUtils.pad(2, '0', time.get(Calendar.HOUR) == 0 ? "24" : time.get(Calendar.HOUR) + "");
            String minuteString = StringUtils.pad(2, '0', time.get(Calendar.MINUTE) + "");
            //String secondString = StringUtils.pad(2, '0', time.get(Calendar.SECOND) + "");
            setText(hourString + ":" + minuteString);
        };

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), e), new KeyFrame(Duration.seconds(1)));
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
