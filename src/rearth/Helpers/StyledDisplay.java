/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.Helpers;

import javafx.scene.paint.Color;
import rearth.StyledLabel;
import rearth.networking.UsageMarker;

/**
 *
 * @author Darkp
 */
public final class StyledDisplay {
    
    private final UsageMarker Elements[] = new UsageMarker[100 / 6];
    private final StyledLabel label;
    private int posX;
    private int posY;
    private String Text;
    private final int labelXGap = 0;
    private final int labelYGap = 6;
    private final int labelDiffX = 8;
    private final int heightGap = 6;
    private int level = 0;
    private boolean visible = true;
    private Color color = Color.DARKRED;
    private Color nightColor = Color.DARKRED;
    private int divide = 6;
    private boolean nightMode = false;
    
    public StyledDisplay(int posX, int posY, String Text) {
        
        this.posX = posX;
        this.posY = posY;
        this.Text = Text;
        
        if (Text != null || !Text.equals("")) {
            label = new StyledLabel(Text, posX + labelXGap, posY + labelYGap);
            label.setSize(label.getHeight(), label.getWidth() - labelDiffX);
            label.getImage().setLayoutX(label.getImage().getLayoutX() + labelDiffX);
        } else {
            label = null;
        }
        
        for (int i=0; i < (100 / divide); i++) {
            UsageMarker marker = new UsageMarker(posX, posY - (heightGap * i));
            rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(marker);
            Elements[i] = (marker);
        }
        
        setNightMode(false);
        
    }

    public String getText() {
        return Text;
    }

    public void setText(String Text) {
        this.Text = Text;
        label.setText(Text);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        for (int i=0; i < (level / divide); i++) {
            Elements[i].setVisible(true);
        }
        for (int i = level / divide; i < (100 / divide); i++) {
            Elements[i].setVisible(false);
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        for (UsageMarker marker : Elements) {
            marker.setVisible(visible);
        }
        label.setVisible(visible);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        for (UsageMarker marker : Elements) {
            marker.setColor(color);
        }
    }
    
    public void setNightColor() {
        
    }

    public int getDivide() {
        return divide;
    }

    public void setDivide(int divide) {
        this.divide = divide;
    }
    
    public void setNightMode(boolean state) {
        
        nightMode = state;
        
        for (UsageMarker marker : Elements) {
            if (nightMode) {
                marker.setColor(nightColor);
            } else {
                marker.setColor(color);
            }
            
        }
        
        label.NightMode(state);
        
    }
    
}
