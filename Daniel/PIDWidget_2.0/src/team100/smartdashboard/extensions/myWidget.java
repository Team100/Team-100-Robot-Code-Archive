/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team100.smartdashboard.extensions;

import edu.wpi.first.smartdashboard.gui.DashboardFrame;
import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.IntegerProperty;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.properties.StringProperty;
import edu.wpi.first.wpilibj.networktables.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Daniel
 */
class paintingThread extends Thread implements Runnable {
    
    DashboardFrame frame = (DashboardFrame) DashboardFrame.getInstance();
    
    
    @Override
        public void run() {
            while(true) {
                //System.out.println("Thready is RUNNING!");
                frame.repaint();
                try {
                    Thread.sleep((long)25.0);
                } catch (InterruptedException ex) {
                    System.out.println("Thread Exception!: " + ex);
                }
            }
        
    };
}


public class myWidget extends StaticWidget {

    //Grid for displaying stuff
    GridBagConstraints bag = new GridBagConstraints();
    //Should grab the sub table "pidextension"
    NetworkTable table;
    private JTextField pField;
    private JTextField iField;
    private JTextField dField;
    private JTextField setpointField;
    private JTextField minOutputField;
    private JTextField maxOutputField;
    JLabel pLabel;
    JLabel iLabel;
    JLabel dLabel;
    JLabel setpointLabel;
    JLabel minOutputLabel;
    JLabel maxOutputLabel;
    private double m_kP = 0.0;
    private double m_kI = 0.0;
    private double m_kD = 0.0;
    private double setpoint = 0.0;
    private double minOut = 0.0;
    private double maxOut = 0.0;
    XYSeries m_data;
    private NumberAxis yAxis;
    private NumberAxis xAxis;
    XYSeriesCollection m_dataset;
    JPanel m_chartPanel;
    public final IntegerProperty bufferSize;
    paintingThread thready;
    public final StringProperty tablePath;

    public myWidget() {

        thready = new paintingThread();
        bufferSize = new IntegerProperty(this, "Buffer Size (samples)", 2000);
        tablePath = new StringProperty(this, "Table Path", "PIDExtension");
        table = NetworkTable.getTable(tablePath.getValue());

        //JLabels
        pLabel = new JLabel("P");
        iLabel = new JLabel("I");
        dLabel = new JLabel("D");
        setpointLabel = new JLabel("Setpoint");
        minOutputLabel = new JLabel("Min Output");
        maxOutputLabel = new JLabel("Max Output");

        //Label Alignments
        pLabel.setHorizontalAlignment(JLabel.RIGHT);
        iLabel.setHorizontalAlignment(JLabel.RIGHT);
        dLabel.setHorizontalAlignment(JLabel.RIGHT);
        setpointLabel.setHorizontalAlignment(JLabel.RIGHT);
        minOutputLabel.setHorizontalAlignment(JLabel.RIGHT);
        maxOutputLabel.setHorizontalAlignment(JLabel.RIGHT);

        //Text Fields (editable)?
        pField = new JTextField(10);
        iField = new JTextField(10);
        dField = new JTextField(10);
        setpointField = new JTextField(10);
        minOutputField = new JTextField(4);
        maxOutputField = new JTextField(4);


        //Add Listeners to run when Enter(?) is pressed
        //sets internal variables to the value inputted
        //Dont put anything other than numbers in the fields

//        (new ITableListener(){
//
//            @Override
//            public void valueChanged(ITable itable, String string, Object o, boolean bln) {
//                table.putBoolean("boolean", true);
//                m_data.add(table.getNumber("time"), table.getNumber("velocity"));
//                if(m_data.getItemCount() > bufferSize.getValue()) {
//                    m_data.remove(0);
//                }
//                revalidate();
//                repaint();
//            }
//            
//        });

        maxOutputField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = maxOutputField.getText();
                try {
                    table.putNumber("maxOutput", Double.parseDouble(text));
                } catch (NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {

                String text = maxOutputField.getText();
                try {
                    table.putNumber("maxOutput", Double.parseDouble(text));
                } catch (NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
                repaint();
            }
        });
        minOutputField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = minOutputField.getText();
                try {
                    table.putNumber("minOutput", Double.parseDouble(text));
                } catch (NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = minOutputField.getText();
                try {
                    table.putNumber("minOutput", Double.parseDouble(text));
                } catch (NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
                repaint();
            }
        });
        setpointField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = setpointField.getText();
                try {
                    table.putNumber("setpoint", Double.parseDouble(text));
                } catch (NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = setpointField.getText();
                try {
                    table.putNumber("setpoint", Double.parseDouble(text));
                } catch (NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
                repaint();
            }
        });
        pField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = pField.getText();
                try {
                    m_kP = Double.parseDouble(text);
                    table.putNumber("p", m_kP);
                } catch (NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = pField.getText();
                try {
                    m_kP = Double.parseDouble(text);
                    table.putNumber("p", m_kP);
                } catch (NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
                repaint();
            }
        });
        iField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = iField.getText();
                try {
                    m_kI = Double.parseDouble(text);
                    table.putNumber("i", m_kI);
                } catch (NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = iField.getText();
                try {
                    m_kI = Double.parseDouble(text);
                    table.putNumber("i", m_kI);
                } catch (NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
                repaint();
            }
        });

        dField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = dField.getText();
                try {
                    m_kD = Double.parseDouble(text);
                    table.putNumber("d", m_kD);
                } catch (NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = dField.getText();
                try {
                    m_kD = Double.parseDouble(text);
                    table.putNumber("d", m_kD);
                } catch (NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
                repaint();
            }
        });


        setLayout(new GridBagLayout());
        bag.anchor = GridBagConstraints.NORTHWEST;
        bag.insets = new Insets(5, 5, 5, 5);
        bag.gridwidth = 1;
        bag.gridheight = 1;
        add(pLabel, 0, 0);
        add(pField, 1, 0);
        add(iLabel, 0, 1);
        add(iField, 1, 1);
        add(dLabel, 0, 2);
        add(dField, 1, 2);
        add(setpointLabel, 2, 0);
        add(setpointField, 3, 0);
        add(minOutputLabel, 0, 4);
        add(minOutputField, 1, 4);
        add(maxOutputLabel, 0, 5);
        add(maxOutputField, 1, 5);

        //Makes sure the thing isnt invalid(??)
        revalidate();

        //This repaints all the stuff you're drawing (maybe)
        repaint();
    }

    public final void add(Component component, int x, int y) {
        bag.gridx = x;
        bag.gridy = y;
        add(component, bag);
    }

    public void setValue(Object o) {
    }

    @Override
    public void init() {
        table.putNumber("time", 0.0);
        table.putNumber("velocity", 18.0);
        m_data = new XYSeries("Velocity");
        m_dataset = new XYSeriesCollection(m_data);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Velocity",
                "Time (units)",
                "Data",
                m_dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

        yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        xAxis = (NumberAxis) chart.getXYPlot().getDomainAxis();
        yAxis.setRange(18, 22);
        yAxis.setTickUnit(new NumberTickUnit(0.5));
        xAxis.setRange(0, 100);
        xAxis.setTickUnit(new NumberTickUnit(10.0));
        m_chartPanel = new ChartPanel(chart);
        m_chartPanel.setPreferredSize(new Dimension(300, 200));
        m_chartPanel.setBackground(Color.DARK_GRAY);
        bag.gridwidth = 3;
        bag.gridheight = 2;
        add(m_chartPanel, 2, 4);
        thready.start();
        revalidate();
        repaint();

    }

    @Override
    public void propertyChanged(Property p) {
        if(p == bufferSize) {
            while(m_data.getItemCount() > bufferSize.getValue()) {
                m_data.remove(0);
            }
        } else if (p == tablePath) {
            table = NetworkTable.getTable(tablePath.getValue());
        }
        
    }

    @Override
    public void paintComponent(Graphics g) {
        Dimension size = getSize();
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, size.width - 1, size.height - 1);
       //g.drawOval((int)table.getNumber("time"), (int)table.getNumber("velocity"), 5, 5);
        m_data.add(table.getNumber("time"), table.getNumber("velocity"));
        if(m_data.getItemCount() > bufferSize.getValue()) {
            m_data.remove(0);
        }
        yAxis.setRange(table.getNumber("velocity") - 2.0, table.getNumber("velocity") + 2.0);
        yAxis.setTickUnit(new NumberTickUnit(0.5));
        xAxis.setRange(table.getNumber("time") - 100.0, table.getNumber("time") + 5.0);
        xAxis.setTickUnit(new NumberTickUnit(10.0));

    }
}
