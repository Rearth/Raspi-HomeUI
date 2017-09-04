/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.IO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Darkp
 */
public class PinHandler {
    
    public enum DisplayState {
        opened, closed
    }
    
    public final ArrayList<IOPin> Pins = new ArrayList<>();
    public final PWMPin servoDisplay;
    public final AnalogInputPin LDR;
    public final AnalogInputPin ClapSensor;
    
    private static final PinHandler instance = new PinHandler();
    private static final int servoDisplayPosUp = 1;
    private static final int servoDisplayPosDown = 81;
    private static Thread AnalogHandle;
    
    private DisplayState displayState = DisplayState.opened;
    
    private PinHandler() {
        clearGPIO();
        
        servoDisplay = new PWMPin(1, servoDisplayPosUp);
        LDR = new AnalogInputPin(0, 10);
        ClapSensor = new AnalogInputPin(1, 256, 256);
        ClapSensor.setLog(true);
        
        Pins.add(servoDisplay);
        Pins.add(LDR);
        Pins.add(ClapSensor);
        
        AnalogHandle = new AnalogHandler();
        AnalogHandle.start();
    }
    
    public static PinHandler getInstance() {
        return instance;
    }
    
    public void shutdown() {
        for (IOPin pin: Pins) {
            pin.stop();
        }
    }
    
    public static void setDisplayPos(DisplayState state) {
        System.out.println("setting display to " + state);
        
        if (state.equals(DisplayState.closed)) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                instance.servoDisplay.moveTo(servoDisplayPosUp, 100, 15, 500);
                }
            }, 10);
            
            
        } else {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                instance.servoDisplay.moveTo(servoDisplayPosDown, 100, 10, 200);
                }
            }, 10);
        }
        instance.displayState = state;
    }

    public static DisplayState getDisplayState() {
        return instance.displayState;
    }
    
    private void clearGPIO() {
        try {
            Runtime.getRuntime().exec("python /home/pi/Desktop/Shutdown.py");
        } catch (IOException ex) {
            Logger.getLogger(PinHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
