/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.IO;

import com.pi4j.wiringpi.Gpio;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Darkp
 */
public class PWMPin extends IOPin{

    private static final int defaultClock = 384;
    private static final int defaultRange = 1000;
    
    private int pos;
    
    public PWMPin(int number, int pos) {
        super(number);
        System.out.println("creating PWMPin at number " + number);
        
        init(number);
        Gpio.pwmWrite(number, pos);
        this.pos = pos;
        doNothing();
    }
    
    private void init(int number) {
        
        Gpio.pinMode(number, com.pi4j.wiringpi.Gpio.PWM_OUTPUT);
        Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
        Gpio.pwmSetClock(defaultClock);
        Gpio.pwmSetRange(defaultRange);
    }
    
    public void setpos (int pos) {
        System.out.println("setting pwm to " + pos);
        this.pos = pos;
        init(number);
        Gpio.pwmWrite(number, pos);
        doNothing();
    }
    
    @Override
    public void stop() {
        
        System.out.println("stopping pwm servo");
        //Gpio.pwmWrite(number, 0);
        /*Gpio.pwmSetClock(0);
        Gpio.pwmSetRange(0);*/
        gpio.shutdown();
    }

    public int getPos() {
        return pos;
    }
    
    private void doNothing() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nothingAgain();
            }
        }, 2*1000);
    }
    
    private void nothingAgain() {
        stop();       
    }
    
}
