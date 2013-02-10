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
public class ClimberAngle extends Widget{
    public static final String NAME = "Climber Angle";
    public static final DataType[] TYPES = { DataType.NUMBER };
    
    private double value = 0;
    BufferedImage robot = null;
    int[] xarray=new int[4];
    int[] yarray=new int[4];
    
    @Override
    public void setValue(Object value) {
        this.value = (double)value;
        
        repaint();
    }
    
    @Override
    public void init() {
        setPreferredSize(new Dimension(220, 275));
        try {
            robot = ImageIO.read(Climber.class.getResourceAsStream("climberAngle.png"));
        } catch (IOException ex) {
        }
    }

    @Override
    public void propertyChanged(Property property) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        calculate();
        Dimension size = getSize();
        g.drawImage(robot, 0, 0, size.width, size.height, this);
        g.setColor(Color.BLACK);
        g.fillPolygon(xarray, yarray, 4);
    }
    
    
    public void calculate(){
        int armWidth=15;
        double armLength=getSize().width;
        int height=getSize().height*16/20;
        xarray[0]=0;
        yarray[0]=height-armWidth;
        xarray[1]=armWidth;
        yarray[1]=height;
        
        xarray[2]=(int) (armLength*Math.cos(Math.toRadians(value)));
        yarray[2]=(int) (height-armLength*Math.sin(Math.toRadians(value)));
        xarray[3]=(int) (armLength*Math.cos(Math.toRadians(value)));
        yarray[3]=(int) (height-armLength*Math.sin(Math.toRadians(value)));
        
    }
}