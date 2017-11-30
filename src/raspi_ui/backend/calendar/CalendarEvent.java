package raspi_ui.backend.calendar;

import raspi_ui.backend.calendar.json.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarEvent implements  Comparable<CalendarEvent> {

    private final String Name;
    private final String Description;
    private final String StartAt;
    private final String EndAt;
    private final CalenderInfo info;
    private final Item item;

    public CalendarEvent(String Name, String Description, String startAt, String EndAt, CalenderInfo info, Item item) {
        this.Name = Name;
        this.Description = Description;
        this.StartAt = startAt;
        this.EndAt = EndAt;
        this.info = info;
        this.item = item;

        //System.out.println("creating item " + Name + " recurrence: " + item.getRecurrence());
    }

    @Override
    public String toString() {
        return Name + " | from: " + StartAt + " | till: " + EndAt + " source calendar: " + info.getSummary();
    }

    @Override
    public int compareTo(CalendarEvent o) {
        return getDate().compareTo(o.getDate());
    }

    public Date getDate() {
        SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateText = StartAt;
        dateText = dateText.substring(0, dateText.length() - 10);

        try {
            return df.parse(dateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }
}
