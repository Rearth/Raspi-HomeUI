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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import rearth.Helpers.TimeService.*;
import rearth.Helpers.Weather;

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
    private void QuitUI(Event event) {
        System.out.println("Ending");
        System.exit(0);
    }
    
    @FXML
    private void toggleNightMode(Event event) {
        
        System.out.println("changing night mode");
        
        if (!nightmode) {
            BackgroundPanel.setStyle("-fx-background-color: #0a001a;");
            DebugButton.setStyle("-fx-border-color: rgba(179, 143, 0, 0.15); -fx-border-radius: 5; -fx-border-width: 3; -fx-background-color: #0a001a;");
            NightModeButton.setStyle("-fx-border-color:  rgba(179, 143, 0, 0.15); -fx-border-radius: 5; -fx-border-width: 3; -fx-background-color: #0a001a;");
            buttonQuit.setStyle("-fx-border-color:  rgba(179, 143, 0, 0.15);; -fx-border-radius: 5; -fx-border-width: 3; -fx-background-color: #0a001a;");
            
            for (Label label: StundenplanItems) {
                if (label.getId() != "Titel") {
                    label.setStyle("-fx-border-color: rgba(179, 143, 0, 0.15); -fx-border-radius: 2; -fx-border-width: 3; -fx-background-color: #0a001a;");
                }
            }
            
            nightmode = true;
        } else {
            BackgroundPanel.setStyle("");
            DebugButton.setStyle("");
            NightModeButton.setStyle("");
            buttonQuit.setStyle("");
            nightmode = false;
            
            for (Label label: StundenplanItems) {
                if (label.getId() != "Titel") {
                    label.setStyle("-fx-border-color:  #0088cc; -fx-border-width: 3;");
                }
            }
        }
        
    }
    
    @FXML
    private void DoDebug(Event event) {
        
        playScaleAnim(timeLabel);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1.0);
        
        Blend blush = new Blend(
                BlendMode.MULTIPLY,
                monochrome,
                new ColorInput(
                        0,
                        0,
                        WeatherImage.getImage().getWidth(),
                        WeatherImage.getImage().getHeight(),
                        Color.STEELBLUE
                )
        );
        
        WeatherImage.setEffect(blush);
        
        System.out.println("Color=" + TempToday.getTextFill().toString());
        TempToday.setTextFill(Color.web("0x333333ff"));
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        
        timeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 71));
        dateLabel.setFont(Font.font("Calibri", 18));
        TempToday.setFont(Font.font("Verdana", FontWeight.BOLD, 62));
        
        Font font = Font.font("Verdana", 15);
        TempMorgen.setFont(font);
        TempUbermorgen.setFont(Font.font("Verdana", 13));
        WeatherState.setFont(font);
        
        //WeatherImage.setImage(Weather.getinstance().);
        //testPane.setStyle("-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);");
        
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
    
    private final ArrayList<Label> StundenplanItems = new ArrayList<>();
    
    private final int ListGap = 42;
    private final int ListWidth = 180;
    private final int PosX = 60;
    private final int PosY = 170;
    
    public void createStundenplan(String[] Texts, String Day) {
        
        
        ArrayList<String> TextUsed = new ArrayList<>();
                
        clearStundenplan();
        
        Label title = new Label(Day);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 43));
        title.setLayoutX(PosX);
        title.setLayoutY(PosY - 55);
        title.setId("Titel");
        title.setVisible(true);
        BackgroundPanel.getChildren().add(title);
        StundenplanItems.add(title);
        
        int numofItems = Texts.length;
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
                
                Label labelA = new Label(Text);
                labelA.setFont(Font.font("Calibri Light", 22));
                labelA.setLayoutX(PosX);
                labelA.setLayoutY(PosY + ListGap * curItem);
                
                //System.out.println(Texts[6]);
                //System.out.println("Text=" + Text + " curItem=" + curItem + " i=" + i + " textlength=" + Texts.length + " Texti-1=" + Texts[(i - 1)]);
                
                if (Texts[i - 1].equals(Text)) {
                    curItem++;
                    labelA.setPrefSize(ListWidth, ListGap * 2 + 1);
                    labelA.setAlignment(Pos.CENTER);
                    //System.out.println("doppelstunde!");
                    labelA.setFont(Font.font("Calibri Light", 26));
                } else {
                    labelA.setPrefSize(ListWidth, ListGap + 1);
                    labelA.setAlignment(Pos.CENTER);
                    //System.out.println("einzelstunde!");
                }
                if (nightmode) {
                   labelA.setStyle("-fx-border-color: rgba(179, 143, 0, 0.15); -fx-border-radius: 2; -fx-border-width: 3; -fx-background-color: #0a001a;");
                } else {
                    labelA.setStyle("-fx-border-color:  #0088cc; -fx-border-width: 3;");
                }
                
                
                labelA.setVisible(true);
                
                BackgroundPanel.getChildren().add(labelA);
                
                StundenplanItems.add(labelA);
                curItem++;
                
            } else if (TextUsed.contains(Text)) {
                numofItems--;
            }
            
            
        }
        
        //System.out.println("Info: " + numofItems + " I " + pauseTime);
        
    }
    
    void drawPause(int value, int curItem) {
        
        //System.out.println("Drawing Pause: " + value + " I " + curItem);
        
        Label labelA = new Label("");
        labelA.setLayoutX(PosX);
        labelA.setLayoutY(PosY + ListGap * curItem); 
        labelA.setPrefSize(ListWidth, ListGap * value + 1);
        labelA.setAlignment(Pos.CENTER);
        if (nightmode) {
                   labelA.setStyle("-fx-border-color: rgba(179, 143, 0, 0.15); -fx-border-radius: 2; -fx-border-width: 3; -fx-background-color: #0a001a;");
                } else {
                    labelA.setStyle("-fx-border-color:  #0088cc; -fx-border-width: 3;");
                }
                
        labelA.setVisible(true);
                
        BackgroundPanel.getChildren().add(labelA);
                
        StundenplanItems.add(labelA);
    }
    
    public void clearStundenplan() {
        
        StundenplanItems.stream().forEach((label) -> {
            BackgroundPanel.getChildren().remove(label);
            //System.out.println("Label=" + label.getId());
        });
        
        StundenplanItems.clear();
        
    }
    
}
