/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.IO;

import com.pi4j.wiringpi.Gpio;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                stop();
            }
        }, 1500);
    }
    
    public void moveTo(int moveTo, int steps, int sleepTime) {
        moveTo(moveTo, steps, sleepTime, 0);
    }
    
    public void moveTo(int moveTo, int steps, int sleepTime, int initialDelay) {
                     
        try {
            Thread.sleep(initialDelay);
        } catch (InterruptedException ex) {
            Logger.getLogger(PWMPin.class.getName()).log(Level.SEVERE, null, ex);
        }

        Point p1 = new Point(0, pos);
        Point p2 = new Point(steps, moveTo);
        float m = ((float) p2.y - (float)p1.y) / ((float)p2.x - (float)p1.x);

        System.out.println("starting servo linear curve: p1=" + p1 + " p2=" + p2 + " m=" + m + " theor: " + ((p2.y - p1.y) / (p2.x - p1.x)));
        init(number);

        for (int x = 0; x <= steps; x++) {
            
            float xf = x;
            float curVal = (m * (xf - p1.x)) + p1.y;
            Gpio.pwmWrite(number, (int) curVal);
            this.pos = (int) curVal;

            //System.out.println("x=" + x + " f(x)=" + curVal);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(PWMPin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        System.out.println("done linear curve");
        stop();
        
        
    }
    
}
