/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth;

import javafx.scene.image.Image;

/**
 *
 * @author Darkp
 */
public class StyledConnector extends StyledLabel {
    
    private final Image post = new Image(getClass().getResource("/rearth/Images/TextElementPost.png").toString());
    private final Image postNight = new Image(getClass().getResource("/rearth/Images/TextElementPostNight.png").toString());
    
    StyledConnector(int posX, int posY, int height, int width) {
        super("", posX, posY, height, width, false);
        setImage(post);
    }
    
    @Override
    public void NightMode(boolean state) {
        if (state) {
            setImage(postNight);
        } else {
            setImage(post);
        }
    }    
}
