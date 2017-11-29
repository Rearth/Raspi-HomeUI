/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspi_ui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

/**
 *
 * @author Darkp
 */
public class SleepController implements Initializable {
    
    @FXML
    private Label labelTime;
    
    private static SleepController instance;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        instance = this;
        
        Calendar time = Calendar.getInstance();
        
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        String displayTime = formatTime.format(time.getTime());
        labelTime.setText(displayTime);
        
    }   
    
    @FXML
    public void stopSleep(Event event) {
        System.out.println("stopping sleep mode");
        DisplayController.setSleeping(false);
    }
    
     public static void setTime(String time) {
        
        Platform.runLater(() -> {
            try {
                if (instance.labelTime.getText().equals(time)) {
                    return;
                }
                instance.labelTime.setText(time);
                Animation.playScaleAnim(instance.labelTime);
            } catch (NullPointerException ex) {
                //expected
            }
        });
    }
}
