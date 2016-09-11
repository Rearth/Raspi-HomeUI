/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rearth.Helpers.TimeService;
/**
 *
 * @author Darkp
 */
public class Raspi_HomeUI extends Application {
    
    TimeService.Datum curDatum;
    TimeService.Zeit curZeit;
    
    private static Raspi_HomeUI instance = null;   
    
    public static Raspi_HomeUI getInstance() {
        return instance;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        instance = this;
        
        Parent root = FXMLLoader.load(getClass().getResource("HomeUI_Design.fxml"));
        
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setFullScreen(true);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        initStartUp();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
    void initStartUp() {
        
        System.out.println("-------------------Starting-------------------------");
        
        curDatum = new TimeService.Datum();
        curZeit = new TimeService.Zeit();
        updateTime();
        
        fastTasks();
    }
    
    void fastTasks() {
        
        TimerTask timerTask = new fastTaskTimer();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 5 * 1000);
        
    }
    
    void updateTime() {
        
        curZeit.update();
        curDatum.update();
        
        boolean playAnim = false;
        
        if (!HomeUI_DesignController.getInstance().timeLabel.getText().equals(curZeit.toString(true))) {
            playAnim = true;
        }
        
        HomeUI_DesignController.getInstance().timeLabel.setText(curZeit.toString(true));
        HomeUI_DesignController.getInstance().dateLabel.setText(curDatum.toString(TimeService.DateFormat.KalenderInfo));
        
        if (playAnim) {
            HomeUI_DesignController.getInstance().playScaleAnim(HomeUI_DesignController.getInstance().timeLabel);
        }
    }
    
}


