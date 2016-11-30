/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.IO;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;

/**
 *
 * @author Darkp
 */
public class IOPin {

    protected final int number;
    protected final GpioController gpio;
    
    public IOPin(int number) {
        this.number = number;
        
        gpio = GpioFactory.getInstance();
        gpio.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
         
    }
    
    public void stop() {
        
        gpio.shutdown();
        
    }
    
}
