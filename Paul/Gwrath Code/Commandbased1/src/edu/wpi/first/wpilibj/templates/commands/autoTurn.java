//Sample autonomous program that turns a corner
package edu.wpi.first.wpilibj.templates.commands;

public class autoTurn extends CommandBase {
    int Degrees;
    public autoTurn(int degrees) {
        requires(drivetrain);
        Degrees=degrees;
    }

    protected void initialize() {
        drivetrain.resetsensors();
    }

    protected void execute() {
        drivetrain.autodriveturn(Degrees);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}