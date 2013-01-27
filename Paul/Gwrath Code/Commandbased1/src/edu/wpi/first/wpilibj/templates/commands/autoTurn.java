//Sample autonomous program that turns a corner
package edu.wpi.first.wpilibj.templates.commands;

public class autoTurn extends CommandBase {
    int Degrees;
    boolean isFinished=false;
    public autoTurn(int degrees) {
        requires(drivetrain);
        Degrees=degrees;
    }

    protected void initialize() {
        drivetrain.resetsensors();
    }

    protected void execute() {
        while (drivetrain.autodriveturn(Degrees)){}
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