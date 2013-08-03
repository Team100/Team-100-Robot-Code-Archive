package org.usfirst.frc100.Mk3;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.*;
import org.usfirst.frc100.Mk3.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    
    //Joystick declarations
    public static final Joystick driverLeft = new Joystick(1);
    public static final Joystick driverRight = new Joystick(2);
    public static final Joystick manipulator = new Joystick(3);
    
    //Button declarations
    //DriverLeft button declarations
    //public static final JoystickButton straightShootButton = new JoystickButton(driverLeft, 1);
    public static final JoystickButton hangButton = new JoystickButton(driverLeft, 1);
    
    //DriverRight button declarations
    public static final JoystickButton shiftGearsButton = new JoystickButton(driverRight, 1);
    //public static final JoystickButton quickTurnButton = new JoystickButton(driverRight, 2);
    
    //Manipulator button declarations
    public static final JoystickButton runIntakeButton = new JoystickButton(manipulator, 1);
    public static final JoystickButton tiltIntakeUpButton = new JoystickButton(manipulator, 2);
    public static final JoystickButton tiltIntakeDownButton = new JoystickButton(manipulator, 3);
    public static final JoystickButton tiltIntakeTestButton = new JoystickButton(manipulator, 4);
    public static final JoystickButton tiltUpButton = new JoystickButton(manipulator, 5);
    public static final JoystickButton shootButton = new JoystickButton(manipulator, 6);
    public static final JoystickButton tiltDownButton = new JoystickButton(manipulator, 7);
    public static final JoystickButton primeHighSpeedButton = new JoystickButton(manipulator, 8);
    public static final JoystickButton unjamButton = new JoystickButton(manipulator, 9);
    public static final JoystickButton primeLowSpeedButton = new JoystickButton(manipulator, 10);
    
    public OI() {  
        //Assigning commands to buttons

        //DriverLeft commands
        //straightShootButton.whileHeld(new AlignToShoot());
        hangButton.whenPressed(new ToggleHanger());
        
        //DriverRight commands
        shiftGearsButton.whileHeld(new ShiftGears());
        shiftGearsButton.whenReleased(new ShiftGearsBack());
        //quickTurnButton.whileHeld(new QuickTurn());
        
        //Manipulator commands
        runIntakeButton.whileHeld(new RunIntake());
        runIntakeButton.whenPressed(new TiltIntake(1));
        runIntakeButton.whenPressed(new TiltIntake(2));
        runIntakeButton.whenPressed(new TiltIntake(3));
        tiltUpButton.whenPressed(new TiltUp());
        shootButton.whileHeld(new Shoot(0,180));
        tiltDownButton.whenPressed(new TiltDown());
        primeHighSpeedButton.whileHeld(new PrimeHighSpeed());
        unjamButton.whileHeld(new UnjamFrisbees());
        primeLowSpeedButton.whileHeld(new PrimeLowSpeed());
    }//end constructor
    
}//end OI