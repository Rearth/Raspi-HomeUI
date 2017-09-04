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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rearth.HomeUI_DesignController;

/**
 *
 * @author Darkp
 */
public class AnalogInputPin extends IOPin {
    
    protected Process pythonReader;
    private final int[]values;
    private final int refreshRate;      //in Hz
    private boolean log = false;
    private int logLine = 0;
    private final ArrayList<Integer> logMarkers = new ArrayList<>();
    private final ArrayList<Integer> logMarkersHigh = new ArrayList<>();
    
    public AnalogInputPin(int number, int refreshRate, int saveLength) {
        super(number);
        System.out.println("creating Analog Pin at number " + number);
        this.refreshRate = refreshRate;
        values = new int[saveLength];
        
        Thread thread = new Thread(){
            @Override
            public void run(){
                init();
            }
        };
        thread.start();
        //init();
    }
    
    public AnalogInputPin(int number, int refreshRate) {
        this(number, refreshRate, 64);
    }
    
    private FileWriter writer = null;
    
    private void init() {
        try {
            pythonReader = Runtime.getRuntime().exec("python /home/pi/Desktop/Main.py " + super.number + " " + refreshRate);
            
            BufferedReader stdout = new BufferedReader(new InputStreamReader(pythonReader.getInputStream()));
            String line;
            
            if (log) {
                String csvFile = "/home/pi/Desktop/Data.csv";
                writer = new FileWriter(csvFile);
            }
            
            while ((line = stdout.readLine()) != null) {
                //System.out.println(line);
                
                try {
                    int curValue = Integer.valueOf(line);
                                        
                    if (curValue < 5) {
                        continue;
                    }
                    
                    if (log) {
                        logLine++;
                        if (logMarkers.contains(logLine)) {
                            CSVUtil.writeLine(writer, Integer.toString(0));
                            CSVUtil.writeLine(writer, Integer.toString(0));
                            CSVUtil.writeLine(writer, Integer.toString(0));
                            CSVUtil.writeLine(writer, Integer.toString(0));
                        }
                        if (logMarkersHigh.contains(logLine)) {
                            CSVUtil.writeLine(writer, Integer.toString(700));
                            CSVUtil.writeLine(writer, Integer.toString(700));
                            CSVUtil.writeLine(writer, Integer.toString(700));
                            CSVUtil.writeLine(writer, Integer.toString(700));
                        }
                        CSVUtil.writeLine(writer, Integer.toString(Integer.valueOf(line)));
                    }

                    /*for (int i = 1; i < 64; i++) {
                        values[i] = values[i - 1];
                    }*/
                    for (int i = values.length - 1; i > 0; i --) {
                        values[i] = values[i - 1];
                    }
                    /*values[4] = values[3];
                    values[3] = values[2];
                    values[2] = values[1];
                    values[1] = values[0];*/
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
        try {
            System.out.println("stopping analog servo");
            pythonReader.destroy();
            if (log) {
                writer.flush();
                writer.close();
            }
            //super.stop();
        } catch (IOException ex) {
            Logger.getLogger(AnalogInputPin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int[] getValues() {
        return values;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setLog(boolean log) {
        this.log = log;
    }

    public boolean isLog() {
        return log;
    }

    public FileWriter getWriter() {
        return writer;
    }

    public int getLogLine() {
        return logLine;
    }
    
    public void setLogMarker(int at, boolean high) {
        if (!log) {
            return;
        }
        if (high) {
            logMarkersHigh.add(at);
            return;
        }
        logMarkers.add(at);
    }
    
}