/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team100.smartdashboard.extensions;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.gui.elements.bindings.AbstractTableWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.properties.StringProperty;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;



/**
 *
 * @author Daniel
 */

class ValueUpdater extends Thread implements Runnable {
    
    private ArtificialHorizon myWidget;
    private Dimension mySize;
    private double myPitch;
    private double myYaw;
    private double myPreviousYaw;
    private double myRoll;
    NetworkTable table = NetworkTable.getTable("ArtificialHorizon");
    
    public ValueUpdater(ArtificialHorizon ah) {
        table.putNumber("Pitch", 0.0);
        table.putNumber("Yaw", 0.0);
        table.putNumber("Roll", 0.0);
        System.out.println("Initted");
        myWidget = ah;
        mySize = ah.getSize();
        myPitch = table.getNumber("Pitch");
        myYaw = table.getNumber("Yaw");
        myRoll = table.getNumber("Roll");
    }
    
    @Override
    public void run() {
        while(true) {
            
            myPitch = 30 * table.getNumber("Pitch");
            myYaw = table.getNumber("Yaw");
            myRoll = 30 * table.getNumber("Roll");
            myWidget.repaint();
            myPreviousYaw = myYaw;
            try {
                Thread.sleep((int)100);
            } catch (InterruptedException ex) {
               // Logger.getLogger(ValueUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public double getPitch() {
        if(myPitch > 50) {
            return 50;
        } else if (myPitch < -50) {
            return -50;
        } else {
            return myPitch;
        }
    }
    
    public double getYaw() {
        return myYaw;
    }
    
    public double getRoll() {
        return myRoll;
    }
    
    public double getChangeInYaw() {
        return myYaw - myPreviousYaw;
    }
    
    public void setTablePath(String s) {
        table = NetworkTable.getTable(s);
    }
}


public class ArtificialHorizon extends StaticWidget {
    
    NetworkTable myTable = NetworkTable.getTable("ArtificialHorizon");
    public final StringProperty tablePath;
    GridBagConstraints bag;
    public JTextArea readouts;
    ValueUpdater updater;
    String pitchValue = "";
    String yawValue = "";
    String rollValue = "";
    
    public ArtificialHorizon() {
        updater = new ValueUpdater(this);
        
        setLayout(new GridBagLayout());
        
        tablePath = new StringProperty(this, "Table Path", "ArtificialHorizon");
        readouts = new JTextArea(5, 20);
        readouts.setVisible(true);
        readouts.setEditable(false);
        readouts.setEnabled(true);
        
        
        bag = new GridBagConstraints();
        
        
        add(readouts, 0, 0);
        
        revalidate();
        repaint();
    }

    public final void add(Component c, int x, int y) {
        bag.fill = GridBagConstraints.BOTH;
        bag.gridx = x;
        bag.gridy = y;
        add(c);
    }
    
    @Override
    public void init() {
        setPreferredSize(new Dimension(300, 300));
        myTable.putNumber("Pitch", 0.0);
        myTable.putNumber("Yaw", 0.0);
        myTable.putNumber("Roll", 0.0);
        updater.start();
    }

    @Override
    public void propertyChanged(Property p) {
        if(p == tablePath) {
            myTable = NetworkTable.getTable(tablePath.getValue());
            updater.setTablePath(tablePath.getValue());
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillArc(0, 0, 100, 100, 0, 180);
        g.setColor(Color.GREEN);
        g.fillArc(0, 0, 100, 100, 180, 180);
        g.setColor(Color.BLACK);
        g.drawOval(0, 0, 100, 100);
        g.drawLine(0, 50, 100, 50);
        drawCross(g, (int)(50 + updater.getChangeInYaw()), (int)(50 + updater.getPitch()), updater.getRoll(), 10);
        readouts.setText("");
        readouts.append("Pitch: " + updater.getPitch() + "\n" + "Yaw: " + updater.getYaw() + "\n" + "Roll: " + updater.getRoll() + "\n");
        
        
    }

    public void drawCross(Graphics g, int x, int y, double kilter, int length) {
        g.setColor(Color.BLACK);
        g.drawLine(x, y, x + (int)((length + 5) * Math.cos(Math.toRadians(kilter))), y + (int)((length + 5) * Math.sin(Math.toRadians(kilter))));
        g.drawLine(x, y, x + (int)(-(length + 5) * Math.cos(Math.toRadians(kilter))), y + (int)(-(length + 5) * Math.sin(Math.toRadians(kilter))));
        g.drawLine(x, y, x + (int)(length * Math.cos(Math.toRadians(kilter + 90))), y + (int)(length * Math.sin(Math.toRadians(kilter + 90))));
        g.drawLine(x, y, x + (int)(-length * Math.cos(Math.toRadians(kilter + 90))), y + (int)(-length * Math.sin(Math.toRadians(kilter + 90))));
        //g.drawLine((int)(length * Math.cos(kilter)) + x, (int)(length * Math.sin(kilter)) + y, (int)(length * Math.cos(kilter)) - x, (int)(length * Math.sin(kilter)) - y);
        //g.drawLine(x - 10, y, x + 10, y);
    }
    
    public NetworkTable getTable() {
        return myTable;
    }
    
}
