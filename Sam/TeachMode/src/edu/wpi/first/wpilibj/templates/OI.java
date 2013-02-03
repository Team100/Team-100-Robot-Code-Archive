
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.templates.commands.Memorize;
import edu.wpi.first.wpilibj.templates.commands.Reproduce;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    
    Joystick joystick = new Joystick(1);
    Encoder leftEncoder = new Encoder(3,4);
    Encoder rightEncoder = new Encoder(5,6);
    
    Button button =  new JoystickButton(joystick,5);
    Button button2 =  new JoystickButton(joystick,6);
    
    public int getLeftEncoder(){
        return leftEncoder.getRaw();
    }
    
    public int getRightEncoder(){
        return rightEncoder.getRaw();
    }
    
    public double getJoy1_y1() {
        return joystick.getY();
    }
    
    public double getJoy1_y2(){
        return joystick.getThrottle();
    }
    
    public OI(){
        //insert all triggered commands here
        button.whileHeld(new Memorize());
        button2.whenPressed(new Reproduce());
    }
    
    public void init(){
        leftEncoder.setReverseDirection(true);
        leftEncoder.start();     
        rightEncoder.start();
    }
    
}