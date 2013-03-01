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
import java.lang.Math;

/**
 *
 * @author Student
 */
public class RobotTilt extends Widget{
    public static final String NAME = "ClimberTilt";
    public static final DataType[] TYPES = { DataType.NUMBER };
    
    private double value = 0;
    BufferedImage Meter = null;
    
    @Override
    public void setValue(Object value) {
        this.value = (double)value;
        
        repaint();
    }
    
    @Override
    public void init() {
        setPreferredSize(new Dimension(200, 25));
        try {
            Meter = ImageIO.read(Climber.class.getResourceAsStream("meter.png"));
        } catch (IOException ex) {
        }
    }

    @Override
    public void propertyChanged(Property property) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension size = getSize();
        double setValue= value;
        int barWidth=size.width/20;
        g.drawImage(Meter, 0, 0, size.width, size.height, this);
        g.setColor(Color.BLACK);
        g.fillRect((int)(size.width*setValue-barWidth/2), 0, barWidth, size.height);
    }
}