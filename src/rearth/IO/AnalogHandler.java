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
        
        int possibleStarts[] = new int[32];
        int possibleEnds[] = new int[32];
        int testnums = 0;
        
        for (int i = 1; i < values.length - 50; i++) {
            
            if (dif(values[i], values[i + 1]) > avgSoundDif) {
                //maybe start
                int curLows = 0;
                for (int k = i; k < i + 40; k++) {
                    
                    int curDif = dif(values[k], values[k-1]);
                    if (curDif > 0.3 * avgSoundDif && curDif < avgSoundDif * 1.1) {
                        curLows++;
                    } else {
                        curLows = 0;
                    }
                    
                    if (curLows >= 5 && k < 21) {
                        i+=8;
                        break;
                    }
                    
                    if (curLows >= 5 && k > 20) {
                        //found valid part?
                        //System.out.println("found valid parts at i=" + i + " and k=" + k);
                        possibleStarts[testnums] = i;
                        possibleEnds[testnums] = k;
                        //checkLog(i, k);
                        i+=k;
                        testnums++;
                        break;
                    }
                }
            }
            
        }
        
        System.out.println("clap validation targets=" + testnums);
        //valite possible starts/ends
        if (testnums < 3) {
            return;
        }
        
        for (int i = 0; i < testnums; i++) {
            int start = possibleStarts[i];
            int end = possibleEnds[i];
            
            int length = end - start;
            if (length < 20 || length > 50) {
                //too short/too long
                System.out.println("invalid length (" + length + ")");
                continue;
            }
            
            int highPeaks = 0;
            int sameLevels = 0;
            
            //TODO
            for (int k = start; k < end; k++) {
                //find peaks
                if (dif(values[k], values[k-1]) > avgSoundDif + 150) {
                    highPeaks++;
                }
                
                //find sames
                for (int j = start; j < end; j++) {
                    if (dif(values[j], values[k]) < 5) {
                        sameLevels++;
                    }
                }
            }
            
            if (highPeaks < 3) {
                System.out.println("invalid: highPeaks=" + highPeaks);
                //not enough peaks
                continue;                
            }
            
            if (highPeaks <= 10 && sameLevels <=  30) {
                System.out.println("invalid: highPeaks=" + highPeaks + " sames=" + sameLevels);
                //not enough peaks or sames
                continue;
            }
            
            //check before and after
            try {       //before
                int a = 0;
                for (int k = start - 20; k < start; k++) {
                    a += dif(values[k], values[k + 1]);
                }
                
                a = a / 20;
                if (a <= 0.3 * avgSoundDif || a >= avgSoundDif) {
                    //not silent before
                    System.out.println("not silent before (" + a + ")");
                    continue;
                }
                
            } catch (ArrayIndexOutOfBoundsException ex) {}
            
            try {       //after
                int a = 0;
                for (int k = end; k < end + 20; k++) {
                    a += dif(values[k], values[k + 1]);
                }
                
                a = a / 20;
                if (a <= 0.3 * avgSoundDif || a >= avgSoundDif) {
                    //not silent after
                    System.out.println("not silent after (" + a + ")");
                    continue;
                }
                
            } catch (ArrayIndexOutOfBoundsException ex) {}
            
            //valid before/after/peaks or sames/length
            System.out.println("Found Clap! Claps=" + claps + " peaks=" + highPeaks + " sames=" + sameLevels + " length=" + length);
            claps++;
            
        }
        
        /*for (int i = 1; i < values.length - 41; i++) {
            
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
        }*/
        
        System.out.println("found claps: " + claps);
        if (claps == 3) {
            foundtripleClap();
        }
        claps = 0;
    }
    
    public static int dif (int a, int b) {
        if (a > b) {
            return a - b;
        }
        return b - a;
    }
    
    private void foundtripleClap() {
        //actually 3 claps now
        System.out.println("double clap!");
        
        clapUsed = true;
        onTripleClap();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                clapUsed = false;
            }
        }, 3000);
        
    }
    
    public static void onTripleClap() {
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

    private void checkLog(int start, int end) {
        if (PinHandler.getInstance().ClapSensor.isLog()) {
            AnalogInputPin sensor = PinHandler.getInstance().ClapSensor;
            //CSVUtil.writeLine(PinHandler.getInstance().ClapSensor.getWriter(), Integer.toString(Integer.valueOf(line)));
            sensor.setLogMarker(sensor.getLogLine() + start, false);
            sensor.setLogMarker(sensor.getLogLine() + end, true);
        }
    }
}
