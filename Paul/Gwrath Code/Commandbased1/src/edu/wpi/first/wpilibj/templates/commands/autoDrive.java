//Sample autonomous program that turns a corner
package edu.wpi.first.wpilibj.templates.commands;

public class autoDrive extends CommandBase {
    int Distance;
    public autoDrive(int distance) {
        requires(drivetrain);
        Distance=distance;
    }

    protected void initialize() {
        drivetrain.resetsensors();
    }

    protected void execute() {
        drivetrain.autodrivestraight(Distance);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}