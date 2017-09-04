/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.Helpers;

import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import rearth.Fitness.FitnessData;
import rearth.IO.AnalogHandler;

/**
 *
 * @author Darkp
 */
public class StyledSwitch {

    private int posX;
    private int posY;
    private final String[] Text;
    private final Rectangle Background = new Rectangle();
    private final Color color = Color.rgb(0, 0, 0, 0.15);
    private final Color lockedColor = Color.rgb(30, 30, 30, 0.35);
    private final Color selectorColor = Color.rgb(220, 30, 30, 0.7);
    private int numofTexts = 0;
    private static final java.awt.Font defaultFont = new java.awt.Font("Carlito", java.awt.Font.PLAIN, 18);
    private int width = 0;
    private final int height = 50;
    private final int shadowsize = 4;
    private final ArrayList<Label> Labels = new ArrayList<>();
    private final ArrayList<String> Texts = new ArrayList<>();
    private final int animTime = 500;
    private boolean FitnessMode = false;
    private final DropShadow borderGlow;
    private boolean twomode = false;
    private boolean NightControl = false;
    private boolean MusicControl = false;
    private boolean ClapControl = false;
    private boolean used = false;
    private States state = States.normal;
    private boolean nightmode = false;
    private final int radius = 3;
    private final Insets insets =  new Insets(0, -2, 0, -2);
    private final CornerRadii radii = new CornerRadii(
            radius, radius, radius, radius, radius, radius, radius, radius,
            false,  false,  false,  false,  false,  false,  false,  false
    );
    
    private Label selection = new Label();
    private int selected = 0;
    
    public static enum States {
        normal, locked, displayOnly
    }
    
    public StyledSwitch(int posX, int posY, String... Text) {
        
        this(posX, posY, (int)((double) calcLength(Text) * 1.1F + 8), Text);
        
    }
    
    public StyledSwitch(int posX, int posY, int width , String... Text) {
        
        this.posX = posX;
        this.posY = posY;
        this.Text = Text;
        this.width = width;
        numofTexts = Text.length;
        
        if (this.Text.length == 0) {
            throw new IllegalArgumentException("No Texts given");
        }
        
        int i = 0;
        
        if (numofTexts == 2) {
            twomode = true;
        }
        
        for (String elem : Text) {
            if (elem == null || elem.equals("")) {
                throw new IllegalArgumentException("Text cant be empty");
            }
            
            Texts.add(elem);
            
            Label label = new Label(elem);
            label.setText(elem);
            label.setLayoutY(posY);
            label.setPrefHeight(height);
            label.setLayoutX(getElementPosition(i));
            if (i == 0) {
                label.setLayoutX(label.getLayoutX() + (width * 0.05));
            }
            if (i == numofTexts - 1) {
                label.setLayoutX(label.getLayoutX() - (width * 0.05));
            }
            if (twomode && i == 0) {
                label.setLayoutX(getElementPosition(i));
            }
            if (twomode && i == 1) {
                label.setLayoutX(getElementPosition(i) + (width * 0.05));
            }
            label.setFont(Font.font("Carlito", 18));
            label.setVisible(true);
            label.setAlignment(Pos.CENTER);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setOnMouseClicked((MouseEvent e) -> {
                handleAction(e);
            });
            Labels.add(label);
            i++;
            
        }
        System.out.println("creating fixed size Switch: Elements=" + numofTexts + " Width=" + width + " twoway=" + twomode);
        
        InnerShadow shadowtop = new InnerShadow();
        shadowtop.setOffsetX(shadowsize);
        shadowtop.setOffsetY(shadowsize);
        shadowtop.setColor(Color.web("0x243642"));
        
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setOffsetX(-shadowsize);
        innerShadow.setOffsetY(-shadowsize);
        innerShadow.setColor(Color.web("0x243642"));
        innerShadow.setInput(shadowtop);
        
        Background.setX(this.posX);
        Background.setY(this.posY);
        Background.setWidth(width);
        Background.setHeight(height);
        Background.setArcHeight(9);
        Background.setArcWidth(9);
        Background.setFill(color);
        if (twomode) {
            Background.setOnMouseClicked((MouseEvent e) -> {
                handleAction(e);
            });
        }
        //Background.setEffect(innerShadow);
         
        borderGlow = new DropShadow();
        borderGlow.setOffsetY(3f);
        borderGlow.setOffsetX(3f);
        borderGlow.setColor(Color.GREY);
        
        selection.setEffect(borderGlow);
        selection.setText(Text[0]);
        selection.setPrefHeight(height * 0.8);
        selection.setFont(Font.font("Carlito", 18));
        selection.setBackground(new Background(new BackgroundFill(selectorColor, radii, insets)));
        selection.setLayoutX(getElementPosition(0) + (width * 0.05));
        selection.setLayoutY(posY + height * 0.1);
        //selection.setScaleX(0.9);
        selection.setVisible(true);
        selection.setOnMouseClicked((MouseEvent e) -> {
            if (FitnessMode && interactable()) {
                FitnessData.getInstance().processInput(rearth.HomeUI_DesignController.getInstance().programs.getSelected(), rearth.HomeUI_DesignController.getInstance().timers.getSelected());
            }
            if (twomode) {
                handleAction(e);
            }
        });
        
        rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(Background);
        
        Labels.stream().forEach((label) -> {
            rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(label);
        });
        
        rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(selection);
        
        hideSelected();
        
    }
    
    private void showall() {
        Labels.stream().forEach((label) -> {
            label.setVisible(true);
        });
    }
    
    private void hideSelected() {
        showall();
        Labels.get(selected).setVisible(false);
    }
    
    private void handleAction(MouseEvent e) {
        
        if (used || !interactable()) {
            return;
        }
        
        used = true;
        
        if (twomode) {
            if (selected == 0) {
                selected = 1;
                selection.setText(Texts.get(1));
                moveSelection(1, 0);
            } else {
                selected = 0;
                selection.setText(Texts.get(0));
                moveSelection(0, 1);
            }
            System.out.println("Two-Mode selection! new Values: id=" + selected + " Text=" + Texts.get(selected));
        } else {
            Label klicked = (Label) e.getSource();
            String text = klicked.getText();
            int select = Texts.indexOf(text);
            selection.setText(text);
            moveSelection(select, selected);
            selected = select;
            System.out.println("klicked: " + klicked.getText() + " value=" + select);
        }
        if (NightControl) {
            rearth.HomeUI_DesignController.getInstance().toggleNightMode(selected);
        }
        if (MusicControl) {
            rearth.HomeUI_DesignController.getInstance().hideMusic(selected);
        }
        if (ClapControl) {
            AnalogHandler.clapEnabled = selected == 1;
        }
        
        onClick.call(selected, this);
    }
    
    private boolean AnimPlaying = false;
    
    private void moveSelection(int index, int oldIndex) {
        
        if (AnimPlaying) {
            return;
        }
        
        AnimPlaying = true;
        
        showall();
        TranslateTransition tt = new TranslateTransition(Duration.millis(animTime), selection);
        tt.setByX(getElementPosition(index, true) - getElementPosition(oldIndex, true));
        tt.setByY(0);
        tt.setCycleCount(1);
        tt.setAutoReverse(true);
        tt.setOnFinished((ActionEvent event) -> {
            used = false;
            AnimPlaying = false;
            hideSelected();
        });
        tt.play();
    }
    
    private int getElementPosition(int i) {
        int toadd = 0;
        if (twomode && i== 0) {
            toadd += 3;
        }
        return toadd + (int)((double) posX + (double) (width * (double)((double) i / (double) numofTexts)));
    }
    private int getElementPosition(int i, boolean state) {
        if (i == 0) {
            int toadd = 0;
            if (twomode) {
                toadd += 3;
            }
            return toadd + (int) (getElementPosition(i) + (width * 0.05));
        }
        if (i == numofTexts - 1) {
            if (twomode) {
                return (int) (getElementPosition(i) + (width * 0.05));
            }
            return (int) (getElementPosition(i) - (width * 0.05));
        }
        return getElementPosition(i);
        
    }
    
    private double getElementSize() {
        return (double) (width * (double)((double) 1 / (double) numofTexts));
    }

    public Rectangle getBackground() {
        return Background;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
        Background.setX(posX);
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
        Background.setY(posY);
    }
    
    private static int calcLength(String[] Text) {
        int width = 0;
        
        for (String val : Text) {
            width += calcLength(val);
        }
        
        return width;
    }
    
    private static int calcLength(String Text) {
        
        AffineTransform affinetransform = new AffineTransform();     
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        int textwidth = (int)(defaultFont.getStringBounds(Text, frc).getWidth());
        
        //System.out.println("calc string length: " + Text + ":" + textwidth);
        
        return textwidth;
    }

    public int getSelected() {
        return selected;
    }
    
    public String getText() {
        return Texts.get(selected);
    }
    
    public void setFitnessMode() {
        FitnessMode = true;
    }
    
    public void delete(StyledSwitch selector) {
        Labels.stream().map((label) -> {
            rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().remove(label);
            return label;
        }).forEach((label) -> {
            label = null;
        });
        Labels.clear();
        rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().remove(selection);
        selection = null;
        
        selector = null;
    }
    
    public void setNightMode(boolean state) {
        nightmode = state;
        if (state) {
            if (this.state.equals(States.locked)) {
                selection.setBackground(new Background(new BackgroundFill(Color.rgb(60, 60, 60, 0.45), radii, insets)));
                selection.setEffect(null);
                return;
            }
            selection.setBackground(new Background(new BackgroundFill(Color.rgb(80, 0, 20), radii, insets)));
            selection.setEffect(null);
        } else {
            System.out.println(this.state);
            if (this.state.equals(States.locked)) {
                selection.setBackground(new Background(new BackgroundFill(lockedColor, radii, insets)));
                selection.setEffect(borderGlow);
                return;
            }
            selection.setBackground(new Background(new BackgroundFill(selectorColor, radii, insets)));
            selection.setEffect(borderGlow);
        }
        
    }
    
    public void setState(int index) {
        
        System.out.println("Changing State to: " + index + " oldstate=" + selected);
        if (index > numofTexts - 1) {
            throw new IllegalArgumentException("Index too high");
        }
        
        int oldselected = selected;
        selected = index;
        selection.setText(Texts.get(index));
        moveSelection(index, oldselected);
    }
    
    public void setNightControl() {
        NightControl = true;
    }
    
    public void setMusicControl() {
        MusicControl = true;
    }
    
    public void setClapControl() {
        ClapControl = true;
    }
    
    public void setState(States stateSelected) {
        
        updateState(stateSelected);
        
    }
    
    public void updateState(States State) {
        
        if (state.equals(State)) {
            return;
        }
        
        state = State;
        
        switch (state) {
            case normal:
                selection.setBackground(new Background(new BackgroundFill(selectorColor, radii, insets)));
                if (nightmode) {
                    selection.setBackground(new Background(new BackgroundFill(Color.rgb(80, 0, 20), radii, insets)));
                }
                break;
            case locked:
                selection.setBackground(new Background(new BackgroundFill(lockedColor, radii, insets)));
                break;
            case displayOnly:
                selection.setBackground(new Background(new BackgroundFill(selectorColor, radii, insets)));
                break;
        }
        
    }
    
    private boolean interactable() {
        return state.equals(States.normal);
    }
    
    private Function onClick = StyledSwitch::doNothing;
    
    @FunctionalInterface
    public interface Function {
        void call(int SelectedOption, StyledSwitch element);
    }
    
    public void setOnClick(final Function function) {
        onClick = function;
    }
    
    private static void doNothing(int SelectedOption, StyledSwitch element) {
        System.out.println("switch element used without onclick handler");
    }
    
}
