/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import rearth.Helpers.TimeService.*;

/**
 *
 * @author Darkp
 */
public class HomeUI_DesignController implements Initializable {
     
    public static boolean nightmode = false;
    
    private static HomeUI_DesignController instance = null;
    
    public static HomeUI_DesignController getInstance() {
        return instance;
    }
    
    @FXML
    public Label timeLabel;
    @FXML
    public Label dateLabel;
    @FXML
    public Button buttonQuit;
    @FXML
    public Button NightModeButton;
    @FXML
    public Button DebugButton;
    @FXML
    public AnchorPane BackgroundPanel;
    
    @FXML
    private void QuitUI(ActionEvent event) {
        System.out.println("Ending");
        System.exit(0);
    }
    
    @FXML
    private void toggleNightMode(ActionEvent event) {
        
        System.out.println("changing night mode");
        
        if (!nightmode) {
            BackgroundPanel.setStyle("-fx-background-color: #0a001a;");
            DebugButton.setStyle("-fx-background-color: #0a001a;");
            NightModeButton.setStyle("-fx-background-color: #0a001a;");
            buttonQuit.setStyle("-fx-background-color: #0a001a;");
            nightmode = true;
        } else {
            BackgroundPanel.setStyle("");
            DebugButton.setStyle("");
            NightModeButton.setStyle("");
            buttonQuit.setStyle("");
            nightmode = false;
        }
        
    }
    
    @FXML
    private void DoDebug(ActionEvent event) {
        int[] Date = {8, 9, 2016};
        Datum datum = new Datum(Date);
        Zeit zeit = new Zeit();
        //timeLabel.setText("test");
        playScaleAnim(timeLabel);
        System.out.println(datum.toString(DateFormat.KalenderInfo) + " I " + datum.toString(DateFormat.ActivityInfo) +" I " +  datum.toString(DateFormat.Normal) + " I " + zeit.toString(true));
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
    }
    
    void playScaleAnim(Label label) {
        
        ScaleTransition scaleanim = new ScaleTransition(Duration.millis(350), label);
        scaleanim.setFromX(1.4);
        scaleanim.setToX(1);
        scaleanim.setFromY(1.4);
        scaleanim.setToY(1);
        scaleanim.setCycleCount(1);
        scaleanim.setAutoReverse(true);
        scaleanim.play();
    }
    
}
