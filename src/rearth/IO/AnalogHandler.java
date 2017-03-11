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
    //private int avgSound = 0;
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
        
        if (!clapUsed && clapEnabled && avgSoundDif < 150) {    //too noisy
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
        
        int totaldif = 0;
        int lastvalue = 500;
        for (int i : PinHandler.getInstance().ClapSensor.getValues()) {
            try {
                totaldif += dif(i, lastvalue);
            } catch (ArrayIndexOutOfBoundsException ex) { }
            lastvalue = i;
        }
        avgSoundDif = totaldif / PinHandler.getInstance().ClapSensor.getValues().length;
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
        
        for (int i = 1; i < values.length - 41; i++) {
            
            int highpeaks = 0;
            int sameLevels = 0;
            for (int k = 0; k < 40; k++) {
                if (dif(values[i + k], values[i + k - 1]) < 3) {
                    sameLevels++;
                } else if (dif(values[i + k], values[i + k - 1]) > 250) {
                    highpeaks++;
                }
            }
            
            //System.out.println("peaks: " + peaks + " avg=" + avgSound);
            if (sameLevels > 4 && highpeaks > 1) {
                claps++;
                i += 55;
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
        
        System.out.println("found claps: " + claps);
        if (claps == 3) {
            foundDoubleClap();
        }
        claps = 0;
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
        }, 5000);
        
    }
    
    public static void onDoubleClap() {
        HomeUI_DesignController.getInstance().switchState();
    }

    /*private boolean onlyTwo(int[] values) {
        
        int amount = 0;
        for (int i = 1; i < values.length - 50; i++) {
            
            int highpeaks = 0;
            int sameLevels = 0;
            for (int k = 0; k < 49; k++) {
                if (dif(values[i + k], values[i + k - 1]) < 3) {
                    sameLevels++;
                } else if (dif(values[i + k], values[i + k - 1]) > 250) {
                    highpeaks++;
                }
            }
            
            //System.out.println("peaks: " + peaks + " avg=" + avgSound);
            if (sameLevels > 4 && highpeaks > 1) {
                amount++;
                i += 80;
            }
        }
        System.out.println("checking clap num: " + amount);
        
        return amount == 2;
    }*/
}
