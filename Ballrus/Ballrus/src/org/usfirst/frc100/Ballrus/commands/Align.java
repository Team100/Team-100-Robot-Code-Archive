//untested
package org.usfirst.frc100.Ballrus.commands;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.subsystems.DriveTrain;

/**
 * Finds the line and lines up with it. Command is whenPressed.
 */
public class Align extends Command {

    private final DriveTrain driveTrain = Ballrus.driveTrain;

    private boolean lTriggered;
    private boolean rTriggered;
    private boolean leftInPos;
    private boolean rightInPos;
    private double displacement;
    private double angle;
    private boolean doneTurn;

    public Align() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        leftInPos = false;
        rightInPos = false;
        displacement = 0.0;
        doneTurn = false;
        driveTrain.startLineReaders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        lTriggered = driveTrain.getLeftTriggerState();
        rTriggered = driveTrain.getRightTriggerState();

        if (!lTriggered && !rTriggered) {
            if (doneTurn) {
                driveTrain.tankDrive(0.0, 0.0); // stops when finished aligning
            } else {
                driveTrain.driveStraight(0.6); // drive forward to find the line
            }
        } else if (lTriggered && !rTriggered) { // only the left trigger is on the line
            if (!leftInPos) {
                if (!rightInPos) {
                    driveTrain.resetEncoders(); // first time the robot sees the line so reset the encoders Once
                    driveTrain.driveStraight(0.6);
                } else { // once the robot has seen the line for a second time you can use math to calculate the exact angle it needs to turn
                    displacement = driveTrain.getEncoderInches();
                    angle = Math.toDegrees(MathUtils.atan(displacement / Preferences.width));
                }
                leftInPos = true;
            } else {
                driveTrain.driveStraight(0.6);
            }
        } else if (!lTriggered && rTriggered) { // same as the above code block but for the reverse case
            if (!rightInPos) {
                if (!leftInPos) {
                    driveTrain.resetEncoders();
                    driveTrain.driveStraight(0.6);
                } else {
                    displacement = -driveTrain.getEncoderInches();
                    angle = Math.toDegrees(MathUtils.atan(displacement / Preferences.width));
                }
                rightInPos = true;
            } else {
                driveTrain.driveStraight(0.6);
            }
        } else if (lTriggered && rTriggered) { // both sides are on the line
            if (doneTurn) {
                driveTrain.tankDrive(0.0, 0.0);
            } else { // if the angle is shallow then both triggers can be on the line before the robot aligns
                driveTrain.driveStraight(0.6);
            }
        }

        if (leftInPos && rightInPos && !doneTurn) { // once the robot has crossed the line twice it is ready to turn
            doneTurn = driveTrain.autoTurnByAngle(angle);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return doneTurn;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
