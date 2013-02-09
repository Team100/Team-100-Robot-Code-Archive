/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team100.SDextension;

import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Student
 */
public class Climber extends Widget{
    public static final String NAME = "Climber Position";
    public static final DataType[] TYPES = { DataType.NUMBER };
    
    private double value = 0;
    BufferedImage robot = null;
    BufferedImage hook = null;
    
    @Override
    public void setValue(Object value) {
        this.value = (double)value;
        
        repaint();
    }
    
    @Override
    public void init() {
        setPreferredSize(new Dimension(200, 250));
        try {
            robot = ImageIO.read(Climber.class.getResourceAsStream("robot.png"));
            hook = ImageIO.read(Climber.class.getResourceAsStream("hook.png"));
        } catch (IOException ex) {
        }
    }

    @Override
    public void propertyChanged(Property property) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension size = getSize();
        int height=(int)((size.height-value*size.height)*8/10);
        g.drawImage(robot, 0, 0, size.width, size.height, this);
        g.drawImage(hook, size.width/2+5, height, size.width*2/5, size.height/15, this);
        if (value<.02 || value>.98){
            g.setColor(Color.GREEN);
        }
        else {
            g.setColor(Color.RED);
        }
        g.fillRect(0, 0, size.width*3/20, size.height*3/25);
    }
}
