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
import java.awt.Font;
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
    private Font defaultFont = new Font("times new roman", Font.PLAIN, 9);
    
    @Override
    public void init()
    {
        this.setPreferredSize(new Dimension(30, 468));
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
        g.fillRect((int)(0.5*this.getWidth()), 2, this.getWidth(), this.getHeight()-4);
        g.setColor(Color.green);
        g.fillRect((int)(0.5*this.getWidth()), (int)(0.5*this.getHeight()), this.getWidth(), (int)(-0.5*(this.getHeight()-4)*value));
        g.setFont(defaultFont);
        //g.drawString("It Works!", 13, 144);
        g.drawString("1.0", 1, 7);
        g.drawString("0.0", 1, (int)(0.5*this.getHeight()));
        g.drawString("-1.0", 0, this.getHeight()-4);
    }
    
    @Override
    public void propertyChanged(Property prprt)
    {
        
    }
}
