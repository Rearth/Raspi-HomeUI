/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspi_ui.backend;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author Darkp
 */
public class Animation {
    
    public static void playScaleAnim(Node label, float from, float to, int duration) {
        
        ScaleTransition scaleanim = new ScaleTransition(Duration.millis(duration), label);
        scaleanim.setFromX(from);
        scaleanim.setToX(to);
        scaleanim.setFromY(from);
        scaleanim.setToY(to);
        scaleanim.setCycleCount(1);
        scaleanim.setAutoReverse(true);
        scaleanim.play();
    }
    
    public static void playScaleAnim(Node Label) {
        playScaleAnim(Label, 1.4f, 1.0f, 500);
    }
    
}
