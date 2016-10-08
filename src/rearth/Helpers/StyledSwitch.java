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
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import rearth.Fitness.FitnessData;

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
    private final Color selectorColor = Color.rgb(220, 30, 30, 0.4);
    private int numofTexts = 0;
    private static final java.awt.Font defaultFont = new java.awt.Font("Carlito", java.awt.Font.PLAIN, 18);
    private int width = 0;
    private final int height = 50;
    private final int shadowsize = 4;
    private final ArrayList<Label> Labels = new ArrayList<>();
    private final ArrayList<String> Texts = new ArrayList<>();
    private final int animTime = 500;
    private final int radius = 3;
    private boolean FitnessMode = false;
    private final DropShadow borderGlow;
    private final Insets insets =  new Insets(0, -2, 0, -2);
    private final CornerRadii radii = new CornerRadii(
            radius, radius, radius, radius, radius, radius, radius, radius,
            false,  false,  false,  false,  false,  false,  false,  false
    );
    
    private Label selection = new Label();
    private int selected = 0;
    
    public StyledSwitch(int posX, int posY, String... Text) {
        
        this(posX, posY, (int)((double) calcLength(Text) * 1.1F), Text);
        
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
            label.setFont(Font.font("Carlito", 18));
            label.setVisible(true);
            label.setAlignment(Pos.CENTER);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setOnTouchPressed((TouchEvent e) -> {
                handleAction(e);
            });
            Labels.add(label);
            i++;
            
        }
        System.out.println("creating fixed size Switch: Elements=" + numofTexts + " Width=" + width);
        
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
        selection.setOnTouchPressed((TouchEvent e) -> {
            if (FitnessMode) {
                FitnessData.getInstance().processInput(rearth.HomeUI_DesignController.getInstance().programs.getSelected(), rearth.HomeUI_DesignController.getInstance().timers.getSelected());
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
    
    private void handleAction(TouchEvent e) {
        Label klicked = (Label) e.getSource();
        String text = klicked.getText();
        int select = Texts.indexOf(text);
        selection.setText(text);
        moveSelection(select, selected);
        selected = select;
        System.out.println("klicked: " + klicked.getText() + " value=" + select);
    }
    
    private void moveSelection(int index, int oldIndex) {
        
        showall();
        TranslateTransition tt = new TranslateTransition(Duration.millis(animTime), selection);
        tt.setByX(getElementPosition(index, true) - getElementPosition(oldIndex, true));
        tt.setByY(0);
        tt.setCycleCount(1);
        tt.setAutoReverse(true);
        tt.setOnFinished((ActionEvent event) -> {
            hideSelected();
        });
        tt.play();
    }
    
    private int getElementPosition(int i) {
        return (int)((double) posX + (double) (width * (double)((double) i / (double) numofTexts)));
    }
    private int getElementPosition(int i, boolean state) {
        if (i == 0) {
            return (int) (getElementPosition(i) + (width * 0.05));
        }
        if (i == numofTexts - 1) {
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
        if (state) {
            selection.setBackground(new Background(new BackgroundFill(Color.rgb(35, 5, 5, 1), radii, insets)));
            selection.setEffect(null);
        } else {
            selection.setBackground(new Background(new BackgroundFill(selectorColor, radii, insets)));
            selection.setEffect(borderGlow);
        }
        
    }
    
}
