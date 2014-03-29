//ready
package org.usfirst.frc100.Ballrus;

import org.usfirst.frc100.Ballrus.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public Joystick driverLeft;
        public JoystickButton intakeButton;
        public JoystickButton expelButton;

    public Joystick driverRight;
        public JoystickButton shootWhileMovingButton;
        public JoystickButton shiftButton;
        public JoystickButton resetGyroButton;

    public Joystick manipulator;
        public JoystickButton tiltToShootLowButton;
        public JoystickButton tiltToIntakeButton;
        public JoystickButton tiltToShootTrussButton;
        public JoystickButton tiltToShootHighButton;
        public JoystickButton manualShootButton;
        public JoystickButton shootButton;
        public JoystickButton releaseShooterButton;
        public JoystickButton armButton;
        public JoystickButton tiltToStowButton;
        public JoystickButton toggleManualControlButton;
        public JoystickButton tiltToTrussPassButton;

    public OI() {
        driverLeft = new Joystick(1);
            intakeButton = new JoystickButton(driverLeft, 1);
            intakeButton.whileHeld(new RunIntakeIn());
            expelButton = new JoystickButton(driverLeft, 2);
            expelButton.whileHeld(new RunIntakeOut());
            
        driverRight = new Joystick(2);
            shootWhileMovingButton = new JoystickButton(driverRight, 1);
//            shootWhileMovingButton.whileHeld(new FastestShotInTheWest());
            shiftButton = new JoystickButton(driverRight, 2);
            shiftButton.whileHeld(new ShiftLow());
            resetGyroButton = new JoystickButton(driverRight, 11);// click left joystick, for testing only
            resetGyroButton.whenPressed(new ResetGyro());

        manipulator = new Joystick(3);
            tiltToShootLowButton = new JoystickButton(manipulator, 1);
            tiltToShootLowButton.whenPressed(new TiltToShootLow());
            tiltToIntakeButton = new JoystickButton(manipulator, 2);
            tiltToIntakeButton.whenPressed(new TiltToIntake());
            tiltToShootTrussButton = new JoystickButton(manipulator, 3);
            tiltToShootTrussButton.whenPressed(new TiltToShootTruss());
            tiltToShootHighButton = new JoystickButton(manipulator, 4);
            tiltToShootHighButton.whenPressed(new TiltToShootHigh());
            manualShootButton = new JoystickButton(manipulator, 5); // accessed by command
            shootButton = new JoystickButton(manipulator, 6);
            shootButton.whenPressed(new TriggerShootReload());
            releaseShooterButton = new JoystickButton(manipulator, 7);
            releaseShooterButton.whenPressed(new ReleaseShooter());
            armButton = new JoystickButton(manipulator, 8);
            armButton.whileHeld(new ArmShooter());
            tiltToStowButton = new JoystickButton(manipulator, 9);
            tiltToStowButton.whenPressed(new TiltToStow());
            toggleManualControlButton = new JoystickButton(manipulator, 10);
            toggleManualControlButton.toggleWhenPressed(new FullManualControl());
            tiltToTrussPassButton = new JoystickButton(manipulator, 11);
            tiltToTrussPassButton.whenPressed(new TiltToTrussPass());

        // SmartDashboard Buttons
        if (Preferences.driveTrainTuningMode) {
            SmartDashboard.putData("AutoDriveStraight", new AutoDriveTest(true));
            SmartDashboard.putData("AutoTurn", new AutoDriveTest(false));
        }
        if (Preferences.tilterTuningMode) {
            SmartDashboard.putData("TiltToTestAngle", new TiltToTestAngle());
        }
        SmartDashboard.putData("UpdatePreferences", new UpdatePreferences());
        SmartDashboard.putData("CalibrateShooter", new CalibrateShooter());
        SmartDashboard.putData("CalibrateTilter", new CalibrateTilter());
        SmartDashboard.putData("ResetGyro", new ResetGyro());
        SmartDashboard.putData("CalibrateGyro", new CalibrateGyro(.5));
        SmartDashboard.putData("ResetRangefinder", new ResetRangefinder());
    }

    public Joystick getDriverLeft() {
        return driverLeft;
    }

    public Joystick getDriverRight() {
        return driverRight;
    }

    public Joystick getManipulator() {
        return manipulator;
    }
}
