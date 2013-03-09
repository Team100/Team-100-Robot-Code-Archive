/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team100.smartdashboard.extensions;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.awt.*;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Daniel
 */
class Indicator extends JPanel {
    
    public boolean state = false;
    public Indicator() {
        setPreferredSize(new Dimension(10, 10));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if(state) {
            g.setColor(Color.green);
        } else {
            g.setColor(Color.red);
        }
        g.fillOval(0, 0, 10, 10);
    }
}

class GetStatus extends Thread implements Runnable {
    NetworkTable table = NetworkTable.getTable("Status");
    IOWidget widgey;
    short mask;
    
    public GetStatus(IOWidget widgey) {
        this.widgey = widgey;
        System.out.println("I got INITTERADASD");
    }
    
    @Override
    public void run() {
        while(true) {
            
            for(int i = 0; i < 8; i++) {
                
                widgey.analogFields.get(i).setText("" + table.getNumber("analog" + (i + 1)));
                widgey.analogFields.get(i).revalidate();
                widgey.analogFields.get(i).repaint();
                
            }
            
            for(int i = 0; i < 10; i++) {
                
                widgey.pwmOutputs.get(i).setText("" + table.getNumber("pwm" + (i + 1)));
                widgey.pwmOutputs.get(i).revalidate();
                widgey.pwmOutputs.get(i).repaint();
                
            }
           
            for(int i = 0; i < 13; i++) {
                
                widgey.digitalIndicators.get(i).state = (table.getNumber("dio" + (i + 1)) == 1);
                widgey.digitalIndicators.get(i).revalidate();
                widgey.digitalIndicators.get(i).repaint();
                
            }
            
            widgey.revalidate();
            widgey.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                
            }
        }
    }
}

public class IOWidget extends StaticWidget {

    NumberFormat f = NumberFormat.getInstance();
    NetworkTable table = NetworkTable.getTable("Status");
    GridBagConstraints bag = new GridBagConstraints();
    boolean[] digitalInputs = new boolean[14];
    ArrayList<JTextField> analogFields = new ArrayList<>();
    ArrayList<Indicator> digitalIndicators = new ArrayList<>();
    ArrayList<JTextField> pwmOutputs = new ArrayList<>();
    GetStatus statusGetter = new GetStatus(this);
    
    
    public IOWidget() {
        f.setMaximumIntegerDigits(2);
        f.setMaximumFractionDigits(4);
        setLayout(new GridBagLayout());
        bag.anchor = GridBagConstraints.NORTHWEST;
        bag.insets = new Insets(1, 1, 1, 1);
        bag.gridwidth = 2;
        bag.ipadx = 10;
        bag.gridheight = 1;
        add(0, 0, new JLabel("Digital Inputs"));
        add(2, 0, new JLabel("Analog Inputs"));
        add(4, 0, new JLabel("PWM Outputs"));
        bag.gridwidth = 1;
        bag.ipadx = 0;
        for(int i = 1; i < 15; i++) {
            add(0, i, new JLabel("" + i));
        }
        for(int i = 1; i < 9; i++) {
            add(2, i, new JLabel("" + i));
        }
        for(int i = 1; i < 11; i++) {
            add(4, i, new JLabel("" + i));
        }
        
    }
    
    @Override
    public void init() {
        
        for(int i = 0; i < 14; i++) {
            digitalInputs[i] = false;
            digitalIndicators.add(new Indicator());
        }
        for(int i = 0; i < 9; i++) {
            analogFields.add(new JTextField(6));
        }
        for(JTextField j : analogFields) {
            j.setEditable(false);
            
        }
        for(int i = 0; i < 10; i++) {
            pwmOutputs.add(new JTextField(6));
        }
        for(JTextField j : pwmOutputs) {
            j.setEditable(false);
        }
        
        //Placeholder Values
        for(int i = 1; i < 15; i++) {
            //short mask = (short) (0x01 << i);
            table.putNumber("dio" + i, 0.0);
        }
        
        for(int i = 1; i <= 10; i++) {
            table.putNumber("pwm" + i, 0.0);
        }
        for(int i = 1; i <= 8; i++) {
            table.putNumber("analog" + i, 0.0);
        }
        
        setPreferredSize(new Dimension(400, 400));
        statusGetter.start();
    }

    @Override
    public void propertyChanged(Property prprt) {
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Dimension size = getSize();
        g.setColor(Color.black);
        g.drawRect(0, 0, size.width - 1, size.height - 1);
        for(int i = 1; i < 15; i++) { //Handle Digital Input Indicators
            add(1, i, digitalIndicators.get(i - 1));
        }
        for(int i = 1; i < 9; i++) {
            add(3, i, analogFields.get(i - 1));
        }
        for(int i = 1; i < 11; i++) {
            add(5, i, pwmOutputs.get(i - 1));
        }
    }
    
    private void add(int x, int y, Component c) {
        bag.gridx = x;
        bag.gridy = y;
        add(c, bag);
    }
    
}
