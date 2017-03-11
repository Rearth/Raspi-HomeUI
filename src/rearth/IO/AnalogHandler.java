/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.IO;

import java.util.Arrays;
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
    private int avgSoundDif = 40;
    private int LDRhigh = 0;
    private boolean clapUsed = false;
    
    public static boolean clapEnabled = true;
    
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
        
        if (!clapUsed && clapEnabled) {
            checkClap();
        }
        
        //prepare for next update!
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onUpdate();
            }
        }, 200);
    }
    
    private void calcAvg() {
        
        int total = 0;
        for (int i : PinHandler.getInstance().LDR.getValues()) {
            if (i != 0)
                total += i;
        }
        avgLDR = total / PinHandler.getInstance().LDR.getValues().length;
        
        total = 0;
        int totaldif = 0;
        int lastvalue = 500;
        for (int i : PinHandler.getInstance().ClapSensor.getValues()) {
            if (i != 0)
                total += i;
            try {
                totaldif += dif(i, lastvalue);
            } catch (ArrayIndexOutOfBoundsException ex) { }
            lastvalue = i;
        }
        avgSoundDif = totaldif / PinHandler.getInstance().ClapSensor.getValues().length;
        avgSound = total / PinHandler.getInstance().ClapSensor.getValues().length;
    }
    
    private void printStats() {
        System.out.println("Sound Sensor: " + PinHandler.getInstance().ClapSensor.getValues()[0]);
        System.out.println("LDR:          " + PinHandler.getInstance().LDR.getValues()[0]);
    }
    
    private int claps = 0;
    private boolean blocked = false;

    private void checkClap() {
        
        if (blocked) {
            return;
        }
        
        int values[] = PinHandler.getInstance().ClapSensor.getValues();
        //System.out.println("values: " + Arrays.toString(values));
        
        for (int i = 35; i < 118; i++) {
            
            int highpeaks = 0;
            
            int peaks = 0;
            for (int k=0; k < 10; k++) {
                //System.out.println("dif: " + dif(values[i + k], avgSound) + " value: " + values[i + k] + " avg=" + avgSound);
                if (dif(values[i + k], avgSound) > (int) (avgSoundDif * 1.1) + 20) {
                    if (dif(values[i + k], avgSound) > avgSoundDif * 3 + 40) {
                        highpeaks++;
                    }
                    //System.out.println("found peak");
                    peaks++;
                }
            }
            
            //System.out.println("peaks: " + peaks + " avg=" + avgSound);
            if (peaks > 4 && highpeaks > 0) {
                claps++;
                i += 10;
                System.out.println("found possible clap");
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        claps = 0;
                    }
                }, 800);
            }
        }
        
        System.out.println("found claps: " + claps + " avgSoundDif=" + avgSoundDif);
        if (claps == 2) {
            if (validateNotMore(values)) {
                foundDoubleClap();
            } else {
                System.out.println("blocking clap sensor");
                blocked = true;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        blocked = false;
                    }
                }, 1600);
            }
        }
        claps = 0;
    }
    
    private boolean validateNotMore(int values[]) {
        
        int clapN = 0;
        
        for (int i = 1; i < 118; i++) {
            
            int highpeaks = 0;
            int peaks = 0;
            for (int k=0; k < 10; k++) {
                //System.out.println("dif: " + dif(values[i + k], avgSound) + " value: " + values[i + k] + " avg=" + avgSound);
                if (dif(values[i + k], avgSound) > (int) (avgSoundDif * 1.2) + 20) {
                    if (dif(values[i + k], avgSound) > avgSoundDif * 3) {
                        highpeaks++;
                    }
                    //System.out.println("found peak");
                    peaks++;
                }
            }
            
            //System.out.println("peaks: " + peaks + " avg=" + avgSound);
            if (peaks > 4 && highpeaks > 0) {
                clapN++;
                i += 10;
            }
        }
        System.out.println("checking if too many claps: " + clapN);
        return clapN == 2;
    }
    
    public static int dif (int a, int b) {
        if (a > b) {
            return a - b;
        }
        return b - a;
    }
    
    private void foundDoubleClap() {
        
        System.out.println("double clap!");
        
        clapUsed = true;
        onDoubleClap();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                clapUsed = false;
            }
        }, 2000);
        
    }
    
    public static void onDoubleClap() {
        HomeUI_DesignController.getInstance().switchState();
    }
}
