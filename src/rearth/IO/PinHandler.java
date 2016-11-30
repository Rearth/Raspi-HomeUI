/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.IO;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Darkp
 */
public class PinHandler {
    
    public enum DisplayState {
        opened, closed
    }
    
    private final ArrayList<IOPin> Pins = new ArrayList<>();
    private final PWMPin servoDisplay = new PWMPin(1, servoDisplayPosUp);
    
    private static final PinHandler instance = new PinHandler();
    private static final int servoDisplayPosUp = 1;
    private static final int servoDisplayPosDown = 81;
    
    private DisplayState displayState = DisplayState.opened;
    
    private PinHandler() {
        Pins.add(servoDisplay);
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
            
            instance.servoDisplay.setpos(servoDisplayPosDown - 3);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    instance.servoDisplay.setpos(servoDisplayPosUp);
                }
            }, 2*1000);
        } else {
            instance.servoDisplay.setpos(servoDisplayPosUp + 3);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    instance.servoDisplay.setpos(servoDisplayPosDown);
                }
            }, 2*1000);          
        }
        instance.displayState = state;
    }

    public static DisplayState getDisplayState() {
        return instance.displayState;
    }
    
}
