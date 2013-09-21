/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package protowidget;

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
public class LinearSpeedometer extends Widget
{
    public static final String NAME = "LinearSpeedometer";
    public static final DataType[] TYPES = {DataType.NUMBER};
    private double value = 0.0;
    
    @Override
    public void init()
    {
        this.setPreferredSize(new Dimension(10, 500));
    }

    @Override
    public void setValue(Object o)
    {
        value = (double) o;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.red);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.green);
        g.fillRect(0, (int)(0.5*this.getHeight()), this.getWidth(), (int)(-0.5*this.getHeight()*value));
    }
    
    @Override
    public void propertyChanged(Property prprt)
    {
        
    }
}
