/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth;

import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

/**
 *
 * @author Darkp
 */
public final class StyledLabel {
    
    private final java.awt.Font defaultFont = new java.awt.Font("Calibri", java.awt.Font.PLAIN, 18);
    private final Font defaultfxFont = Font.font("Calibri", 18);
    private final int defaultheight = 45;
    private String Text;
    private int PosX;
    private int PosY;
    private int height;
    private int width;
    private final Image background = new Image("file:" + "C:\\Users\\Darkp\\Desktop\\RaspberryPi\\Design\\TextElement.png");
    private final ImageView image = new ImageView();
    private final Label textLabel = new Label();
    
    public Label getLabel() {
        return textLabel;
    }
    
    public void playAnim() {
        rearth.HomeUI_DesignController.getInstance().playScaleAnim(this);
    }
    
    public ImageView getImage() {
        return image;
    }
    
    public StyledLabel(String Text, int PosX, int PosY, int height, int width) {
        this(Text, PosX, PosY, height, width, true);
    }
    
    public StyledLabel(String Text, int PosX, int PosY, int height, int width, boolean playAnim) {
        if (height == 0) {
            this.height = defaultheight;
        }
        if (width == 0) {
            this.width = calcLength(Text);
        }
        
        this.Text = Text;
        this.PosX = PosX;
        this.PosY = PosY;
        
        
        textLabel.setText(this.Text);
        setLocation(this.PosX, this.PosY);
        setSize(this.height, this.width);
        image.setImage(background);
        textLabel.setFont(defaultfxFont);
        textLabel.setVisible(true);
        image.setVisible(true);
        
        rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(image);
        rearth.HomeUI_DesignController.getInstance().BackgroundPanel.getChildren().add(textLabel);
    }
    
    public void setSize(int height, int width) {
        image.setFitHeight(height);
        image.setFitWidth(width);
        
        textLabel.setPrefSize(width, height);
        
    }
    
    public void setLocation(int x, int y) {
        textLabel.setLayoutX(x);
        textLabel.setLayoutY(y);
        image.setLayoutX(x - 15);
        image.setLayoutY(y);
    }
    
    
    public StyledLabel() {
        this("", 0, 0, 0, 0);
    }
    
    public StyledLabel(String Text) {
        
        this(Text, 0, 0, 0, 0);
    }
    
    public StyledLabel(String Text, int PosX, int PosY) {
        this(Text, PosX, PosY, 0, 0);
    }
    
    public StyledLabel(int posX, int PosY) {
        this("", posX, PosY, 0, 0);
    }
    
    private int calcLength(String Text) {
        
        AffineTransform affinetransform = new AffineTransform();     
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        int textwidth = (int)(defaultFont.getStringBounds(Text, frc).getWidth());
        
        //System.out.println("calc string length: " + Text + ":" + textwidth);
        
        return textwidth + 25;
    }

    public String getText() {
        return Text;
    }

    public void setText(String Text) {
        setText(Text, true);
    }
    
    public void setText(String Text, boolean resize) {
        this.Text = Text;
        textLabel.setText(Text);
        if (resize) {
            width = calcLength(Text);
        }
        image.setFitWidth(width);
    }

    public int getPosX() {
        return PosX;
    }

    public void setPosX(int PosX) {
        this.PosX = PosX;
    }

    public int getPosY() {
        return PosY;
    }

    public void setPosY(int PosY) {
        this.PosY = PosY;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "StyledLabel{" + "Text=" + Text + ", PosX=" + PosX + ", PosY=" + PosY + ", height=" + height + ", width=" + width + '}';
    }
    
    
    
}
