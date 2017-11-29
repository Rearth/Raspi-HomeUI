/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspi_ui;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Darkp
 */
public class Raspi_UI extends Application {
    
    private static final int refreshDelay = 5;
    
    private static Stage stage;
    private static Scene normal;
    private static Scene sleep = null;
    
    public static Raspi_UI instance;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        System.out.println("Starting!");
        Raspi_UI.stage = stage;
        instance = this;
        
        Parent root = FXMLLoader.load(getClass().getResource("Display.fxml"));
        
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setFullScreen(true);
        
        Scene scene = new Scene(root);
        normal = scene;
        
        stage.setScene(scene);
        stage.show();
        
        fastTasks();
    }
    
    public void setScene(String className) {
        try {
            Scene scene = normal;
            
            if (!className.equals("Display.fxml")) {
                if (sleep == null) {
                    Parent root = FXMLLoader.load(getClass().getResource(className));
                    scene = new Scene(root);
                    sleep = scene;
                } else {
                    scene = sleep;
                }
            }
            
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Raspi_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    static void fastTasks() {
        
        System.out.println("scheduling fast tasks");
        
        TimerTask timerTask = new fastTaskTimer();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, refreshDelay * 1000);
        
    }
    
}
