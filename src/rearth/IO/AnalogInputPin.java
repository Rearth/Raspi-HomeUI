/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Darkp
 */
public class AnalogInputPin extends IOPin {
    
    protected Process pythonReader;
    private final int[] values = new int[64];
    private final int refreshRate;      //in Hz
    
    public AnalogInputPin(int number, int refreshRate) {
        super(number);
        System.out.println("creating Analog Pin at number " + number);
        this.refreshRate = refreshRate;
        
        Thread thread = new Thread(){
            @Override
            public void run(){
                init();
            }
        };
        thread.start();
        //init();
    }
    
    private void init() {
        try {
            pythonReader = Runtime.getRuntime().exec("python /home/pi/Desktop/Main.py " + super.number + " " + refreshRate);
            
            BufferedReader stdout = new BufferedReader(new InputStreamReader(pythonReader.getInputStream()));
            String line;
            
            while ((line = stdout.readLine()) != null) {
                //System.out.println(line);
                try {
                    int curValue = Integer.valueOf(line);
                    if (curValue < 5) {
                        continue;
                    }

                    for (int i = 1; i < 64; i++) {
                        values[i] = values[i - 1];
                    }
                    
                    values[0] = curValue;
                } catch (NumberFormatException ex) {
                    System.out.println("Python Message: " + line);
                }
            }
            
        } catch (IOException ex) {
            //Logger.getLogger(AnalogInputPin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stop() {
        System.out.println("stopping analog servo");
        pythonReader.destroy();
        //super.stop();
    }
    
    public int[] getValues() {
        return values;
    }

    public int getRefreshRate() {
        return refreshRate;
    }
    
}
