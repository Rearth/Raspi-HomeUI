/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import rearth.Fitness.FitnessData;
import rearth.Helpers.StyledSwitch;
import rearth.IO.PinHandler.DisplayState;
import rearth.networking.ComputerStats;

/**
 *
 * @author Darkp
 */
public class HomeUI_DesignController implements Initializable {
     
    public static boolean nightmode = false;
    public static final Color lightGrayBackground = Color.rgb(208, 208, 208);
    public static final Color darkGrayBackground = Color.rgb(45, 0, 22);
    
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
    public Rectangle WeatherA;
    @FXML
    public Rectangle WeatherB;
    @FXML
    public Rectangle WeatherC;
        
    public Button SleepMode = new Button("Schlafen");
    
    public void hideMusic(int state) {
        if (state == 1) {
            for (Node node: MusicElements) {
                node.setVisible(true);
            }
        } else {
            for (Node node: MusicElements) {
                node.setVisible(false);
            }
            
        }
    }
    
    @FXML
    private void QuitUI(Event event) {
        System.out.println("Ending");
        System.exit(0);
    }
    
    @FXML
    private void timeClicked(Event event) {
        
        DisplayState display = rearth.IO.PinHandler.getDisplayState();
        System.out.println("time clicked; state=" + display.name());
        if (display.equals(DisplayState.closed)) {
            rearth.IO.PinHandler.setDisplayPos(DisplayState.opened);
        }
        
        Sleeping = false;
        black.setVisible(false);
        timeLabel.setLayoutX(759);
        timeLabel.setLayoutY(32);
        timeLabel.setPrefSize(239, 92);
        timeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 71));
        dateLabel.setLayoutX(759);
        dateLabel.setLayoutY(115);
        dateLabel.setPrefSize(239, 26);
        dateLabel.setFont(Font.font("Carlito", 20));
        BackgroundPanel.getChildren().remove(black);
        
    }
    
    public static boolean autoDark = false;
    private static boolean darkened = false;
    
    private void setDark(boolean state) {
        
        if (darkened == state) {
            return;
        }
        
        darkened = state;
        
        if (state) {
            SleepMode.setStyle("-fx-border-color:  rgba(52, 17, 17, 0.8); -fx-border-radius: 5; -fx-border-width: 3; -fx-background-color: #0a001a;");
            BackgroundPanel.setStyle("-fx-background-color: #0a001a;");
            buttonQuit.setStyle("-fx-border-color:  rgba(52, 17, 17, 0.8); -fx-border-radius: 5; -fx-border-width: 3; -fx-background-color: #0a001a;");
            
            for (StyledLabel label: StundenplanItems) {
                label.NightMode(true);
            }
            
            for (StyledLabel label: FitnessData.getInstance().DrawnObjects) {
                label.NightMode(true);
            }
            
            for (Shape shape: MusicElementsBackground) {
                shape.setFill(darkGrayBackground);
            }
            
            ComputerStats.setNightMode(true);
            programs.setNightMode(true);
            timers.setNightMode(true);
            ModeSelector.setNightMode(true);
            MusicChanger.setNightMode(true);
            musiclabel.NightMode(true);
            nightlabel.NightMode(true);
            Color nightColor = Color.rgb(0, 0, 0, 0.18);
            WeatherA.setFill(nightColor);
            WeatherB.setFill(nightColor);
            WeatherC.setFill(nightColor);
            
            nightmode = true;
        } else {
            BackgroundPanel.setStyle("");
            SleepMode.setStyle("");
            //NightModeButton.setStyle("");
            buttonQuit.setStyle("");
            nightmode = false;
            programs.setNightMode(false);
            timers.setNightMode(false);
            ModeSelector.setNightMode(false);
            MusicChanger.setNightMode(false);
            musiclabel.NightMode(false);
            nightlabel.NightMode(false);
            Color color = Color.rgb(0, 0, 0, 0.1);
            WeatherA.setFill(color);
            WeatherB.setFill(color);
            WeatherC.setFill(color);
            
            for (StyledLabel label : StundenplanItems) {
                label.NightMode(false);
            }
            
            for (StyledLabel label: FitnessData.getInstance().DrawnObjects) {
                label.NightMode(false);
            }
            for (Shape shape: MusicElementsBackground) {
                shape.setFill(lightGrayBackground);
            }
            ComputerStats.setNightMode(false);
        }
    }
    
    public static void switchDark(boolean dark) {
        
        if (darkened == dark) {
            return;
        }
        
        Platform.runLater(() -> {
            instance.setDark(dark);
        });
    }
    
    //@FXML
    public void toggleNightMode(int state) {
        
        if (state == 2) {
            System.out.println("Started Dark mode auto...");
            autoDark = true;
            return;
        }
        
        autoDark = false;
        
        System.out.println("changing dark mode; state=" + state);
        nightmode = state == 0;
        
        this.setDark(!nightmode);
        
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
        
        nightlabel = new StyledLabel("Dunkel", 18, 10, 45, 140);
        nightlabel.setTextCenter();
        ModeSelector = new StyledSwitch(5, 58, 150, "Aus", "Ein", "Auto");
        ModeSelector.setNightControl();
        
        musiclabel = new StyledLabel("Music", 177, 10, 45, 85);
        MusicChanger = new StyledSwitch(164, 58, 90, "Aus", "Ein");
        MusicChanger.setMusicControl();
        if (ComputerStats.getInstance().connected) {
            MusicChanger.setState(1);
        }
        
        SleepMode.setLayoutX(280);
        SleepMode.setLayoutY(20);
        SleepMode.setPrefSize(150, 90);
        SleepMode.setFocusTraversable(false);
        BackgroundPanel.getChildren().add(SleepMode);
        SleepMode.setOnMouseClicked((MouseEvent e) -> {
                startSleepMode();
            });
        
        drawMusicControls();
                
    }
    
    private Rectangle black = null;
    
    private void startSleepMode() {
        
        Sleeping = true;
        black = new Rectangle();
        black.setFill(Color.BLACK);
        black.setHeight(800);
        black.setWidth(1200);
        BackgroundPanel.getChildren().add(black);
        
        FadeTransition ft = new FadeTransition(Duration.millis(500), black);
        ft.setFromValue(0F);
        ft.setToValue(1.0F);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        ft.play();
        
        timeLabel.setLayoutX(362);
        timeLabel.setLayoutY(250);
        timeLabel.setPrefSize(400, 100);
        timeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 114));
        dateLabel.setLayoutX(362);
        dateLabel.setLayoutY(360);
        dateLabel.setPrefSize(400, 100);
        dateLabel.setFont(Font.font("Carlito", 40));
        
        BackgroundPanel.getChildren().remove(timeLabel);
        BackgroundPanel.getChildren().remove(dateLabel);
        BackgroundPanel.getChildren().add(timeLabel);
        BackgroundPanel.getChildren().add(dateLabel);
                
        black.setOnMouseClicked((MouseEvent e) -> {
                Sleeping = false;
                black.setVisible(false);
                timeLabel.setLayoutX(759);
                timeLabel.setLayoutY(32);
                timeLabel.setPrefSize(239, 92);
                timeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 71));
                dateLabel.setLayoutX(759);
                dateLabel.setLayoutY(115);
                dateLabel.setPrefSize(239, 26);
                dateLabel.setFont(Font.font("Carlito", 20));
                BackgroundPanel.getChildren().remove(black);
                rearth.IO.PinHandler.setDisplayPos(DisplayState.opened);
                Raspi_HomeUI.getInstance().hourlyTasks();
            });
        
            rearth.IO.PinHandler.setDisplayPos(DisplayState.closed);
        
    }
    
    public static boolean Sleeping = false;
    
    private StyledLabel nightlabel = null;
    private StyledLabel musiclabel = null;
    private StyledSwitch ModeSelector = null;
    public StyledSwitch MusicChanger = null;
    
    private final int MusicX = 0;
    private final int MusicY = -8;
    
    private void drawMusicControls() {
        
        center.setCenterX(640 + MusicX);
        center.setCenterY(560 + MusicY);
        center.setRadius(40);
        center.setFill(lightGrayBackground);
        center.setOnMouseClicked((MouseEvent e) -> {
                switchState();
            });
        MusicElements.add(center);
        MusicElementsBackground.add(center);
        
        Rectangle higher = new Rectangle();
        higher.setLayoutX(643 + MusicX);
        higher.setLayoutY(537 + MusicY);
        higher.setHeight(50);
        higher.setWidth(80);
        higher.setArcWidth(25);
        higher.setArcHeight(25);
        higher.setFill(lightGrayBackground);
        MusicElements.add(higher);
        MusicElementsBackground.add(higher);
        
        Rectangle lower = new Rectangle();
        lower.setLayoutX(560 + MusicX);
        lower.setLayoutY(537 + MusicY);
        lower.setHeight(50);
        lower.setWidth(80);
        lower.setArcWidth(25);
        lower.setArcHeight(25);
        lower.setFill(lightGrayBackground);
        MusicElements.add(lower);
        MusicElementsBackground.add(lower);
        
        Label lowerVol = new Label();
        lowerVol.setText("-");
        lowerVol.setFont(Font.font("Verdana", FontWeight.BOLD, 42));
        lowerVol.setTextFill(Color.rgb(220, 30, 30));
        //lowerVol.setPrefSize(150, 40);
        lowerVol.setAlignment(Pos.CENTER);
        lowerVol.setLayoutX(574 + MusicX);
        lowerVol.setLayoutY(534 + MusicY);
        MusicElements.add(lowerVol);
        
        Label incrVol = new Label();
        incrVol.setText("+");
        incrVol.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        incrVol.setTextFill(Color.rgb(220, 30, 30));
        //incrVol.setPrefSize(150, 40);
        incrVol.setAlignment(Pos.CENTER);
        incrVol.setLayoutX(684 + MusicX);
        incrVol.setLayoutY(534 + MusicY);
        MusicElements.add(incrVol);
        
        centerIcon.setImage(new Image(getClass().getResource("/rearth/Images/pause.png").toString()));
        int size = 72;
        centerIcon.setFitHeight(size);
        centerIcon.setFitWidth(size);
        centerIcon.setLayoutX(604 + MusicX);
        centerIcon.setLayoutY(523 + MusicY);
        centerIcon.setOnMouseClicked((MouseEvent e) -> {
                switchState();
            });
        MusicElements.add(centerIcon);
        
        
        higher.setOnMouseClicked((MouseEvent e) -> {
                playScaleAnim(higher, incrVol);
                increaseVolume();
            });
        incrVol.setOnMouseClicked((MouseEvent e) -> {
                playScaleAnim(higher, incrVol);
                increaseVolume();
            });
        
        lowerVol.setOnMouseClicked((MouseEvent e) -> {
                playScaleAnim(lowerVol, lower);
                lowerVolume();
            });
        lower.setOnMouseClicked((MouseEvent e) -> {
                playScaleAnim(lower, lowerVol);
                lowerVolume();
            });
        
        BackgroundPanel.getChildren().add(higher);
        BackgroundPanel.getChildren().add(lower);
        BackgroundPanel.getChildren().add(center);
        BackgroundPanel.getChildren().add(incrVol);
        BackgroundPanel.getChildren().add(lowerVol);
        BackgroundPanel.getChildren().add(centerIcon);
        
        startAnimation();
    }
    
    private void startAnimation() {
        
        sizeAnim();
        //widthAnim();
        
    }
    
    private static boolean playingAnim = false;
    
    public void sizeAnim() {
        //System.out.println("Called anim: state=" + playingAnim + " playing=" + ComputerStats.getInstance().playing);
        if (!ComputerStats.getInstance().playing) {
            playingAnim = false;
            return;
        }
        if (playingAnim) {
            return;
        }
        
        playingAnim = true;
        
        Double valScale = 1 + (Math.random() * 0.25);
        Double valTime = 0.24 + Math.random() * 0.15;
        
        if (valTime < 0.1) {
            valTime += 0.05;
        }
        ScaleTransition sizeanim = new ScaleTransition(Duration.millis(valTime * 500), center);
        sizeanim.setFromX(1);
        sizeanim.setToX(valScale);
        sizeanim.setFromY(1);
        sizeanim.setToY(valScale);
        sizeanim.setCycleCount(1);
        sizeanim.setAutoReverse(true);
        
        ScaleTransition scaleanim = new ScaleTransition(Duration.millis(valTime * 300), center);
        scaleanim.setFromX(valScale);
        scaleanim.setToX(1);
        scaleanim.setFromY(valScale);
        scaleanim.setToY(1);
        scaleanim.setCycleCount(1);
        scaleanim.setAutoReverse(true);
        
        sizeanim.setOnFinished((ActionEvent e) -> {
                scaleanim.play();
            });
        sizeanim.play();
        scaleanim.setOnFinished((ActionEvent e) -> {
                playingAnim = false;
                sizeAnim();
            });
    }
    
    private void widthAnim() {
        //Not Used
    }
    
    private final Circle center = new Circle();
    
    private void increaseVolume() {
        if (ComputerStats.getInstance().Volume < 1) {
            ComputerStats.getInstance().Volume += 0.05;  
        }
        
    }
    
    private void lowerVolume() {
        if (ComputerStats.getInstance().Volume > 0.05) {
            ComputerStats.getInstance().Volume -= 0.05;
        }
        
    }
    
    private void switchState() {
        playScaleAnim(centerIcon);
        if (ComputerStats.getInstance().playing) {
            centerIcon.setImage(new Image(getClass().getResource("/rearth/Images/play.png").toString()));
            ComputerStats.getInstance().playing = false;
        } else {
            centerIcon.setImage(new Image(getClass().getResource("/rearth/Images/pause.png").toString()));
            ComputerStats.getInstance().playing = true;
            startAnimation();
        }
        
    }
    
    public final ImageView centerIcon = new ImageView();
    private final ArrayList<Node> MusicElements = new ArrayList<>();
    private final ArrayList<Shape> MusicElementsBackground = new ArrayList<>();
    
    
    public StyledSwitch programs;
    public StyledSwitch timers;
    
    public void playScaleAnim(Node... Nodes) {
        for (Node node: Nodes) {
            playScaleAnim(node);
        }
    }
    
    public void playScaleAnim(Node label) {
        
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
    private final int PosX = 35;
    private final int PosY = 180;
    private final int maxHeight = 480 - PosY;
    
    public void createStundenplan(String[] Texts, String Day, boolean ishidden) {
                
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
            label.delete();
        }
        
        BackgroundPanel.getChildren().remove(StundenPlanTitle);
        StundenplanItems.clear();
        
    }
    
}
