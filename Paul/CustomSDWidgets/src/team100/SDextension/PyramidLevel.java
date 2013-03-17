/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team100.SDextension;

import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
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
public class PyramidLevel extends Widget{
    public static final String NAME = "ClimberLevel";
    public static final DataType[] TYPES = { DataType.NUMBER };
    
    private double value = 0;
    BufferedImage pyramid = null;
    BufferedImage robot = null;
    
    @Override
    public void setValue(Object value) {
        this.value = (double)value;
        
        repaint();
    }
    
    @Override
    public void init() {
        setPreferredSize(new Dimension(250, 250));
        try {
            pyramid = ImageIO.read(Climber.class.getResourceAsStream("pyramid.png"));
            robot = ImageIO.read(Climber.class.getResourceAsStream("pyrbot.png"));
        } catch (IOException ex) {
        }
    }

    @Override
    public void propertyChanged(Property property) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension size = getSize();
        g.drawImage(pyramid, 0, 0, size.width, size.height, this);
        if (value==0){
            g.drawImage(robot, size.width*1/10, size.height*3/4, size.width*1/5, size.height*1/4, this);
        }
        if (value==1){
            g.drawImage(robot, size.width*2/10, size.height*1/2, size.width*1/5, size.height*1/4, this);
        }
        if (value==2){
            g.drawImage(robot, size.width*3/10, size.height*1/4, size.width*1/5, size.height*1/4, this);
        }
        if (value==3){
            g.drawImage(robot,size.width*4/10, 0, size.height*1/5, size.width*1/4, this);
        }
    }
}
