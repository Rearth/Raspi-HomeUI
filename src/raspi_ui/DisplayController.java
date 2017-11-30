/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspi_ui;

import raspi_ui.backend.Animation;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import raspi_ui.backend.calendar.CalendarDesign;
import raspi_ui.backend.calendar.CalendarEvent;

/**
 *
 * @author Darkp
 */
public class DisplayController implements Initializable {
    
    private static DisplayController instance;
    
    @FXML
    private AnchorPane mainPane;
    
    @FXML
    private ImageView backgroundImage;
    
    @FXML
    private Label labelTime;
    
    @FXML
    private Label labelDate;
    
    @FXML
    private Button sleepButton;
    
    @FXML
    private Pane panelTime;
    
    private static boolean sleeping = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        System.out.println("Initializing FXML Controller...");
        instance = this;
        // TODO
        Image wallpaper = new Image(getClass().getResource("/raspi_ui/images/Wallpaper_Planet.jpg").toString());
        backgroundImage.setImage(wallpaper);
    }    
    
    @FXML
    private void startSleep(Event event) {
        DisplayController.setSleeping(true);
    }
    
    public static void setTime(String time) {
        
        SleepController.setTime(time);
        
        Platform.runLater(() -> {
            if (instance.labelTime.getText().equals(time)) {
                return;
            }
            instance.labelTime.setText(time);
            Animation.playScaleAnim(instance.labelTime);
        });
    }
    
    public static void setDate(String date) {
        
        Platform.runLater(() -> {
            if (instance.labelDate.getText().equals(date)) {
                return;
            }
            instance.labelDate.setText(date);
            
        });
    }
    
    public static void setSleeping(boolean state) {
        System.out.println("starting sleep mode: " + state);
        sleeping = state;
        if (state) {
            Raspi_UI.instance.setScene("sleep.fxml");
        } else {
            Raspi_UI.instance.setScene("Display.fxml");
        }

        CalendarDesign.clear();
    }

    public static void loadCalendar(List<CalendarEvent> events, boolean today) {

        Platform.runLater(() -> {
            CalendarDesign.setLabels(instance.mainPane, events, today);

        });
    }

    public static Pane getMainPane() {
        return instance.mainPane;
    }
    
}
