package org.usfirst.frc100.OrangaHang;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.*;
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
    public static final JoystickButton shiftGearsLButton = new JoystickButton(driverLeft, 1);
    public static final JoystickButton quickTurnLButton = new JoystickButton(driverLeft, 2);
    public static final JoystickButton straightShootLButton = new JoystickButton(driverLeft, 3);
    public static final JoystickButton autoClimbLButton = new JoystickButton(driverLeft, 4);
    
    //DriverRight button declarations - numbers NOT final
    public static final JoystickButton shiftGearsRButton = new JoystickButton(driverRight, 1);
    public static final JoystickButton quickTurnRButton = new JoystickButton(driverRight, 2);
    public static final JoystickButton straightShootRButton = new JoystickButton(driverRight, 3);
    public static final JoystickButton autoClimbRButton = new JoystickButton(driverRight, 4);
    
    //Manipulator button declarations - number assignments are correct, don't change them!
    public static final JoystickButton tiltClimbButton = new JoystickButton(manipulator, 1);
    public static final JoystickButton tiltShootButton = new JoystickButton(manipulator, 2);
    public static final JoystickButton tiltIntakeButton = new JoystickButton(manipulator, 3);
    public static final JoystickButton abortClimbButton = new JoystickButton(manipulator, 4);
    public static final JoystickButton primeShootButton = new JoystickButton(manipulator, 5);
    public static final JoystickButton shootButton = new JoystickButton(manipulator, 6);
    public static final JoystickButton primeDumpButton = new JoystickButton(manipulator, 7);
    public static final JoystickButton intakeButton = new JoystickButton(manipulator, 8);

    public OI()
    {
        //Assigning commands to buttons

        //DriverLeft commands
        shiftGearsLButton.whileHeld(new ShiftGears());
        quickTurnLButton.whenPressed(new DeployFixedArms());
        straightShootLButton.whenPressed(new AlignToShoot());
        autoClimbLButton.whenPressed(new Climb());
        
        //DriverRight commands
        shiftGearsRButton.whileHeld(new ShiftGears());
        quickTurnRButton.whenPressed(new QuickTurn(90.0 * double2unit(driverRight.getX())));
        straightShootRButton.whenPressed(new AlignToShoot());
        autoClimbRButton.whenPressed(new Climb());

        //Manipulator commands
        tiltClimbButton.whenPressed(new TiltToClimb());
        tiltShootButton.whenPressed(new TiltToShoot());
        tiltIntakeButton.whenPressed(new TiltToIntake());
        abortClimbButton.whenPressed(new AbortClimb());
        primeShootButton.whileHeld(new PrimeToShoot());
        shootButton.whenPressed(new FrisbeesToShoot());
        primeDumpButton.whileHeld(new PrimeToDump());
        intakeButton.whileHeld(new Intake());

        //SmartDashboardButtons

    }//end constructor
    
    /**
     * @param d
     * @return +1 if d is positive; -1 if d is negative 
     */
    public static int double2unit(double d)
    {
        if(d == 0)
        {
            return 0;
        }
        
        int returnVal = (int) (Math.abs(d)/d);
        return returnVal;
    }

}//end OI