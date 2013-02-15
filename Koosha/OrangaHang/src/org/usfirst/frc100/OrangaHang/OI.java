
package org.usfirst.frc100.OrangaHang;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.*;
import org.usfirst.frc100.OrangaHang.commands.*;
import org.usfirst.frc100.OrangaHang.commands.CommandBase;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    private static Joystick driverLeft = new Joystick(1);
        
    private static Joystick driverRight = new Joystick(2);
        private static JoystickButton rightTurnButton = new JoystickButton(driverRight, 2);
        
    private static Joystick manipulator = new Joystick(3);
        private static JoystickButton tiltClimbButton = new JoystickButton(manipulator, 1);
        private static JoystickButton tiltShootButton = new JoystickButton(manipulator, 2);
        private static JoystickButton tiltIntakeButton = new JoystickButton(manipulator, 3);
        private static JoystickButton climbUpButton = new JoystickButton(manipulator, 5);
        private static JoystickButton shootButton = new JoystickButton(manipulator, 6);
        private static JoystickButton climbDownButton = new JoystickButton(manipulator, 7);
        private static JoystickButton takeFrisbeesButton = new JoystickButton(manipulator, 8);
    
    public OI(){

        //Button declarations
        
//        DriverLeft button declarations - numbers NOT final
        //JoystickButton highGearLeftButton = new JoystickButton(driverLeft, 1);
        //JoystickButton lowGearLeftButton = new JoystickButton(driverLeft, 2);
        
//        DriverRight button declarations - numbers NOT final
        //JoystickButton highGearRightButton = new JoystickButton(driverRight, 1);
        //JoystickButton lowGearRightButton = new JoystickButton(driverRight,2);
        
        //Manipulator button declarations - number assignments are correct, don't change them!
        
        //Assigning commands to buttons
        
        //DriverLeft commands
        
        //DriverRight commands
        rightTurnButton.whenPressed(new QuickTurn(90.0 *  CommandBase.driveTrain.double2unit(this.getRightX()) ));
        
        //Manipulator commands
        climbUpButton.whenPressed(new Climb());
        shootButton.whenPressed(new Shoot());
        climbDownButton.whenPressed(new Climb());
        takeFrisbeesButton.whileHeld(new TakeFrisbees());
    
        //SmartDashboardButtons
        
    }//end constructor
    
    public double getLeftX()
    {
        return driverLeft.getX();
    }
    
    public double getLeftY()
    {
        return driverLeft.getY();
    }
    
    public double getRightX()
    {
        return driverRight.getX();
    }
    
    public double getRightY()
    {
        return driverRight.getY();
    }
    
    public boolean isJoystick()
    {
        return (Math.abs(driverLeft.getX()) + Math.abs(driverLeft.getY()) + 
                Math.abs(driverRight.getX()) + Math.abs(driverRight.getY())) > 0.2;
    }
    
}//end OI