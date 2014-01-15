/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team100.SDextension;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 *
 * @author Student
 */
public class CameraAim extends Widget{
    public static final String NAME = "CameraAim";
    public static final DataType[] TYPES = { DataType.NUMBER };//(x,y)=>1000*(int)((x+1)*500)+(int)((y+1)*500)
    
    private double x=0, y=0;
    
    @Override
    public void init() {
        setPreferredSize(new Dimension(200, 200));
    }

    @Override
    public void propertyChanged(Property prprt) {
        
    }

    @Override
    public void setValue(Object o) {
        double i = (double)o;
        x=((int)(i/1000.0))/1000.0;
        y=(i%1000.0)/1000.0;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.red);
        g.fillOval((int)(this.getWidth()*x)-10, (int)(getHeight()*y)-10, 20, 20);
    }
}
