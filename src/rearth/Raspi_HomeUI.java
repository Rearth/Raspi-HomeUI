/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rearth.Fitness.FitnessData;
import rearth.Helpers.Stundenplan;
import rearth.Helpers.TimeService;
import rearth.Helpers.Weather;
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
        
        try {
            Weather.getinstance().init();
        } catch (IOException ex) {
           System.out.println("Error getting Weather Data, trying again");
            try {
                Weather.getinstance().init();
            } catch (IOException ex1) {
                Logger.getLogger(Raspi_HomeUI.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        Weather wetter = Weather.getinstance();
        HomeUI_DesignController UI = HomeUI_DesignController.getInstance();
        Label[] Labels = {UI.TempToday, UI.WeatherState, UI.TempMorgen, UI.TempUbermorgen};
        wetter.updateWidget(Labels);
        
        Stundenplan stundenplan = Stundenplan.getInstance();
        System.out.println(stundenplan);
        
        fastTasks();
        
        System.out.println(FitnessData.getInstance());
        //FitnessData.getInstance().addActivity(FitnessData.Types.Workout, FitnessData.length.kurz, curDatum);
    }
    
    void fastTasks() {
        
        TimerTask timerTask = new fastTaskTimer();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 5 * 1000);
        
    }
    
    void updateTime() {
        
        int oldHours = curZeit.getHours();
        
        curZeit.update();
        curDatum.update();
        
        if (oldHours != curZeit.getHours()) {
            System.out.println("Hour done");
            hourlyTasks();
        }
        
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
    
    void hourlyTasks() {
        
        Weather wetter = Weather.getinstance();
        try {
            wetter.init();
        } catch (IOException ex) {
            System.out.println("Error getting Weather Data, trying again");
            try {
                wetter.init();
            } catch (IOException ex1) {
                Logger.getLogger(Raspi_HomeUI.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        HomeUI_DesignController UI = HomeUI_DesignController.getInstance();
        Label[] Labels = {UI.TempToday, UI.WeatherState, UI.TempMorgen, UI.TempUbermorgen};
        wetter.updateWidget(Labels);
        
        Stundenplan.getInstance().updateDay();
    }
    
}


