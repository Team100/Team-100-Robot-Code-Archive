package org.usfirst.frc100.FrisBeast;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.*;
import org.usfirst.frc100.FrisBeast.commands.*;

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
    
    //Joystick declarations
    public static final Joystick driverLeft = new Joystick(1);
    public static final Joystick driverRight = new Joystick(2);
    public static final Joystick manipulator = new Joystick(3);
    
    //Button declarations
    //DriverLeft button declarations
    public static final JoystickButton straightShootButton = new JoystickButton(driverLeft, 1);
    public static final JoystickButton hangButton = new JoystickButton(driverLeft, 2);
    
    //DriverRight button declarations
    public static final JoystickButton shiftGearsButton = new JoystickButton(driverRight, 1);
    public static final JoystickButton quickTurnButton = new JoystickButton(driverRight, 2);
    
    //Manipulator button declarations
    //Button 1: (empty)
    //Button 2: (empty)
    //Button 3: (empty)
    //Button 4: (empty)
    public static final JoystickButton tiltUpButton = new JoystickButton(manipulator, 5);
    public static final JoystickButton shootButton = new JoystickButton(manipulator, 6);
    public static final JoystickButton tiltDownButton = new JoystickButton(manipulator, 7);
    public static final JoystickButton primeHighSpeedButton = new JoystickButton(manipulator, 8);
    //Button 9: (empty)
    public static final JoystickButton primeLowSpeedButton = new JoystickButton(manipulator, 10);
    
    public OI() {  
        //Assigning commands to buttons

        //DriverLeft commands
        straightShootButton.whileHeld(new AlignToShoot());
        hangButton.whenPressed(new ToggleHanger());
        
        //DriverRight commands
        shiftGearsButton.whileHeld(new ShiftGears());
        shiftGearsButton.whenReleased(new ShiftGearsBack());
        quickTurnButton.whileHeld(new QuickTurn());
        
        //Manipulator commands
        tiltUpButton.whenPressed(new TiltUp());
        shootButton.whileHeld(new Shoot());
        tiltDownButton.whenPressed(new TiltDown());
        primeHighSpeedButton.whileHeld(new PrimeHighSpeed());
        primeLowSpeedButton.whileHeld(new PrimeLowSpeed());
    }//end constructor
    
}//end OI