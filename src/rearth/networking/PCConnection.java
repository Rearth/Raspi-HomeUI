/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.networking;

import pc_serversocket.RaspberryInfo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import pc_serversocket.PCInfo;

/**
 *
 * @author Darkp
 */
public class PCConnection extends Thread {
    
    private static final int port = 230;
    private static final String serverName = "192.168.0.50";    //"192.168.0.50" or "localhost"
    private static final int timeout = 1000;
    private static PCInfo lastData = null;
    private static boolean connected = false;
    
    private static Socket server = null;
    
    @Override
    public void run() {
        
        System.out.println("started PCConnection Thread");
        
        try {
            restartUpdate();
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(PCConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.err.println("error while serializing: " + ex);
        }
        
    }
    
    private void restartUpdate() throws InterruptedException, IOException, ClassNotFoundException {
        try {
            while (true) {
                update();
            }
        } catch (java.net.SocketException ex) {
            System.err.println("host went offline: " + ex);
            connected = false;
            server = null;
            Thread.sleep(10 * 1000);
            restartUpdate();
        }
    }
    
    private void update() throws InterruptedException, IOException, ClassNotFoundException {
        
        if (rearth.HomeUI_DesignController.Sleeping) {
            Thread.sleep(60 * 1000);
            server = null;
            connected = false;
            update();
            return;
        }
        
        if (server == null) {
            System.out.println("connecting with raspberry...");
            server = connect();
        }
        
        if (server == null) { 
            Thread.sleep(10 * 1000);
            update();
            return;
        }
        
        connected = true;
        System.out.println("waiting for input from desktop");
        DataInputStream in = new DataInputStream(server.getInputStream());

        String input = in.readUTF();
        PCInfo recievedInfo;
        try {
            recievedInfo = (PCInfo) fromString(input);
        } catch (java.lang.StackOverflowError ex) {
            System.out.println("got overflow!");
            server.getInputStream().close();
            server.getOutputStream().close();
            server.close();
            server = connect();
            return;
        }
        System.out.println(recievedInfo);
        lastData = recievedInfo;
        System.out.println("sending answer to desktop");

        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        RaspberryInfo sendInfo = RaspberryInfo.gather();

        String toSend = toString(sendInfo);
        out.writeUTF(toSend);
        
        ComputerStats.getInstance().updateStats();
        System.out.println("communication done, updating again");
        
        update();
    }
    
    private static Object fromString(String s) throws IOException, ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream( 
                                        new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
   }

    /** Write the object to a Base64 string. */
    private static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray()); 
    }

    private Socket connect() {
        try {
            System.out.println("connecting with Desktop server...");
            
            Socket client = new Socket();
            client.connect(new InetSocketAddress(serverName, port), timeout);
            
            System.out.println("succesfully connected!");
            
            return client;
        } catch (IOException ex) {
            System.err.println("couldnt connect to Desktop: " + ex);
            connected = false;
        }
        return null;
    }

    public static PCInfo getLastData() {
        return lastData;
    }

    public static boolean isConnected() {
        return connected;
    }
    
    
}
