//Sample autonomous program that turns a corner
package edu.wpi.first.wpilibj.templates.commands;

public class autonomousTest extends CommandBase {
    boolean isFinished=false;
    public autonomousTest() {
        requires(drivetrain);
    }

    protected void initialize() {
        drivetrain.resetsensors();
    }

    protected void execute() {
        drivetrain.autodrivestraight(24);
        drivetrain.autodriveturn(90);
        drivetrain.autodriveturn(-90);
        isFinished=true;
    }

    protected boolean isFinished() {
        return isFinished;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}