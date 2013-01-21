/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team100;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.properties.StringProperty;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
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

    setLayout(new GridLayout());
    add(this.button, 0);

    this.button.setFocusable(false);
    this.button.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent ae) {
        Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        Team100Camera.SwitchCamera();
        if ((focusOwner instanceof JTextField)) {
              
          }
          ((JTextField)focusOwner).postActionEvent();
      }
    });
  }

    @Override
  public void propertyChanged(Property property)
  {
    if (property == this.label) {
          this.button.setText((String)this.label.getValue());
      }
  }
}