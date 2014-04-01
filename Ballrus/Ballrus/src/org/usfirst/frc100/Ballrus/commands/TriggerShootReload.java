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
        requires(Ballrus.shooter);
        requires(Ballrus.tilter);
        setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.tilter.stop();
        Ballrus.shooter.stop();
        pistonRaised = Ballrus.intake.getTopPiston();
        Ballrus.intake.setTopPiston(true);
        grabDelay.reset();
        isFinished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if((pistonRaised || timeSinceInitialized() > 0.2)&&!isFinished) {
            Ballrus.shooter.setTrigger(true);
            isFinished = Ballrus.shooter.reload();
            grabDelay.reset();
            grabDelay.start();
        }
        System.out.println("isFinished: "+isFinished);
        System.out.println("grabDelay: "+grabDelay.get());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return grabDelay.get()>Preferences.shooterGrabDelay || timeSinceInitialized() > 5.0;
        
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println("End");
        Ballrus.shooter.setTrigger(false);
        new DeArmShooter().start();
        new PullBackFull().start();
        //new TiltToIntake().start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
