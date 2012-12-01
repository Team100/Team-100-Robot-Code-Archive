//Sample autonomous program that turns a corner
package edu.wpi.first.wpilibj.templates.commands;

public class autonomousTest extends CommandBase {

    public autonomousTest() {
        requires(drivetrain);
    }

    protected void initialize() {
    }

    protected void execute() {
        drivetrain.resetsensors();
        drivetrain.autodrivestraight(.5,12);
        drivetrain.autodriveturn(-90);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}