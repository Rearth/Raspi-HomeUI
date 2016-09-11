/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth;

import java.util.TimerTask;
import javafx.application.Platform;

/**
 *
 * @author Darkp
 */
public class fastTaskTimer extends TimerTask{
    
    @Override
    public void run() {
        
        Raspi_HomeUI GUI = Raspi_HomeUI.getInstance();
        
        Platform.runLater(GUI::updateTime);
        
    }  
    
}
