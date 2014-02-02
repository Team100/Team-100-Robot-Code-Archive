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
    private JTextField minOutputField;
    private JTextField maxOutputField;
    private JTextField maxVelocityField;
    private JTextField myFilePath;
    JLabel pLabel;
    JLabel iLabel;
    JLabel dLabel;
    JLabel minOutputLabel;
    JLabel maxOutputLabel;
    JLabel maxVelocityLabel;
    private double m_kP = 0.0;
    private double m_kI = 0.0;
    private double m_kD = 0.0;
    private double minOut = 0.0;
    private double maxOut = 0.0;
    XYSeries velocityChartData;
    XYSeries outputChartData;
    private NumberAxis velocityChartYAxis;
    private NumberAxis velocityChartXAxis;
    private NumberAxis outputChartYAxis;
    private NumberAxis outputChartXAxis;
    XYSeriesCollection velocityChartDataset;
    XYSeriesCollection outputChartDataset;
    JPanel velocityChartPanel;
    JPanel outputChartPanel;
    public final IntegerProperty bufferSize;
    paintingThread thready;
    public final StringProperty tablePath;

    public myWidget() {

        thready = new paintingThread();
        bufferSize = new IntegerProperty(this, "Buffer Size (samples)", 2000);
        tablePath = new StringProperty(this, "Table Path", "PIDExtension");
        myFilePath = new JTextField(10);
        myFilePath.setText(tablePath.getValue());
        table = NetworkTable.getTable(tablePath.getValue());

        //JLabels
        pLabel = new JLabel("P");
        iLabel = new JLabel("I");
        dLabel = new JLabel("D");
        minOutputLabel = new JLabel("Min Output");
        maxOutputLabel = new JLabel("Max Output");
        maxVelocityLabel = new JLabel("Max Velocity");

        //Label Alignments
        pLabel.setHorizontalAlignment(JLabel.RIGHT);
        iLabel.setHorizontalAlignment(JLabel.RIGHT);
        dLabel.setHorizontalAlignment(JLabel.RIGHT);
        minOutputLabel.setHorizontalAlignment(JLabel.RIGHT);
        maxOutputLabel.setHorizontalAlignment(JLabel.RIGHT);

        //Text Fields (editable)?
        pField = new JTextField(10);
        iField = new JTextField(10);
        dField = new JTextField(10);
        minOutputField = new JTextField(4);
        maxOutputField = new JTextField(4);
        maxVelocityField = new JTextField(4);


        //Add Listeners to run when Enter(?) is pressed
        //sets internal variables to the value inputted
        //Dont put anything other than numbers in the fields
        addFieldListener(maxVelocityField, "kMaxVeloc");
        addFieldListener(maxOutputField, "kMaxOutput");
        addFieldListener(minOutputField, "kMinOutput");
        addFieldListener(pField, "kP");
        addFieldListener(iField, "kI");
        addFieldListener(dField, "kD");
        


        setLayout(new GridBagLayout());
        bag.anchor = GridBagConstraints.NORTHWEST;
        bag.insets = new Insets(5, 5, 5, 5);
        bag.gridwidth = 1;
        bag.gridheight = 1;
        add(myFilePath, 0, 0);
        add(pLabel, 0, 1);
        add(pField, 1, 1);
        add(iLabel, 0, 2);
        add(iField, 1, 2);
        add(dLabel, 0, 3);
        add(dField, 1, 3);
        add(minOutputLabel, 0, 4);
        add(minOutputField, 1, 4);
        add(maxOutputLabel, 0, 5);
        add(maxOutputField, 1, 5);
        add(maxVelocityLabel, 0, 6);
        add(maxVelocityField, 1, 6);

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

    private void insertPlaceHolders() {
        table.putNumber("Time", 0.0);
        table.putNumber("Inst_Veloc", 0.0);
        table.putNumber("kMaxVeloc", 0.0);
        table.putNumber("Output", 0.0);
    }
    
    @Override
    public void init() {
        myFilePath.setEditable(false);
        insertPlaceHolders();
        velocityChartData = new XYSeries("Velocity");
        velocityChartDataset = new XYSeriesCollection(velocityChartData);
        outputChartData = new XYSeries("Output");
        outputChartDataset = new XYSeriesCollection(outputChartData);
        JFreeChart outputChart = ChartFactory.createXYLineChart(
                "Output",
                "Time (units)",
                "Data",
                outputChartDataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        JFreeChart velocityChart = ChartFactory.createXYLineChart(
                "Velocity",
                "Time (units)",
                "Data",
                velocityChartDataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        velocityChartYAxis = (NumberAxis) velocityChart.getXYPlot().getRangeAxis();
        outputChartYAxis = (NumberAxis) outputChart.getXYPlot().getRangeAxis();
        velocityChartXAxis = (NumberAxis) velocityChart.getXYPlot().getDomainAxis();
        outputChartXAxis = (NumberAxis) outputChart.getXYPlot().getDomainAxis();
        velocityChartYAxis.setRange(18, 22);
        outputChartYAxis.setRange(18, 22);
        velocityChartYAxis.setTickUnit(new NumberTickUnit(0.5));
        outputChartYAxis.setTickUnit(new NumberTickUnit(0.1));
        velocityChartXAxis.setRange(0, 100);
        outputChartXAxis.setRange(0, 100);
        velocityChartXAxis.setTickUnit(new NumberTickUnit(10.0));
        outputChartXAxis.setTickUnit(new NumberTickUnit(10.0));
        velocityChartPanel = new ChartPanel(velocityChart);
        outputChartPanel = new ChartPanel(outputChart);
        velocityChartPanel.setPreferredSize(new Dimension(150, 100));
        outputChartPanel.setPreferredSize(new Dimension(150, 100));
        velocityChartPanel.setBackground(Color.DARK_GRAY);
        outputChartPanel.setBackground(Color.DARK_GRAY);
        bag.gridwidth = 3;
        bag.gridheight = 4;
        add(velocityChartPanel, 2, 4);
        add(outputChartPanel, 2, 0);
        thready.start();
        revalidate();
        repaint();

    }

    @Override
    public void propertyChanged(Property p) {
        if(p == bufferSize) {
            while(velocityChartData.getItemCount() > bufferSize.getValue()) {
                velocityChartData.remove(0);
            }
        } else if (p == tablePath) {
            table = NetworkTable.getTable(tablePath.getValue());
            myFilePath.setText("");
            myFilePath.setText(tablePath.getValue());
        }
        
    }

    @Override
    public void paintComponent(Graphics g) {
        Dimension size = getSize();
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, size.width - 1, size.height - 1);
       //g.drawOval((int)table.getNumber("time"), (int)table.getNumber("velocity"), 5, 5);
        velocityChartData.add(table.getNumber("Time"), table.getNumber("Inst_Veloc"));
        outputChartData.add(table.getNumber("Time"), table.getNumber("Output"));
        if(velocityChartData.getItemCount() > bufferSize.getValue()) {
            velocityChartData.remove(0);
        }
        if(outputChartData.getItemCount() > bufferSize.getValue()) {
            outputChartData.remove(0);
        }
        velocityChartYAxis.setRange(table.getNumber("Inst_Veloc") - 2.0, table.getNumber("Inst_Veloc") + 2.0);
        outputChartYAxis.setRange(-1, 1);
        velocityChartYAxis.setTickUnit(new NumberTickUnit(0.5));
        outputChartYAxis.setTickUnit(new NumberTickUnit(0.5));
        velocityChartXAxis.setRange(table.getNumber("Time") - 100.0, table.getNumber("Time") + 5.0);
        outputChartXAxis.setRange(table.getNumber("Time") - 100.0, table.getNumber("Time") + 5.0);
        velocityChartXAxis.setTickUnit(new NumberTickUnit(10.0));
        outputChartXAxis.setTickUnit(new NumberTickUnit(10.0));

    }
    
    private void addFieldListener(final JTextField f, final String s) {
        f.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = f.getText();
                try {
                    table.putNumber(s, Double.parseDouble(text));
                } catch (NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {

                String text = f.getText();
                try {
                    table.putNumber(s, Double.parseDouble(text));
                } catch (NumberFormatException ex) {
                    System.out.println("Exception!: " + ex);
                }
                repaint();
            }
        });
    }
}
