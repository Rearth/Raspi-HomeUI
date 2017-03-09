/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.IO;

import java.util.Timer;
import java.util.TimerTask;
import rearth.HomeUI_DesignController;

/**
 *
 * @author Darkp
 */
public class AnalogHandler extends Thread {
    
    private int avgLDR = 0;
    private int avgSound = 0;
    private int LDRhigh = 0;
    
    @Override
    public void run() {
        System.out.println("Creating Analog Handler");
        onUpdate();
    }
    
    private void onUpdate() {
        
        //printStats();
        calcAvg();
        
        if (avgLDR >= 450 && HomeUI_DesignController.autoDark && LDRhigh <= 100) {
            LDRhigh += 3;
        }
        if (LDRhigh >= 50) {
            HomeUI_DesignController.switchDark(true);
        } else {
            HomeUI_DesignController.switchDark(false);
        }
        
        if (LDRhigh > 0) {
            LDRhigh--;
        }
        
        //prepare for next update!
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onUpdate();
            }
        }, 100);
    }
    
    private void calcAvg() {
        
        int total = 0;
        for (int i : PinHandler.getInstance().LDR.getValues()) {
            if (i != 0)
                total += i;
        }
        avgLDR = total / PinHandler.getInstance().LDR.getValues().length;
        
        total = 0;
        for (int i : PinHandler.getInstance().ClapSensor.getValues()) {
            if (i != 0)
                total += i;
        }
        avgSound = total / PinHandler.getInstance().ClapSensor.getValues().length;
    }
    
    private void printStats() {
        System.out.println("Sound Sensor: " + PinHandler.getInstance().ClapSensor.getValues()[0]);
        System.out.println("LDR:          " + PinHandler.getInstance().LDR.getValues()[0]);
    }
}
