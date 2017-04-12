package cc.moecraft.products.smartmirror.elements.digitalclock;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.time.LocalDate;

import static cc.moecraft.products.smartmirror.Main.config;

/**
 * Created by Kilpikonna on 2017/4/12 0012.
 */
public class DCalendar extends Pane
{
    public DCalendar() {
        bindToTime();
    }

    private void bindToTime()
    {
        DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node popupContent = datePickerSkin.getPopupContent();

        //popupContent.applyCss();
        popupContent.lookup(".month-year-pane").setVisible(false);
        popupContent.setLayoutX(config.getInt());

        //getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        //root.setCenter(popupContent);
    }
}
