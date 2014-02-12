//ready
package org.usfirst.frc100.Robot2014;

import org.usfirst.frc100.Robot2014.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public JoystickButton alignToShootButton;
    public JoystickButton quickTurnAroundButton;
    public Joystick driverLeft;
    public JoystickButton shiftButton;
    public JoystickButton quickTurnForwardButton;
    public Joystick driverRight;
    public JoystickButton tiltToShootLowButton;
    public JoystickButton tiltToIntakeButton;
    public JoystickButton tiltToShootTrussButton;
    public JoystickButton tiltToShootHighButton;
    public JoystickButton expelButton;
    public JoystickButton shootButton;
    public JoystickButton intakeButton;
    public JoystickButton armButton;
    public JoystickButton toggleLowRollerButton;
    public JoystickButton toggleManualControlButton;
    public Joystick manipulator;

    public OI() {
        manipulator = new Joystick(3);
        armButton = new JoystickButton(manipulator, 8);
        armButton.whileHeld(new ArmShooter());
        intakeButton = new JoystickButton(manipulator, 7);
        intakeButton.whileHeld(new RunIntakeIn());
        shootButton = new JoystickButton(manipulator, 6);
        shootButton.whenPressed(new TriggerShootReload());
        expelButton = new JoystickButton(manipulator, 5);
        expelButton.whileHeld(new RunIntakeOut());
        tiltToShootHighButton = new JoystickButton(manipulator, 4);
        tiltToShootHighButton.whenPressed(new TiltToShootHigh());
        tiltToShootTrussButton = new JoystickButton(manipulator, 3);
        tiltToShootTrussButton.whenPressed(new TiltToShootTruss());
        tiltToIntakeButton = new JoystickButton(manipulator, 2);
        tiltToIntakeButton.whenPressed(new TiltToIntake());
        tiltToShootLowButton = new JoystickButton(manipulator, 1);
        tiltToShootLowButton.whenPressed(new TiltToShootLow());
        toggleLowRollerButton = new JoystickButton(manipulator, 9);
        toggleLowRollerButton.whenPressed(new ToggleLowerRoller());
        toggleManualControlButton = new JoystickButton(manipulator, 10);
        toggleManualControlButton.toggleWhenPressed(new FullManualControl());

        driverRight = new Joystick(2);
        shiftButton = new JoystickButton(driverRight, 1);
        shiftButton.whileHeld(new ShiftLow());
        quickTurnForwardButton = new JoystickButton(driverRight, 2);
        quickTurnForwardButton.whileHeld(new AutoTurn(0,true));

        driverLeft = new Joystick(1);
        alignToShootButton = new JoystickButton(driverLeft, 1);
        alignToShootButton.whileHeld(new AlignToShoot());
        quickTurnAroundButton = new JoystickButton(driverLeft, 2);
        quickTurnAroundButton.whileHeld(new AutoTurn(180));

        // SmartDashboard Buttons
//        SmartDashboard.putData("ShiftLow", new ShiftLow());
//        SmartDashboard.putData("TiltToIntake", new TiltToIntake());
//        SmartDashboard.putData("TiltToShootTruss", new TiltToShootTruss());
//        SmartDashboard.putData("TiltToShootLow", new TiltToShootLow());
//        SmartDashboard.putData("TiltToShootHigh", new TiltToShootHigh());
//        SmartDashboard.putData("TriggerShootReload", new TriggerShootReload());
//        SmartDashboard.putData("RunIntakeIn", new RunIntakeIn());
//        SmartDashboard.putData("RunIntakeOut", new RunIntakeOut());
        SmartDashboard.putData("AutoDriveStraight", new AutoDriveStraight(24));
//        SmartDashboard.putData("Catch", new Catch());
//        SmartDashboard.putData("ArmShooter", new ArmShooter());
//        SmartDashboard.putData("Drive", new Drive());
        SmartDashboard.putData("AutoTurn", new AutoTurn(0, true));
//        SmartDashboard.putData("AlignToShoot", new AlignToShoot());
//        SmartDashboard.putData("DeployLowerRoller", new DeployLowerRoller());
//        SmartDashboard.putData("RetractLowerRoller", new RetractLowerRoller());
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
