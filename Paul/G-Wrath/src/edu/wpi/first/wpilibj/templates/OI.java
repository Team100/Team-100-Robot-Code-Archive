//Declares/initializes all of the operator inputs
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.*;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    Joystick leftJoystick = new Joystick(RobotMap.leftjoystick);
    Joystick rightJoystick = new Joystick(RobotMap.rightjoystick);
    JoystickButton leftButton1 = new JoystickButton(leftJoystick, RobotMap.leftjoystickbutton1);
    JoystickButton leftButton2 = new JoystickButton(leftJoystick, RobotMap.leftjoystickbutton2);
    JoystickButton rightButton1 = new JoystickButton(rightJoystick, RobotMap.rightjoystickbutton1);
    JoystickButton rightButton2 = new JoystickButton(rightJoystick, RobotMap.rightjoystickbutton2);

    public Joystick getLeftJoystick(){
        return leftJoystick; 
    }
    public Joystick getRightJoystick(){
        return rightJoystick;
    }
    public JoystickButton getLeftButton1(){
        return leftButton1;
    }
    public JoystickButton getLeftButton2(){
        return leftButton2;
    }
    public JoystickButton getRightButton1(){
        return rightButton1;
    }
    public JoystickButton getRightButton2(){
        return rightButton2;
    }



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
}