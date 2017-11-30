/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspi_ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
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
import raspi_ui.backend.calendar.CalendarData;
import raspi_ui.backend.calendar.CalendarEvent;
import raspi_ui.backend.fastTaskTimer;
import raspi_ui.backend.slowTimerTask;

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
        slowTasks();
        //initCalendar();

    }

    private void initCalendar() throws IOException {

        Calendar now = Calendar.getInstance();
        Calendar next = Calendar.getInstance();
        next.add(Calendar.DATE, 1);

        SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

        List<CalendarEvent> Events = CalendarData.getEventsInTime(df.format(now.getTime()), df.format(next.getTime()));

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

    static void slowTasks() {

        System.out.println("scheduling slow tasks");

        TimerTask timerTask = new slowTimerTask();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 60 * 60 * 1000);

    }
    
}
