/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.networking;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import rearth.Helpers.TimeService.Zeit;
import rearth.StyledLabel;
import static rearth.networking.ComputerConnection.*;

/**
 *
 * @author Darkp
 */

public final class ComputerStats {

    final static ComputerStats instance = new ComputerStats();
    
    private final double posX = 350;
    private final double posY = 540;
    private final double heightGap = 6;
    private final int vGap = 60;
    private final int labelDX = 2;
    private final int labelDY = 5;
    private final int labelSX = 8;
    
    public double CPUusage = 0;        //in %
    public double RAMusage = 0;        //in %
    public double RAMused = 0;         //in GigaByte
    public double GPUload = 0;
    
    private boolean connected = true;
    
    public static ComputerStats getInstance() {
        return instance;
    }
    
    public void updateStats() {
        
        try {
        String data = new ComputerConnection().Communicate("");
        
        CPUusage = getCPUusage(data);
        RAMusage = getRAMusage(data);
        RAMused = getRAMused(data);
        GPUload = getGPUload(data);
        
        connected = true;
        System.out.println(toString());
        
        drawBars();
        
        } catch (java.lang.NumberFormatException ex) {
            System.err.println("Cant reach host");
            connected = false;
            for (UsageMarker thing : Elements) {
            UsageMarker.delete(thing);
            }
            for (StyledLabel label : Labels) {
                StyledLabel.delete(label);
            }
            Elements.clear();
            Labels.clear();
            
            StyledLabel errorLabel = new StyledLabel("PC not Reachable", (int) posX + 10, (int) posY + 5);
            errorLabel.setSize(errorLabel.getHeight(), 200);
            errorLabel.setTextCenter();
            Labels.add(errorLabel);
        }
    }
    
    private ComputerStats() {
        updateStats();
    }

    @Override
    public String toString() {
        return "ComputerStats{" + "CPUusage=" + CPUusage + ", RAMusage=" + RAMusage + ", RAMused=" + RAMused + ", GPUload=" + GPUload + '}' + new Zeit().toString(true);
    }

    public static void setNightMode(boolean state) {
        ComputerStats stats = getInstance();
        
        for (UsageMarker marker: stats.Elements) {
            marker.setNightMode(state);
        }
        for (StyledLabel label: stats.Labels) {
            label.NightMode(state);
        }
    }
    
    private final ArrayList<UsageMarker> Elements = new ArrayList<>();
    private final ArrayList<StyledLabel> Labels = new ArrayList<>();
    
    private final Color markerColor = Color.BLACK;
    private final double toPass = 60;
    
    private void drawBars() {
        
        if (!connected) {
            return;
        }
        
        for (UsageMarker thing : Elements) {
            UsageMarker.delete(thing);
        }
        for (StyledLabel label : Labels) {
            StyledLabel.delete(label);
        }
        
        Elements.clear();
        Labels.clear();
        
        StyledLabel CPUload = new StyledLabel("CPU", (int) posX + 2, (int) posY + 5);
        CPUload.setSize(CPUload.getHeight(), CPUload.getWidth() - labelSX);
        CPUload.getImage().setLayoutX(CPUload.getImage().getLayoutX() + labelSX);
        Labels.add(CPUload);
        
        StyledLabel MemUsage = new StyledLabel("RAM", (int) posX + labelDX + vGap, (int) posY + labelDY);
        MemUsage.setSize(MemUsage.getHeight(), MemUsage.getWidth() - labelSX);
        MemUsage.getImage().setLayoutX(MemUsage.getImage().getLayoutX() + labelSX);
        Labels.add(MemUsage);
        
        StyledLabel GPUUsage = new StyledLabel("GPU", (int) posX + labelDX + vGap * 2, (int) posY + labelDY);
        GPUUsage.setSize(GPUUsage.getHeight(), GPUUsage.getWidth() - labelSX);
        GPUUsage.getImage().setLayoutX(GPUUsage.getImage().getLayoutX() + labelSX);
        Labels.add(GPUUsage);
        
        for (int i=0; i < CPUusage / 6; i++) {
            UsageMarker marker = new UsageMarker(posX, posY - (heightGap*i));
            rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(marker);
            Elements.add(marker);
        }
        if (CPUusage > toPass) {
            UsageMarker topmark = new UsageMarker(posX, posY - (heightGap * 17));
            topmark.setColor(markerColor);
            rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(topmark);
            Elements.add(topmark);
        }
        
        for (int i=0; i < RAMusage / 6; i++) {
            UsageMarker marker = new UsageMarker(posX + vGap, posY - (heightGap*i));
            rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(marker);
            Elements.add(marker);
        }
        if (RAMusage > toPass) {
            UsageMarker topmark = new UsageMarker(posX + vGap, posY - (heightGap * 17));
            topmark.setColor(markerColor);
            rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(topmark);
            Elements.add(topmark);
        }
        
        for (int i=0; i < GPUload / 6; i++) {
            UsageMarker marker = new UsageMarker(posX + vGap * 2, posY - (heightGap * i));
            rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(marker);
            Elements.add(marker);
        }
        if (GPUload > toPass) {
            UsageMarker topmark = new UsageMarker(posX + vGap * 2, posY - (heightGap * 17));
            topmark.setColor(markerColor);
            rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(topmark);
            Elements.add(topmark);
        }
        
    }
        
}
