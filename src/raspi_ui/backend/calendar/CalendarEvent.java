package raspi_ui.backend.calendar;

import raspi_ui.backend.calendar.json.*;

import javax.management.Descriptor;

public class CalendarEvent {

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
    }

    @Override
    public String toString() {
        return Name + " | from: " + StartAt + " | till: " + EndAt + " source calendar: " + info.getSummary();
    }

}
