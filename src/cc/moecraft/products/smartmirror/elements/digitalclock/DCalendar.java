package cc.moecraft.products.smartmirror.elements.digitalclock;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static cc.moecraft.products.smartmirror.Main.config;
import static cc.moecraft.products.smartmirror.Main.exoLightFontCSS;
import static cc.moecraft.products.smartmirror.Main.getLang;
import static cc.moecraft.products.smartmirror.logger.Debug;

public class DCalendar
{
    private final ObjectProperty<YearMonth> month = new SimpleObjectProperty<>();

    private final ObjectProperty<Locale> locale = new SimpleObjectProperty<>(Locale.ENGLISH);

    private final BorderPane view ;
    private final GridPane calendar ;

    public DCalendar(YearMonth month) {
        view = new BorderPane();
        view.getStyleClass().add("calendar");
        calendar = new GridPane();
        calendar.getStyleClass().add("calendar-grid");
        calendar.setGridLinesVisible(true);

        this.month.addListener((obs, oldMonth, newMonth) ->
                rebuildCalendar());

        this.locale.addListener((obs, oldLocale, newLocale) ->
                rebuildCalendar());

        view.setCenter(calendar);

        view.getStylesheets().add(getClass().getResource("calendar.css").toExternalForm());

        setMonth(month);
    }

    public DCalendar() {
        this(YearMonth.now()) ;
    }

    public void nextMonth() {
        month.set(month.get().plusMonths(1));
    }

    public void previousMonth() {
        month.set(month.get().minusMonths(1));
    }

    private void rebuildCalendar()
    {
        calendar.getChildren().clear();

        WeekFields weekFields = WeekFields.of(locale.get());

        LocalDate first = month.get().atDay(1);

        Debug("first = " + first.toString());
        Debug("weekFields.dayOfWeek().toString() = " + weekFields.dayOfWeek().toString());
        Debug("first.get(weekFields.dayOfWeek()) = " + first.get(weekFields.dayOfWeek()));
        int dayOfWeekOfFirst = first.get(weekFields.dayOfWeek()) ;

        // column headers:
        for (int dayOfWeek = 1 ; dayOfWeek < 8 ; dayOfWeek++)
        {
            Text t = new Text(getLang("DCalendar.Text.DayInWeek." + dayOfWeek));
            t.setFill(Paint.valueOf("rgb(" +
                    config.getInt("DCalendar.Headers.Font.Color.Red") + ", " +
                    config.getInt("DCalendar.Headers.Font.Color.Green") + ", " +
                    config.getInt("DCalendar.Headers.Font.Color.Blue") + ");")
            );
            GridPane.setHalignment(t, HPos.CENTER);
            calendar.add(t, dayOfWeek - 1, 0);
        }

        LocalDate firstDisplayedDate = first.minusDays(dayOfWeekOfFirst - 1);
        LocalDate last = month.get().atEndOfMonth() ;
        int dayOfWeekOfLast = last.get(weekFields.dayOfWeek());
        LocalDate lastDisplayedDate = last.plusDays(7 - dayOfWeekOfLast);

        PseudoClass beforeMonth = PseudoClass.getPseudoClass("before-display-month");
        PseudoClass afterMonth = PseudoClass.getPseudoClass("after-display-month");

        for (LocalDate date = firstDisplayedDate ; ! date.isAfter(lastDisplayedDate) ; date = date.plusDays(1))
        {
            Text t = new Text(String.valueOf(date.getDayOfMonth()));
            t.getStyleClass().add("calendar-cell");
            t.setTextAlignment(TextAlignment.CENTER);
            t.pseudoClassStateChanged(beforeMonth, date.isBefore(first));
            t.pseudoClassStateChanged(afterMonth, date.isAfter(last));
            t.setFill(Paint.valueOf("rgb(" +
                    config.getInt("DCalendar.Dates.InMonth.Font.Color.Red") + ", " +
                    config.getInt("DCalendar.Dates.InMonth.Font.Color.Green") + ", " +
                    config.getInt("DCalendar.Dates.InMonth.Font.Color.Blue") + ");")
            );

            GridPane.setHalignment(t, HPos.CENTER);

            int dayOfWeek = date.get(weekFields.dayOfWeek()) ;
            int daysSinceFirstDisplayed = (int) firstDisplayedDate.until(date, ChronoUnit.DAYS);
            int weeksSinceFirstDisplayed = daysSinceFirstDisplayed / 7 ;

            calendar.add(t, dayOfWeek - 1, weeksSinceFirstDisplayed + 1);
            Debug(t.toString());
        }
    }

    public Node getView() {
        view.getStylesheets().add(exoLightFontCSS);
        BorderPane output = view;
        view.setStyle(
                        " -fx-font: "      + config.getInt("DCalendar.Font.Size") + "pt \"" + config.getString("DCalendar.Font.Name") + "\";"
        );

        view.setLayoutX(config.getInt("DClock.Position.X") +
                //config.getInt("DClock.DDate.Position.Offset.X") +
                config.getInt("DCalendar.Position.Offset.X"));
        view.setLayoutY(config.getInt("DClock.Position.Y") +
                //config.getInt("DClock.DDate.Position.Offset.Y") +
                config.getInt("DCalendar.Position.Offset.Y"));
        return view ;
    }

    public final ObjectProperty<YearMonth> monthProperty() {
        return this.month;
    }

    public final YearMonth getMonth() {
        return this.monthProperty().get();
    }

    public final void setMonth(final YearMonth month) {
        this.monthProperty().set(month);
    }

    public final ObjectProperty<Locale> localeProperty() {
        return this.locale;
    }

    public final java.util.Locale getLocale() {
        return this.localeProperty().get();
    }

    public final void setLocale(final java.util.Locale locale) {
        this.localeProperty().set(locale);
    }

}
