/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspi_ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimerTask;

/**
 *
 * @author Darkp
 */
class fastTaskTimer extends TimerTask {

    private static int minutes = 0;
    
    
    @Override
    public void run() {
        
        Calendar time = Calendar.getInstance();
        if (minutes == time.get(Calendar.MINUTE)) {
            return;
            //no change
        }
        
        minutes = time.get(Calendar.MINUTE);
        
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        String displayTime = formatTime.format(time.getTime());
        DisplayController.setTime(displayTime);
        
        String dateText = "";
        dateText += time.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG_STANDALONE, Locale.GERMANY);
        dateText += ", " + Integer.toString(time.get(Calendar.DAY_OF_MONTH));
        dateText += ". " + time.getDisplayName(Calendar.MONTH, Calendar.LONG_STANDALONE, Locale.GERMANY);
        DisplayController.setDate(dateText);
        
        System.out.println("5 sec update: " + displayTime + " | " + dateText);
    }
    
}
