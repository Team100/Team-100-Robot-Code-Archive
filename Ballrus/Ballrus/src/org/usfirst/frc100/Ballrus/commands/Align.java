/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.Ballrus.commands;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.subsystems.DriveTrain;

/**
 *
 * @author Student
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
                driveTrain.driveStraight(0.6);
            }
            else if (!(leftInPos && rightInPos)) {
                driveTrain.driveStraight(0.6);
            }
        }
        else if (lTriggered && !rTriggered) {
            if (!leftInPos) {
                if (!rightInPos) {
                    driveTrain.resetEncoders();
                    driveTrain.driveStraight(0.6);
                }
                else {
                    displacement = driveTrain.getEncoderInches();
                    angle = Math.toDegrees(MathUtils.atan(displacement / Preferences.width));
                }
                leftInPos = true;
            }
            else {
                if (!doneTurn) {
                    driveTrain.driveStraight(0.6);
                }
                else {
                    driveTrain.driveStraight(0.6);
                }
            }
        } 
        else if (!lTriggered && rTriggered) {
            if (!rightInPos) {
                if (!leftInPos) {
                    driveTrain.resetEncoders();
                    driveTrain.driveStraight(0.6);
                }
                else {
                    displacement = -driveTrain.getEncoderInches();
                    angle = Math.toDegrees(MathUtils.atan(displacement / Preferences.width));
                }
                rightInPos = true;
            }
            else {
                if (!doneTurn) {
                    driveTrain.driveStraight(0.6);
                }
                else {
                    driveTrain.driveStraight(0.6);
                }
            }
        }
        else if (lTriggered && rTriggered) {
            if (doneTurn) {
                driveTrain.tankDrive(0.0, 0.0);
            }
            else {
                driveTrain.driveStraight(0.6);
            }
        }

        if (leftInPos && rightInPos && !doneTurn) {
            doneTurn = driveTrain.autoTurnByAngle(angle);
        }
        else {
            driveTrain.resetGyro();
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
