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
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Student
 */
public class ProtoWidget extends Widget
{
    public static final String NAME = "TrafficLight";
    public static final DataType[] TYPES = {DataType.BOOLEAN};
    private Timer timer = new Timer();
    private TimerTask task;
    private boolean value = false;
    private Color light = Color.red;
    private boolean indicator = false;

    @Override
    public void init()
    {
        this.setPreferredSize(new Dimension(100, 100));
    }

    @Override
    public void setValue(Object o)
   {
        value = (boolean) o;
        if (value)
        {
            light = Color.green;
            indicator = false;
        }
        else
        {
            if (!indicator)
            {
                light = Color.yellow;
                
                task = new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        light = Color.red;
                        repaint();
                    }
                };

                timer.schedule(task, 1000);
                indicator = true;
            }
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(light);

        g.fillOval(0, 0, this.getWidth(), this.getHeight());
    }

    @Override
    public void propertyChanged(Property prprt) {
    }
}
