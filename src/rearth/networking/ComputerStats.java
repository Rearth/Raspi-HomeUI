/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.networking;

import java.util.ArrayList;
import javafx.application.Platform;
import pc_serversocket.PCInfo.PerformanceData;
import rearth.Helpers.StyledDisplay;
import rearth.Helpers.StyledSwitch;
import rearth.Helpers.TimeService.Zeit;
import rearth.StyledLabel;

/**
 *
 * @author Darkp
 */

public final class ComputerStats {

    final static ComputerStats instance = new ComputerStats();
    
    private final int posX = 375;
    private final int posY = 540;
    private final int vGap = 60;
    
    public double CPUusage = 0;         //in %
    public double RAMused = 0;          //in %
    public double GPUload = 0;          //in %
    public boolean playing = false;
    public float Volume = 0.35F;
    
    
    public static ComputerStats getInstance() {
        return instance;
    }
    
    public void updateStats() {
        
        if (!PCConnection.isConnected()) {
            handleError();
            return;
        }
        PerformanceData PCData;
        try {
            PCData = PCConnection.getLastData().getPCdata();
        } catch (NullPointerException ex) {
            System.err.println("PCData is null");
            return;
        }
        
        CPUusage = PCData.getCPUusage() * 100;
        RAMused = PCData.getUsedRam() / PCData.getMaxRam() * 100;
        GPUload = PCData.getGPULoad() * 100;
        Platform.runLater(() -> {
            showMusic();
            drawBars();
        });
        
        
        //System.out.println(toString());
            
            /*Runnable myRunnable = () -> {
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
                
                Thread.currentThread().interrupt();
            };

            Thread thread = new Thread(myRunnable);
            thread.start();
            
            if (!connected) {
                handleError();
            } else {
               showMusic();
            }
            
            drawBars();*/
            

    }
    
    private void handleError() {
            System.err.println("drawing missing connection");
            if (errorLabel != null) {
                errorLabel.delete();
            }
            Displays.stream().filter((StyledDisplay label) -> {
                return label != null;
            }).forEach((label) -> {
                label.setVisible(false);
            });
            
            errorLabel = new StyledLabel("PC Offline", (int) posX + 10, (int) posY + 5);
            errorLabel.setSize(errorLabel.getHeight(), 200);
            errorLabel.setTextCenter();
            hideMusic();
        
    }
    
    private StyledLabel errorLabel = null;
    
    private void hideMusic() {
        
        
        if (musicHidden/* || rearth.HomeUI_DesignController.getInstance().MusicChanger.getSelected() == 0*/) {
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
        Displays.stream().filter((label) -> (label != null)).forEach((label) -> {
            label.setVisible(true);
        });
        musicHidden = false;
    }
    
    private ComputerStats() {
        updateStats();
    }

    @Override
    public String toString() {
        return "ComputerStats{" + "CPUusage=" + CPUusage + "RAMused=" + RAMused + ", GPUload=" + GPUload + " playing=" + playing + " Volume=" + Volume + '}' + " connected=" + PCConnection.isConnected() + new Zeit().toString(true);
    }

    public static void setNightMode(boolean state) {
        ComputerStats stats = getInstance();
        
        for (StyledDisplay marker : stats.Displays) {
            marker.setNightMode(state);
        }
    }
    
    boolean inited = false;
    
    private void init() {
        
        if (inited) {
            return;
        }
        
        cpuDisplay = new StyledDisplay(posX, posY, "CPU");
        Displays.add(cpuDisplay);
        ramDisplay = new StyledDisplay(posX + vGap, posY, "RAM");
        Displays.add(ramDisplay);
        gpuDisplay = new StyledDisplay(posX + vGap * 2, posY, "GPU");
        Displays.add(gpuDisplay);
        
        System.out.println("Initialised Displays");
        
        inited = true;
        
    }
    
    private final ArrayList<StyledDisplay> Displays = new ArrayList<>();
    StyledDisplay cpuDisplay = null;
    StyledDisplay gpuDisplay = null;   
    StyledDisplay ramDisplay = null; 
        
    private void drawBars() {
        
        if (!PCConnection.isConnected()) {
            return;
        }
        
        //System.out.println("drawing bars");
        
        if (errorLabel != null) {
            errorLabel.delete();
        }
        init();
        
        if (CPUusage < 6 && CPUusage > 1) {
            CPUusage = 5;
        }if (GPUload < 6 && GPUload > 1) {
            GPUload = 5;
        }
        
        cpuDisplay.setVisible(true);
        ramDisplay.setVisible(true);
        gpuDisplay.setVisible(true);
        cpuDisplay.setLevel((int) CPUusage);
        ramDisplay.setLevel((int) RAMused);
        gpuDisplay.setLevel((int) GPUload);
        
        
    }
        
}
