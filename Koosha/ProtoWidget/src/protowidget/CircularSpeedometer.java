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
public class CircularSpeedometer extends Widget
{
    public static final String NAME = "CircularSpeedometer";
    public static final DataType[] TYPES = {DataType.NUMBER};
    public double value = 0.0;
    
    @Override
    public void init()
    {
        this.setPreferredSize(new Dimension(288, 144));
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
        g.setColor(Color.black);
        g.fillArc(0, 0, this.getWidth(), 2*this.getHeight(), 0, 180);
    }

    @Override
    public void propertyChanged(Property prprt)
    {

    }
}
