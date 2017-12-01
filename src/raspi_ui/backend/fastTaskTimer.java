/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspi_ui.backend;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimerTask;

import javafx.application.Platform;
import raspi_ui.DisplayController;
import raspi_ui.Raspi_UI;

/**
 *
 * @author Darkp
 */
public class fastTaskTimer extends TimerTask {

    private static final File sleepOnFile = new File("/tmp/raspi/sleepOn");
    private static final File sleepOffFile = new File("/tmp/raspi/sleepOff");

    private static int minutes = 0;
    
    
    @Override
    public void run() {

        checkAlexaInput();
        
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

    private void checkAlexaInput() {

        if (sleepOnFile.exists()) {

            System.out.println("found command from voice service: start sleep");
            sleepOnFile.delete();
            Platform.runLater(() -> {
                DisplayController.setSleeping(true);
            });
        } else if (sleepOffFile.exists()) {

            System.out.println("found command from voice service: stop sleep");
            sleepOffFile.delete();
            Platform.runLater(() -> {
                DisplayController.setSleeping(false);
            });
        }
    }
    
}
