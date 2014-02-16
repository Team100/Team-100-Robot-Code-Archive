
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.Memorize;
import edu.wpi.first.wpilibj.templates.commands.Reproduce;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    
    public static final Joystick driverLeft = new Joystick(1);
    public static final Joystick driverRight = new Joystick(2);
    public static final Joystick manipulator = new Joystick(3);
    
    public OI(){
        
        //insert all triggered commands here
        SmartDashboard.putData(new Memorize());
        SmartDashboard.putData(new Reproduce());
        
    }
    
    public void init(){
    }
    
}