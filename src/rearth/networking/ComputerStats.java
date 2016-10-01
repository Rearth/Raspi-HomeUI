/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.networking;

import java.util.ArrayList;
import javafx.scene.Node;
import rearth.Helpers.TimeService.Zeit;
import static rearth.networking.ComputerConnection.getCPUusage;
import static rearth.networking.ComputerConnection.getRAMusage;
import static rearth.networking.ComputerConnection.getRAMused;

/**
 *
 * @author Darkp
 */

public class ComputerStats {

    final static ComputerStats instance = new ComputerStats();
    
    public double CPUusage = 0;        //in %
    public double RAMusage = 0;        //in %
    public double RAMused = 0;         //in GigaByte
    
    public static ComputerStats getInstance() {
        return instance;
    }
    
    public void updateStats() {
        
        String data = new ComputerConnection().Communicate("");
        
        CPUusage = getCPUusage(data);
        RAMusage = getRAMusage(data);
        RAMused = getRAMused(data);
        
        System.out.println(toString());
        
        //drawBars();
    }
    
    private ComputerStats() {
        updateStats();
    }

    @Override
    public String toString() {
        return "ComputerStats{" + "CPUusage=" + CPUusage + ", RAMusage=" + RAMusage + ", RAMused=" + RAMused + '}' + " Zeit: " + new Zeit().toString(true);
    }
    
    private final ArrayList<UsageMarker> Elements = new ArrayList<>();
    
    private void drawBars() {
        
        for (UsageMarker thing : Elements) {
            thing.delete();
        }
        
        Elements.clear();
        
        for (int i=0; i < 5; i++) {
            UsageMarker marker = new UsageMarker(500, 500 - 8*i);
            rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(marker);
            Elements.add(marker);
        }
        
    }
        
}
