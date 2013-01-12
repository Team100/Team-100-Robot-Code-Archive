//Sample autonomous program that turns a corner
package edu.wpi.first.wpilibj.templates.commands;

public class autoDrive extends CommandBase {
    int Distance;
    boolean isFinished=false;
    public autoDrive(int distance) {
        requires(drivetrain);
        Distance=distance;
    }

    protected void initialize() {
        drivetrain.resetsensors();
    }

    protected void execute() {
        while (drivetrain.autodrivestraight(Distance)){}
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