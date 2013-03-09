package org.usfirst.frc100.OrangaHang;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.commands.*;

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
    //DriverLeft button declarations - numbers NOT final
    public static final JoystickButton straightShootButton = new JoystickButton(driverLeft, 1);
    public static final JoystickButton autoClimbButton = new JoystickButton(driverLeft, 2);
    
    //DriverRight button declarations - numbers NOT final
    public static final JoystickButton shiftGearsButton = new JoystickButton(driverRight, 1);
    public static final JoystickButton quickTurnButton = new JoystickButton(driverRight, 2);
    
    //Manipulator button declarations - number assignments are correct, don't change them!
    public static final JoystickButton tiltClimbButton = new JoystickButton(manipulator, 1);
    public static final JoystickButton tiltShootButton = new JoystickButton(manipulator, 2);
    public static final JoystickButton tiltIntakeButton = new JoystickButton(manipulator, 3);
    public static final JoystickButton tiltStartButton = new JoystickButton(manipulator, 4);
    public static final JoystickButton primeShootButton = new JoystickButton(manipulator, 5);
    public static final JoystickButton shootButton = new JoystickButton(manipulator, 6);
    public static final JoystickButton primeDumpButton = new JoystickButton(manipulator, 7);
    public static final JoystickButton intakeButton = new JoystickButton(manipulator, 8);
    public static final JoystickButton toggleArmsButton = new JoystickButton(manipulator, 9);
    // button 10 is available

    public OI()
    {
        //Assigning commands to buttons

        //DriverLeft commands
        straightShootButton.whileHeld(new AlignToShoot());
        autoClimbButton.whenPressed(new Climb());
        
        //DriverRight commands
        shiftGearsButton.whileHeld(new ShiftGears());
        shiftGearsButton.whenReleased(new ShiftGearsBack());
        quickTurnButton.whileHeld(new QuickTurn());
        
        //Manipulator commands
        tiltClimbButton.whenPressed(new TiltToClimb());
        tiltShootButton.whenPressed(new TiltToShoot());
        tiltIntakeButton.whenPressed(new TiltToIntake());
        tiltStartButton.whenPressed(new TiltToStart());
        intakeButton.whileHeld(new Intake());
        primeShootButton.whileHeld(new PrimeToShoot());
        primeDumpButton.whileHeld(new PrimeToDump());
        shootButton.whileHeld(new FrisbeesToShoot());
        toggleArmsButton.whenPressed(new ToggleArms());
        
        //SmartDashboardButtons
        SmartDashboard.putData(new Memorize());
        SmartDashboard.putData(new WritePreferences());
    }//end constructor
    
}//end OI