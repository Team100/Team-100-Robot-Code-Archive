//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Shoots the ball and resets shooter, then starts a TiltToIntake command.
 * Command is when pressed.
 */
public class TriggerShootReload extends Command {

    private boolean isFinished;
    private boolean hasShot;

    public TriggerShootReload() {
        requires(Ballrus.shooter);
        requires(Ballrus.tilter);
        setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.intake.setTopPiston(true);
        Ballrus.tilter.stop();
        Ballrus.shooter.stop();
        //if(Ballrus.intake.getTopPiston())
       // Ballrus.shooter.setTrigger(true);
        isFinished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(Ballrus.intake.getTopPiston() && !hasShot)
        {
            Ballrus.shooter.setTrigger(true);
            hasShot = true;
        }
        if(hasShot)
            isFinished = Ballrus.shooter.reload();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//        if(isFinished)
//        {
//            return true;
//        }
//        else if(timeSinceInitialized() > 10)
//        {
//            return true;
//        }
//        return isFinished; 
        return isFinished || (timeSinceInitialized() > 10.0);
    }

    // Called once after isFinished returns true
    protected void end() {
        Ballrus.shooter.setTrigger(false);
        new DeArmShooter().start();
        new TiltToIntake().start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
