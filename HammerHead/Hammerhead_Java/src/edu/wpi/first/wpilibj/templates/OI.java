
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.DigitalIOButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.templates.commands.DeployArm;
import edu.wpi.first.wpilibj.templates.commands.Kick;
import edu.wpi.first.wpilibj.templates.commands.ResetCounter;
import edu.wpi.first.wpilibj.templates.commands.RetractArm;
import edu.wpi.first.wpilibj.templates.commands.SetKicker;
import edu.wpi.first.wpilibj.templates.commands.StopArm;

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
    
    Joystick joy1 = new Joystick(1);
    Joystick joy2 = new Joystick(2);
    Button trigger = new JoystickButton(joy1, 8);
    Button trigger2 = new JoystickButton(joy1, 7);
    Button joy1Btn1 = new JoystickButton(joy1, 1);
    Button joy1Btn2 = new JoystickButton(joy1, 2);
    Button joy1Btn3 = new JoystickButton(joy1, 3);
    Button joy1Btn4 = new JoystickButton(joy1, 4);
    
    public double getJoy1_y1() {
        return -joy1.getY();
    }
    public double getJoy1_y2() {
        return joy1.getThrottle();
    }
    public boolean getjoy1Btn1() {
        return joy1Btn1.get();
    }
    public boolean getjoy1Btn2() {
        return joy1Btn2.get();
    }
    public boolean getjoy1Btn3() {
        return joy1Btn3.get();
    }
    public boolean getjoy1Btn4() {
        return joy1Btn4.get();
    }
    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    public DigitalIOButton kickerButton = new DigitalIOButton(10);
    
    public boolean isKickerReady() {
        return kickerButton.get();
    }
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
    
    public OI() {
        trigger2.whenPressed(new ResetCounter());
        trigger.whileHeld(new Kick());
        trigger.whenReleased(new SetKicker());
        joy1Btn2.whileHeld(new DeployArm());
        joy1Btn2.whenReleased(new StopArm());
        joy1Btn4.whileHeld(new RetractArm());
        joy1Btn4.whenReleased(new StopArm());
        
    }
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}

