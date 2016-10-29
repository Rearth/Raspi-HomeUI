/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.networking;

import java.util.ArrayList;
import javafx.scene.image.Image;
import rearth.Helpers.StyledSwitch;
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
    public boolean playing = false;
    public float Volume = 0F;
    public boolean muted = false;
    private boolean started = false;
    private String Title = "";
    
    public boolean connected = true;
    
    public static ComputerStats getInstance() {
        return instance;
    }
    
    public void updateStats() {
            
            Runnable myRunnable = () -> {
                try {
                    String toSend = "Null";
                    if (started) {
                        toSend = Volume + ":" + playing;
                    }
                    
                    String data = new ComputerConnection().Communicate(toSend);

                    CPUusage = getCPUusage(data);
                    RAMusage = getRAMusage(data);
                    RAMused = getRAMused(data);
                    GPUload = getGPUload(data);
                    Title = getTitle(data);
                    if (Volume == 0F && !muted) {
                        Volume = getVolume(data);
                        playing = isPlaying(data);
                        if (playing) {
                            rearth.HomeUI_DesignController.getInstance().centerIcon.setImage(new Image(getClass().getResource("/rearth/Images/pause.png").toString()));
                            rearth.HomeUI_DesignController.getInstance().sizeAnim();
                        } else {
                            rearth.HomeUI_DesignController.getInstance().centerIcon.setImage(new Image(getClass().getResource("/rearth/Images/play.png").toString()));
                        }
                        started = true;
                    }
                    rearth.HomeUI_DesignController.getInstance().sizeAnim();
                    connected = true;
                    System.out.println(toString());
                    
                } catch (java.lang.NumberFormatException ex) {
                    connected = false;
                }
            };

            Thread thread = new Thread(myRunnable);
            thread.start();
            
            if (!connected) {
                handleError();
            } else {
               showMusic();
            }
            
            drawBars();
            

    }
    
    private void handleError() {
            System.err.println("Cant reach host");
            for (UsageMarker thing : Elements) {
            UsageMarker.delete(thing);
            }
            for (StyledLabel label : Labels) {
                label.delete();
            }
            Elements.clear();
            Labels.clear();
            
            StyledLabel errorLabel = new StyledLabel("PC Offline", (int) posX + 10, (int) posY + 5);
            errorLabel.setSize(errorLabel.getHeight(), 200);
            errorLabel.setTextCenter();
            Labels.add(errorLabel);
            hideMusic();
        
    }
    
    private void hideMusic() {
        
        
        if (musicHidden || rearth.HomeUI_DesignController.getInstance().MusicChanger.getSelected() == 0) {
            return;
        }
        rearth.HomeUI_DesignController.getInstance().hideMusic(0);
        rearth.HomeUI_DesignController.getInstance().MusicChanger.setState(0);
        rearth.HomeUI_DesignController.getInstance().MusicChanger.setState(StyledSwitch.States.locked);
        System.out.println("hiding music");
        musicHidden = true;
    }
    
    private boolean musicHidden = false;
    
    private void showMusic() {
        
        
        if (!musicHidden || rearth.HomeUI_DesignController.getInstance().MusicChanger.getSelected() == 1) {
            return;
        }
        rearth.HomeUI_DesignController.getInstance().hideMusic(1);
        rearth.HomeUI_DesignController.getInstance().MusicChanger.setState(1);
        rearth.HomeUI_DesignController.getInstance().MusicChanger.setState(StyledSwitch.States.normal);
        System.out.println("showing music");
        musicHidden = false;
    }
    
    private ComputerStats() {
        updateStats();
    }

    @Override
    public String toString() {
        return "ComputerStats{" + "CPUusage=" + CPUusage + ", RAMusage=" + RAMusage + ", RAMused=" + RAMused + ", GPUload=" + GPUload + " playing=" + playing + " Volume=" + Volume + " Title=" + Title + '}' + new Zeit().toString(true);
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
        
    private void drawBars() {
        
        if (!connected) {
            return;
        }
        
        for (UsageMarker thing : Elements) {
            UsageMarker.delete(thing);
        }
        for (StyledLabel label : Labels) {
            label.delete();
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
        
        for (int i=0; i < RAMusage / 6; i++) {
            UsageMarker marker = new UsageMarker(posX + vGap, posY - (heightGap*i));
            rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(marker);
            Elements.add(marker);
        }
        
        for (int i=0; i < GPUload / 6; i++) {
            UsageMarker marker = new UsageMarker(posX + vGap * 2, posY - (heightGap * i));
            rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(marker);
            Elements.add(marker);
        }
        
    }
        
}
