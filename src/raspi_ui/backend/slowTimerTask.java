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
import java.util.*;

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
        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), 0,0);
        Calendar next = Calendar.getInstance();
        next.set(next.get(Calendar.YEAR), next.get(Calendar.MONTH), next.get(Calendar.DATE), 0,0);
        next.add(Calendar.DATE, 2);


        SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

        List<CalendarEvent> Events = CalendarData.getEventsInTime(df.format(now.getTime()), df.format(next.getTime()));

        System.out.println();
        System.out.println("---------------------done gathering events, sorting now---------------------");
        System.out.println();

        Collections.sort(Events);
        for (CalendarEvent event : Events) {
            System.out.println(event);
        }

    }
    
}
