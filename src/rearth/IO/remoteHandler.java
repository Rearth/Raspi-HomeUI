/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.IO;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import rearth.Helpers.StyledSwitch;
import rearth.HomeUI_DesignController;

/**
 *
 * @author Darkp
 */
public class remoteHandler {

    public static final remoteHandler instance = new remoteHandler();
    public final toggleElement bilderLED;
    private lightingState curState = lightingState.Off;
    
    public static void update() {
        if (instance.curState != remoteHandler.lightingState.Auto || HomeUI_DesignController.Sleeping) {
            return;
        }
        
        if (HomeUI_DesignController.nightmode) {
            instance.bilderLED.setOn(true);
        } else {
            instance.bilderLED.setOn(false);
        }
    }
    
    public static void initSleepMode() {
        System.out.println("initing sleep mode for remote handler");
        if (instance.curState != remoteHandler.lightingState.Auto) {
            return;
        }
        
        System.out.println("turning off lights in 90 seconds");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
            System.out.println("turning off lights now");
                instance.bilderLED.setOn(false);
            }
        }, 90*1000);
    }
            
    private enum lightingState {
        Off, On, Auto
    }
    
    private remoteHandler() {
        
        System.out.println("creating remoteHandler");
        
        bilderLED = new remoteHandler.RemoteLight("1361", "1364");
    }
    
    public static void changeLightState(int SelectedOption, StyledSwitch element) {
        System.out.println("czhanging lighting state to: " + SelectedOption);
        instance.curState = lightingState.values()[SelectedOption];
        System.out.println("lighting state is now: " + instance.curState);
        if (instance.curState == lightingState.On) {
            instance.bilderLED.setOn(true);
        } else if (instance.curState == lightingState.Off) {
            instance.bilderLED.setOn(false);
        }
        
    }
    
    public interface toggleElement {
        Boolean isOn();
        void toggle();
        void setOn(Boolean value);
    }
    
    public static final class RemoteLight implements toggleElement {
        
        private final String onCode;
        private final String offCode;
        private Boolean isOn = false;
        
        public RemoteLight(String onCode, String offCode) {
            System.out.println("creating remoteLight with onCode=" + onCode + " offCode=" + offCode);
            this.onCode = onCode;
            this.offCode = offCode;
            
            setOn(false);
        }

        @Override
        public Boolean isOn() {
            return isOn;
        }

        @Override
        public void toggle() {
            if (isOn()) {
                setOn(false);
            } else {
                setOn(false);
            }
        }

        @Override
        public void setOn(Boolean value) {
            if (Objects.equals(value, isOn)) {
                return;
            }
            System.out.println("setting light to: " + value);
            isOn = value;
            String usedCode;
            
            usedCode = value ? onCode : offCode;
            
            
            Thread thread = new Thread(){
                @Override
                public void run(){
                    try {
                        System.out.println("total command: " + "sudo /home/pi/433Utils/RPi_utils/codesend " + usedCode + " ...(starting process now)");
                        
                        Process executor = Runtime.getRuntime().exec("sudo /home/pi/433Utils/RPi_utils/codesend " + usedCode);
                        
                        System.out.println("process started; output:");
                        
                        BufferedReader stdout = new BufferedReader(new InputStreamReader(executor.getInputStream()));
                        String line;
                        
                        while ((line = stdout.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(remoteHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            thread.start();
            
        }
    }
    
}
