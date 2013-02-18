package team100;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.properties.StringProperty;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellRenderer;

/**
 * @author Sam
 */
public class CameraToggle extends StaticWidget
{
  public static final String NAME = "Camera Toggle";
  JButton button;
  StringProperty label = new StringProperty(this, "Label", "Camera");

    @Override
  public void init()
  {
    this.button = new JButton((String)this.label.getValue());
    System.out.println("Init CameraToggle");

    setLayout(new GridLayout());
    add(this.button, 0);
    this.button.setFocusable(false);
    
    this.button.addActionListener(new ActionListener(){
        @Override
      public void actionPerformed(ActionEvent ae) {
        Team100Camera.SwitchCamera();
        System.out.println("SWITCHING CAMERA");
       
      }
    });
    
    //this.button.setFocusable(true);
    //this.button.requestFocus();
    
  }

    @Override
    public void propertyChanged(Property property){}
}