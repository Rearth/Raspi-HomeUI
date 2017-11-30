package raspi_ui.backend.calendar;

import raspi_ui.backend.calendar.json.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarEvent implements  Comparable<CalendarEvent> {

    //TODO
    //add possibility to click on items for more info

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

    public int dayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getDate());
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public String getNiceTime() {
        SimpleDateFormat df =  new SimpleDateFormat("HH:mm");
        return df.format(getDate());
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getStartAt() {
        return StartAt;
    }

    public String getEndAt() {
        return EndAt;
    }

    public CalenderInfo getInfo() {
        return info;
    }

    public Item getItem() {
        return item;
    }
}
