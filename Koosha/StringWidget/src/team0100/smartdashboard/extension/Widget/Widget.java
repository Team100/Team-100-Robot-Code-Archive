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
    
        
    NetworkTable table = NetworkTable.getTable("SmartDashboard");
    
    @Override
    public void init()
    {
        JButton[] buttons = null;
        for(int i=0; i<buttonNames.length; i++)
        {
            final JButton tempbutton = new JButton(buttonNames[i]);
            
            buttons[i] = tempbutton; 
            add(tempbutton, i);
            tempbutton.setFocusable(false);
            tempbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae)
                {
                    System.out.println(tempbutton.getName());
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
