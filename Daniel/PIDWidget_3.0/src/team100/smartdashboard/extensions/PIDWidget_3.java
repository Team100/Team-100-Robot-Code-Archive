/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team100.smartdashboard.extensions;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.gui.elements.bindings.AbstractTableWidget;
import edu.wpi.first.smartdashboard.gui.elements.bindings.AbstractTableWidget.NumberTableField;
import edu.wpi.first.smartdashboard.livewindow.elements.NameTag;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.smartdashboard.types.named.PIDType;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

/**
 *
 * @author Daniel
 */
public class PIDWidget_3 extends AbstractTableWidget{

    public static final DataType[] TYPES = {PidgetType.get()};
    
    private StringTableField pField;
    private StringTableField iField;
    private StringTableField dField;
    private StringTableField maxOutField;
    private StringTableField minOutField;
    private StringTableField maxVelField;
    
    private JLabel pLabel;
    private JLabel iLabel;
    private JLabel dLabel;
    private JLabel maxOutLabel;
    private JLabel minOutLabel;
    private JLabel maxVelLabel;
    
    private GridBagConstraints c;
    private int columns;
    
    NameTag nameTag;
    
    public PIDWidget_3() {
        
        setLayout(new GridBagLayout());
        
        nameTag = new NameTag("My Widget");
        
        pLabel = new JLabel("P:");
        iLabel = new JLabel("I:");
        dLabel = new JLabel("D:");
        maxOutLabel = new JLabel("Max Output:");
        minOutLabel = new JLabel("Min Output:");
        maxVelLabel = new JLabel("Max Velocity:");
        
        pField = new StringTableField("p");
        iField = new StringTableField("i");
        dField = new StringTableField("d");
        maxOutField = new StringTableField("maxOut");
        minOutField = new StringTableField("minOut");
        maxVelField = new StringTableField("maxVelocity");
        
        columns = 10;
        pField.setColumns(columns);
        iField.setColumns(columns);
        dField.setColumns(columns);
        maxOutField.setColumns(columns);
        minOutField.setColumns(columns);
        maxVelField.setColumns(columns);
        
        c = new GridBagConstraints();
        add(0, 0, nameTag);
        add(0, 1, pLabel);
        add(0, 2, iLabel);
        add(0, 3, dLabel);
        add(0, 4, maxOutLabel);
        add(0, 5, minOutLabel);
        add(0, 6, maxVelLabel);
        add(1, 1, pField);
        add(1, 2, iField);
        add(1, 3, dField);
        add(1, 4, maxOutField);
        add(1, 5, minOutField);
        add(1, 6, maxVelField);
        
        
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
        
        revalidate();
	repaint();
    }
    
    private void add(int x, int y, Component comp) {
       c.gridx = x;
       c.gridy = y;
       add(comp, c);
    }
    
    @Override
    public void init() {
        nameTag.setText(getFieldName());
    }

    @Override
    public void propertyChanged(Property prprt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
