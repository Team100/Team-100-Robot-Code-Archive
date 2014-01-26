package org.usfirst.frc100.Mk3.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 * Triggers the shooter piston to move forward and back again.
 */
public class Shoot extends CommandBase {

    private final Timer timer = new Timer();
    private double backDuration;
    private double forwardDuration;
    private final double initialWait;
    private final double timeOut;

    public Shoot(double initialWait, double timeOut) {
        this.initialWait = initialWait;
        this.timeOut = timeOut;
        requires(feeder);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        backDuration = feeder.getShootBackDuration();
        forwardDuration = feeder.getShootForwardDuration();
        timer.reset();
        timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (shooter.getCurrentCommand() == null) { //was giving null pointer exeptions
            return;
        }
        if (timeSinceInitialized() > initialWait && !"ShooterOff".equals(shooter.getCurrentCommand().toString())) {
            //System.out.println("execute");
            if (timer.get() <= backDuration) {
                feeder.pullBack();
            } else if (timer.get() <= forwardDuration + backDuration) {
                feeder.pushForward();
            } else {
                timer.reset();
            }
        } else {
            timer.reset();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //System.out.println("isFinished");
        return (this.timeSinceInitialized() > timeOut);
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println("end");
        feeder.pushForward();
        timer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
