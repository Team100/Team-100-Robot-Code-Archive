/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team0100.smartdashboard.extension.Widget;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Student
 */
public class Widget extends StaticWidget
{
    public static final String NAME = "StringWidget";
    String temp = "One,Two,Three,Four";
    private String[] buttonNames = this.split(temp);
    private JButton[] buttons = null;
    NetworkTable table = NetworkTable.getTable("SmartDashboard");
    private int i = 0;
    
    @Override
    public void init()
    {
        //JButton[] buttons = null;
        for(i=0; i<buttonNames.length; i++)
        {            
            buttons[i] = new JButton(buttonNames[i]);
            add(buttons[i], 0);
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae)
                {
                    System.out.println(buttons[i].getName());
                }
            });
        }
    }

    @Override
    public void propertyChanged(Property prprt)
    {
        
    }
    
    public String[] split(String patient)
    {
        String[] returnVal;
        returnVal = patient.split(",");
        for(int i=0; i<returnVal.length; i++)
        {
            System.out.println(returnVal[i]);
        }
        return returnVal;
    }
}
