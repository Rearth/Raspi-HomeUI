/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.networking;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Darkp
 */
public class UsageMarker extends Rectangle {
    
    private static final double height = 3;
    private static final double width = 60;

    public UsageMarker(double x, double y) {
        super(x, y, width, height);
        
        setArcHeight(2);
        setArcWidth(2);
        setFill(Color.DARKRED);
        setVisible(true);
        
    }
    
    public void delete() {
        rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().remove(this);
    }
    
}