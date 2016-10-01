/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth;

import java.util.TimerTask;
import javafx.application.Platform;
import rearth.Helpers.TimeService.Zeit;
import rearth.networking.ComputerConnection;
import static rearth.networking.ComputerConnection.*;

/**
 *
 * @author Darkp
 */

public class fastTaskTimer extends TimerTask{
    
    @Override
    public void run() {
        
        Raspi_HomeUI GUI = Raspi_HomeUI.getInstance();
        
        Platform.runLater(GUI::updateTime);
        
        Runnable myRunnable = this::getPCData;

        Thread thread = new Thread(myRunnable);
        thread.start();
        
    }
    
    private void getPCData() {
        
        ComputerConnection connection = new ComputerConnection();
        String data = connection.Communicate("");
        System.out.println("PC Stats: " + "CPU=" + getCPUusage(data) + "% RAM=" + getRAMusage(data) + "% RAMused=" + getRAMused(data) + " Zeit=" +new Zeit().toString(true));
        
    }
    
}
