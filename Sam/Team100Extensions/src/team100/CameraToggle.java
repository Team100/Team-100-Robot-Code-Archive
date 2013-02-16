package team100;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.properties.StringProperty;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

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
  }

    @Override
    public void propertyChanged(Property property){}
  
}