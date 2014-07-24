//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;
import org.usfirst.frc100.Ballrus.Preferences;

/**
 * Shoots the ball and resets shooter, then starts a TiltToIntake command.
 * Command is when pressed.
 */
public class TriggerShootReload extends Command {

    private boolean isFinished;
    private boolean pistonRaised;
    Timer grabDelay = new Timer();
    
    public TriggerShootReload() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.tilter.stop();
        Ballrus.shooter.stop();
        pistonRaised = Ballrus.intake.getTopPiston();
        Ballrus.intake.setTopPiston(true);
        grabDelay.stop();
        grabDelay.reset();
        isFinished = false;
        Ballrus.shooter.setReloading(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if((pistonRaised || timeSinceInitialized() > 0.2)&&!isFinished) {
            Ballrus.shooter.setTrigger(true);
            isFinished = Ballrus.shooter.reload();
            if(isFinished){
                grabDelay.start();
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (isFinished&&grabDelay.get()>Preferences.shooterGrabDelay) || timeSinceInitialized() > 5.0;
        
    }

    // Called once after isFinished returns true
    protected void end() {
        Ballrus.shooter.setTrigger(false);
        Ballrus.shooter.setReloading(false);
        System.out.println("Reload completed");
        new DeArmShooter().start();
        new PullBackFull().start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
