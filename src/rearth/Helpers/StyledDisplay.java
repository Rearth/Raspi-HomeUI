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
    
    private final UsageMarker Elements[] = new UsageMarker[100 / 5];
    private final StyledLabel label;
    private int posX;
    private int posY;
    private String Text;
    private final int labelXGap = 0;
    private final int labelYGap = 6;
    private final int labelDiffX = 8;
    private final int heightGap = 6;
    private float level = 0;
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

    public float getLevel() {
        return level;
    }

    public void setLevel(float level) {
        if (level > 100) {
            level = 99;
        }
        this.level = level;
        try {
            for (int i = 0; i < (level / divide); i++) {
                Elements[i].setVisible(true);
            }
            for (int i = (int) level / divide; i < (100 / divide); i++) {
                Elements[i].setVisible(false);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("error setting level: " + ex);
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        try {
            this.visible = visible;
            for (UsageMarker marker : Elements) {
                if (marker != null) {
                    marker.setVisible(visible);
                }
            }
        } catch (NullPointerException ex) {
            System.err.println("NullPointer at StyledDisplay.setVisible(visible)");
            ex.printStackTrace();
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
            if (marker == null) {
                break;
            }
            if (nightMode) {
                marker.setColor(nightColor);
            } else {
                marker.setColor(color);
            }
            
        }
        
        label.NightMode(state);
        
    }
    
}
