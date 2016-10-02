/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author Darkp
 */
public class ComputerConnection {
    
    private static final int port = 230;
    private static final String serverName = "192.168.0.20";    //"192.168.0.20" or "localhost"
    private static final int timeout = 1000;
           
    public String Communicate(String toSend) {
        
        try (Socket client = new Socket()){
            client.connect(new InetSocketAddress(serverName, port), timeout);
            client.setSoTimeout(2000);
            //System.out.println("Connecting to " + serverName + " on port " + port);
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF(toSend);
            
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            return in.readUTF();
            
        }catch(IOException e) {
            //System.err.println("Error: 643");
        }
        
        return "couldnt connect";
    }
    
    public static double getCPUusage(String data) {       //both in %
        if (data.equals("error")) {
            return 0;
        }
        String Parts[] = data.split(":");
                
        return runden(Double.valueOf(Parts[0]) / 2);
    }
    
    public static double getRAMusage(String data) {
        if (data.equals("error")) {
            return 0;
        }
        String Parts[] = data.split(":");
        
        return runden((Double.valueOf(Parts[1]) / Double.valueOf(Parts[2])) * 100);
    }
    
    public static double getRAMused(String data) {
        if (data.equals("error")) {
            return 0;
        }
        String Parts[] = data.split(":");
        
        return runden(Double.valueOf(Parts[1]));
    }
    
    public static double getGPUload(String data) {
        if (data.equals("error")) {
            return 0;
        }
        String Parts[] = data.split(":");
        return Double.valueOf(Parts[3]);
    }
    
    public static double getGPUMemload(String data) {
        return Double.valueOf(data.split(":")[4]);
    }
    
    private static double runden(double number) {
        return (double) ((int) (number * 10)) / 10;
    }
    
}
