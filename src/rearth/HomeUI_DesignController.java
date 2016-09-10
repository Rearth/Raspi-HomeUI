/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import rearth.Helpers.TimeService.*;

/**
 *
 * @author Darkp
 */
public class HomeUI_DesignController implements Initializable {
     
    private static HomeUI_DesignController instance = null;
    
    public static HomeUI_DesignController getInstance() {
        return instance;
    }
    
    @FXML
    public Label timeLabel;
    @FXML
    public Label dateLabel;
    
    @FXML
    private void QuitUI(ActionEvent event) {
        System.out.println("Ending");
        System.exit(0);
    }
    
    @FXML
    private void DoDebug(ActionEvent event) {
        int[] Date = {8, 9, 2016};
        Datum datum = new Datum(Date);
        Zeit zeit = new Zeit();
        //timeLabel.setText("test");
        System.out.println(datum.toString(DateFormat.KalenderInfo) + " I " + datum.toString(DateFormat.ActivityInfo) +" I " +  datum.toString(DateFormat.Normal) + " I " + zeit.toString(true));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
    }
    
}
