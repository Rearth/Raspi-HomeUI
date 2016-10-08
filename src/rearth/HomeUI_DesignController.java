/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import rearth.Fitness.FitnessData;
import rearth.Helpers.StyledSwitch;
import rearth.networking.ComputerStats;

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
    public Label TempToday;
    @FXML
    public Label TempMorgen;
    @FXML
    public Label TempUbermorgen;
    @FXML
    public Label WeatherState;
    @FXML
    public ImageView WeatherImage;
    @FXML
    public ImageView WeatherImageB;
    @FXML
    public ImageView WeatherImageC;
    @FXML
    public Button addFitness;
    
    @FXML
    private void addFitnessClicked(Event event) {
        FitnessData.getInstance().processInput(programs.getSelected(), timers.getSelected());
    }
    
    @FXML
    private void QuitUI(Event event) {
        System.out.println("Ending");
        System.exit(0);
    }
    
    @FXML
    private void toggleNightMode(Event event) {
        
        System.out.println("changing night mode");
        
        if (!nightmode) {
            BackgroundPanel.setStyle("-fx-background-color: #0a001a;");
            DebugButton.setStyle("-fx-border-color: rgba(52, 17, 17, 0.8); -fx-border-radius: 5; -fx-border-width: 3; -fx-background-color: #0a001a;");
            NightModeButton.setStyle("-fx-border-color:  rgba(52, 17, 17, 0.8); -fx-border-radius: 5; -fx-border-width: 3; -fx-background-color: #0a001a;");
            buttonQuit.setStyle("-fx-border-color:  rgba(52, 17, 17, 0.8); -fx-border-radius: 5; -fx-border-width: 3; -fx-background-color: #0a001a;");
            addFitness.setStyle("-fx-border-color:  rgba(52, 17, 17, 0.8); -fx-border-radius: 5; -fx-border-width: 3; -fx-background-color: #0a001a;");
            
            for (StyledLabel label: StundenplanItems) {
                label.NightMode(true);
            }
            
            for (StyledLabel label: FitnessData.getInstance().DrawnObjects) {
                label.NightMode(true);
            }
            ComputerStats.setNightMode(true);
            programs.setNightMode(true);
            timers.setNightMode(true);
            
            nightmode = true;
        } else {
            BackgroundPanel.setStyle("");
            DebugButton.setStyle("");
            NightModeButton.setStyle("");
            buttonQuit.setStyle("");
            addFitness.setStyle("");
            nightmode = false;
            programs.setNightMode(false);
            timers.setNightMode(false);
            
            for (StyledLabel label : StundenplanItems) {
                label.NightMode(false);
            }
            
            for (StyledLabel label: FitnessData.getInstance().DrawnObjects) {
                label.NightMode(false);
            }
            ComputerStats.setNightMode(false);
        }
        
    }
    
    @FXML
    private void DoDebug(Event event) {
        
        playScaleAnim(timeLabel);
        
        System.out.println("Color=" + TempToday.getTextFill().toString());
        TempToday.setTextFill(Color.web("0x333333ff"));
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        instance = this;
        
        timeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 71));
        dateLabel.setFont(Font.font("Carlito", 20));
        TempToday.setFont(Font.font("Verdana", FontWeight.BOLD, 62));
        
        Font font = Font.font("Verdana", 15);
        TempMorgen.setFont(font);
        TempUbermorgen.setFont(Font.font("Verdana", 13));
        WeatherState.setFont(font);
        
        programs = new StyledSwitch(760, 240, 250, "Radeln", "Joggen", "Workout");
        programs.setFitnessMode();
        timers = new StyledSwitch(760, 300, 250, "Kurz", "Mittel", "Lang");
        timers.setFitnessMode();
        
    }
    
    public StyledSwitch programs;
    public StyledSwitch timers;
    
    public void playScaleAnim(Label label) {
        
        ScaleTransition scaleanim = new ScaleTransition(Duration.millis(350), label);
        scaleanim.setFromX(1.4);
        scaleanim.setToX(1);
        scaleanim.setFromY(1.4);
        scaleanim.setToY(1);
        scaleanim.setCycleCount(1);
        scaleanim.setAutoReverse(true);
        scaleanim.play();
    }
    
    public void playScaleAnim(StyledLabel label) {
        
        ScaleTransition scaleanim = new ScaleTransition(Duration.millis(350), label.getLabel());
        scaleanim.setFromX(1.4);
        scaleanim.setToX(1);
        scaleanim.setFromY(1.4);
        scaleanim.setToY(1);
        scaleanim.setCycleCount(1);
        scaleanim.setAutoReverse(true);
        scaleanim.play();
        scaleanim = new ScaleTransition(Duration.millis(350), label.getImage());
        scaleanim.play();
    }
    
    private final ArrayList<StyledLabel> StundenplanItems = new ArrayList<>();
    private Label StundenPlanTitle = new Label();
    
    private int ListGap = 42;
    private final int GapDefault = 42;
    private final int ListWidth = 180;
    private final int PosX = 60;
    private final int PosY = 170;
    private final int maxHeight = 480 - PosY;
    
    public void createStundenplan(String[] Texts, String Day, boolean ishidden) {
        
        //TODO improve pause Draw 
        
        ArrayList<String> TextUsed = new ArrayList<>();
                
        clearStundenplan();
        
        if (ishidden) {
            return;
        }
        
        Label title = new Label(Day);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 43));
        title.setLayoutX(PosX - 17);
        title.setLayoutY(PosY - 55);
        title.setId("Titel");
        title.setVisible(true);
        BackgroundPanel.getChildren().add(title);
        StundenPlanTitle = title;
        //StundenplanItems.add(title);
        
        int numofItems = Texts.length;
        if (numofItems >= 8) {      //begin: 170; end:470
            ListGap = maxHeight / numofItems;
        } else {
            ListGap = GapDefault;
        }
        int pauseTime = 0;
        int curItem = 0;
        int i = 0;
        
        for (String Text: Texts) {
            
            if (i < Texts.length - 1) {
                i++;
            }
            //System.out.println(Text);
            
            if (Text.equals("nichts")) {
                
                numofItems--;
                pauseTime++;
                curItem++;
                
            } else if (!TextUsed.contains(Text)) {
                
                if (pauseTime > 0) {
                    drawPause(pauseTime, curItem - pauseTime);
                    pauseTime = 0;
                }
                
                TextUsed.add(Text);
                
                StyledLabel labelA = new StyledLabel(Text, PosX, PosY + ListGap * curItem);
                labelA.getLabel().setFont(Font.font("Carlito Light", 22));
                
                //System.out.println(Texts[6]);
                //System.out.println("Text=" + Text + " curItem=" + curItem + " i=" + i + " textlength=" + Texts.length + " Texti-1=" + Texts[(i - 1)]);
                
                if (Texts[i - 1].equals(Text)) {
                    curItem++;
                    labelA.setSize(ListGap * 2 + 1, ListWidth);
                    labelA.setTextCenter();
                    //System.out.println("doppelstunde!");
                    labelA.getLabel().setFont(Font.font("Carlito Light", 26));
                } else {
                    labelA.setSize(ListGap + 1, ListWidth);
                    labelA.setTextCenter();
                    //System.out.println("einzelstunde!");
                }
                
                StundenplanItems.add(labelA);
                curItem++;
                
            } else if (TextUsed.contains(Text)) {
                numofItems--;
            }
            
            
        }
        
        //System.out.println("Info: " + numofItems + " I " + pauseTime);
        
    }
    
    void drawPause(int value, int curItem) {
        
        StyledLabel labelA = new StyledConnector(PosX + ListWidth - 32, PosY + ListGap * curItem - 2, ListGap * value + 10, 2);
        StyledLabel labelB = new StyledConnector(PosX + 32, PosY + ListGap * curItem - 2, ListGap * value + 10, 2);
                
        StundenplanItems.add(labelA);
        StundenplanItems.add(labelB);
    }
    
    public void clearStundenplan() {
        
        for (StyledLabel label: StundenplanItems) {
            StyledLabel.delete(label);
        }
        
        BackgroundPanel.getChildren().remove(StundenPlanTitle);
        StundenplanItems.clear();
        
    }
    
}
