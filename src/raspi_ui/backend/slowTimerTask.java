/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspi_ui.backend;

import raspi_ui.DisplayController;
import raspi_ui.Raspi_UI;
import raspi_ui.backend.calendar.CalendarData;
import raspi_ui.backend.calendar.CalendarEvent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimerTask;

/**
 *
 * @author Darkp
 */
public class slowTimerTask extends TimerTask {

    private static int minutes = 0;
    
    
    @Override
    public void run() {

        try {
            initCalendar();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("hourly tasks done");
    }

    private void initCalendar() throws IOException {

        System.out.println("initing calendar (hourly)");
        Calendar now = Calendar.getInstance();
        Calendar next = Calendar.getInstance();
        next.add(Calendar.DATE, 1);

        SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

        List<CalendarEvent> Events = CalendarData.getEventsInTime(df.format(now.getTime()), df.format(next.getTime()));

    }
    
}
