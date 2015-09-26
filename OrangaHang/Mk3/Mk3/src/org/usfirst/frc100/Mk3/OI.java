package org.usfirst.frc100.Mk3;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.*;
import org.usfirst.frc100.Mk3.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    // Manipulator declarations
	// This is where changes will have to be made
    public static final Joystick manipulator = new Joystick(1);
        public static final JoystickButton useIntakeButton = new JoystickButton(manipulator, 1);
        public static final JoystickButton lowerIntakeButton = new JoystickButton(manipulator, 2);
        public static final JoystickButton raiseIntakeButton = new JoystickButton(manipulator, 3);
        public static final JoystickButton driveStraightButton = new JoystickButton(manipulator, 4);
        public static final JoystickButton tiltUpButton = new JoystickButton(manipulator, 5);
        public static final JoystickButton shootButton = new JoystickButton(manipulator, 6);
        public static final JoystickButton tiltDownButton = new JoystickButton(manipulator, 7);
        public static final JoystickButton primeHighSpeedButton = new JoystickButton(manipulator, 8);
        public static final JoystickButton unjamButton = new JoystickButton(manipulator, 9);
        public static final JoystickButton primeLowSpeedButton = new JoystickButton(manipulator, 10);

    // Driver Left declarations
    public static final Joystick driverLeft = new Joystick(2);
        public static final JoystickButton hangButton = new JoystickButton(driverLeft, 1);

    // Driver Right declararations
    public static final Joystick driverRight = new Joystick(3); 
        public static final JoystickButton shiftGearsButton = new JoystickButton(driverRight, 1);

    public OI() {
        //Assigning commands to buttons

        //DriverLeft commands
//        hangButton.whenPressed(new ToggleHanger());

        //DriverRight commands
        shiftGearsButton.whileHeld(new ShiftDown());
        shiftGearsButton.whenReleased(new ShiftUp());

        //Manipulator commands
        useIntakeButton.whileHeld(new UseIntake());
        lowerIntakeButton.whenPressed(new LowerIntake());
        raiseIntakeButton.whenPressed(new RaiseIntake());
        driveStraightButton.whileHeld(new DriveStraight());
        tiltUpButton.whenPressed(new TiltUp());
        shootButton.whileHeld(new Shoot(0, 180));
        tiltDownButton.whenPressed(new TiltDown());
        primeHighSpeedButton.whileHeld(new PrimeHighSpeed());
        unjamButton.whileHeld(new UnjamFrisbees());
        primeLowSpeedButton.whileHeld(new PrimeLowSpeed());
    }//end constructor
}//end OI