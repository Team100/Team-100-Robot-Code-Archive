
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
    public static double left;
    public static double right;
    
    Button button =  new JoystickButton(joystick,1);
    Button button2 =  new JoystickButton(joystick,2);
    
    public double getLeftEncoder(){
        return leftEncoder.getRate();
    }
    
    public double getRightEncoder(){
        return rightEncoder.getRate();
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
        
        left = leftEncoder.getRate();
        right = rightEncoder.getRate();
    }
    
    public void init(){
        leftEncoder.setReverseDirection(true);
        leftEncoder.start();     
        rightEncoder.start();
    }
    
}

