package team100;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.properties.StringProperty;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

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
    this.button.setFocusable(true);
  
    this.button.addActionListener(new ActionListener(){
        @Override
      public void actionPerformed(ActionEvent ae) {
        Team100Camera.SwitchCamera();
        System.out.println("SWITCHING CAMERA");
       
      }
    });
    
    this.button.addKeyListener(new KeyListener() {

          @Override
          public void keyTyped(KeyEvent e) {
          }

          @Override
          public void keyPressed(KeyEvent e) {
          }

          @Override
          public void keyReleased(KeyEvent e) {
                if(e.getKeyChar() == 'c'){
                    Team100Camera.SwitchCamera();
                    System.out.println("SWITCHING CAMERA");
                }
          }
      });
    
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            button.requestFocus();
        }
    });
    
    
    //this.requestFocus();
  }

    @Override
    public void propertyChanged(Property property){}
}

