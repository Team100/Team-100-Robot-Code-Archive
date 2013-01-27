/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team100.smartdashboard.extensions;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.wpilibj.networktables.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Daniel
 */
public class myWidget extends StaticWidget {

    //Should grab the sub table "pidextension"
    NetworkTable table;
    private JTextField pField;
    private JTextField iField;
    private JTextField dField;
    private JTextField setpointField;
    private JTextArea pDisplay;
    private JTextArea iDisplay;
    private JTextArea dDisplay;
    JLabel pLabel;
    JLabel iLabel;
    JLabel dLabel;
    JLabel setpointLabel;
    private double m_kP = 0.0;
    private double m_kI = 0.0;
    private double m_kD = 0.0;
    private double setpoint = 0.0;

    public myWidget() {

        table = NetworkTable.getTable("SmartDashboard");


        //JLabels
        pLabel = new JLabel("P");
        iLabel = new JLabel("I");
        dLabel = new JLabel("D");
        setpointLabel = new JLabel("Setpoint");

        //Label Alignments
        pLabel.setHorizontalAlignment(JLabel.RIGHT);
        iLabel.setHorizontalAlignment(JLabel.RIGHT);
        dLabel.setHorizontalAlignment(JLabel.RIGHT);



        //Text Fields (editable)?
        pField = new JTextField(10);
        iField = new JTextField(10);
        dField = new JTextField(10);
        setpointField = new JTextField(10);
        pDisplay = new JTextArea();
        pDisplay.setColumns(10);
        pDisplay.setEditable(false);
        iDisplay = new JTextArea();
        iDisplay.setColumns(10);
        iDisplay.setEditable(false);
        dDisplay = new JTextArea();
        dDisplay.setColumns(10);
        dDisplay.setEditable(false);

        //Add Listeners to run when Enter(?) is pressed
        //sets internal variables to the value inputted
        //Dont put anything other than numbers in the fields
        setpointField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                String text = setpointField.getText();
                try {
                    table.putNumber("setpoint", Double.parseDouble(text));
                } catch(NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
        });
        pField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                String text = pField.getText();
                try {
                    m_kP = Double.parseDouble(text);
                    table.putNumber("p", m_kP);
                } catch(NumberFormatException ex) {
                    System.out.append("Exception!: " + ex);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = pField.getText();
                try {
                    m_kP = Double.parseDouble(text);
                    table.putNumber("p", m_kP);
                } catch(NumberFormatException ex) {
                    System.out.append("Exception!: " + ex);
                }
            }
            
        });
        iField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                String text = iField.getText();
                try {
                    m_kI = Double.parseDouble(text);
                    table.putNumber("i", m_kI);
                } catch(NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = iField.getText();
                try {
                    m_kI = Double.parseDouble(text);
                    table.putNumber("i", m_kI);
                } catch(NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
            }
            
        });
        
        dField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                String text = dField.getText();
                try {
                    m_kD = Double.parseDouble(text);
                    table.putNumber("d", m_kD);
                } catch(NumberFormatException ex) {
                    System.out.append("Exception!: " + ex);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = dField.getText();
                try {
                    m_kD = Double.parseDouble(text);
                    table.putNumber("d", m_kD);
                } catch(NumberFormatException ex) {
                    System.out.append("Exception!: " + ex);
                }
            }
            
        });

        //Grid for displaying stuff
        GridBagConstraints bag = new GridBagConstraints();

        add(pLabel, bag);
        add(pField, bag);
        add(iLabel, bag);
        add(iField, bag);
        add(dLabel, bag);
        add(dField, bag);
        add(setpointLabel, bag);
        add(setpointField, bag);
        //Makes sure the thing isnt invalid(??)
        revalidate();

        //This repaints all the stuff you're drawing (maybe)
        repaint();
    }

    public void setValue(Object o) {
    }

    @Override
    public void init() {
    }

    @Override
    public void propertyChanged(Property prprt) {
    }
}
