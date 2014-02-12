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
//        public JoystickButton alignToShootButton;
//        public JoystickButton quickTurnAroundButton;
        public JoystickButton intakeButton;
        public JoystickButton shootButton;

    public Joystick driverRight;
        public JoystickButton shiftButton;
        public JoystickButton quickTurnForwardButton;

    public Joystick manipulator;
        public JoystickButton tiltToShootLowButton;
        public JoystickButton tiltToIntakeButton;
        public JoystickButton tiltToShootTrussButton;
        public JoystickButton tiltToShootHighButton;
        public JoystickButton expelButton;
//        public JoystickButton shootButton;
//        public JoystickButton intakeButton;
        public JoystickButton manualShootButton;
        public JoystickButton armButton;
        public JoystickButton toggleManualControlButton;
        public JoystickButton shootWhileMovingButton;
        public JoystickButton resetGyroButton;
        public JoystickButton tiltToStowButton;

    public OI() {
        manipulator = new Joystick(3);
            armButton = new JoystickButton(manipulator, 8);
            armButton.whileHeld(new ArmShooter());
//            intakeButton = new JoystickButton(manipulator, 7);
//            intakeButton.whileHeld(new RunIntakeIn());
            shootButton = new JoystickButton(manipulator, 6);
            shootButton.whenPressed(new TriggerShootReload());
            manualShootButton = new JoystickButton(manipulator, 5);
//            expelButton = new JoystickButton(manipulator, 5);
//            expelButton.whileHeld(new RunIntakeOut());
            tiltToShootHighButton = new JoystickButton(manipulator, 4);
            tiltToShootHighButton.whenPressed(new TiltToShootHigh());
            tiltToShootTrussButton = new JoystickButton(manipulator, 3);
            tiltToShootTrussButton.whenPressed(new TiltToShootTruss());
            tiltToIntakeButton = new JoystickButton(manipulator, 2);
            tiltToIntakeButton.whenPressed(new TiltToIntake());
            tiltToShootLowButton = new JoystickButton(manipulator, 1);
            tiltToShootLowButton.whenPressed(new TiltToShootLow());
            tiltToStowButton = new JoystickButton(manipulator, 9);
            tiltToStowButton.whenPressed(new TiltToStow());
            toggleManualControlButton = new JoystickButton(manipulator, 10);
            toggleManualControlButton.toggleWhenPressed(new FullManualControl());

        driverRight = new Joystick(2);
            shiftButton = new JoystickButton(driverRight, 2);
            shiftButton.whileHeld(new ShiftLow());
            shootWhileMovingButton = new JoystickButton(driverRight, 1);
            shootWhileMovingButton.whileHeld(new FastestShotInTheWest());
            resetGyroButton = new JoystickButton(driverRight, 11);// click left joystick, for testing only
            resetGyroButton.whenPressed(new ResetGyro());

        driverLeft = new Joystick(1);
//            alignToShootButton = new JoystickButton(driverLeft, 1);
//            alignToShootButton.whileHeld(new AlignToShoot());
//            quickTurnAroundButton = new JoystickButton(driverLeft, 2);
//            quickTurnAroundButton.whenPressed(new AutoTurn(180));
//            quickTurnAroundButton.whenReleased(new Drive());
            intakeButton = new JoystickButton(driverLeft, 1);
            intakeButton.whileHeld(new RunIntakeIn());
            expelButton = new JoystickButton(driverLeft, 2);
            expelButton.whileHeld(new RunIntakeOut());

        // SmartDashboard Buttons
        if (Preferences.driveTrainTuningMode) {
            SmartDashboard.putData("AutoDriveStraight", new AutoDriveTest(true));
            SmartDashboard.putData("AutoTurn", new AutoDriveTest(false));
        }
        SmartDashboard.putData("ResetGyro", new ResetGyro());
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
