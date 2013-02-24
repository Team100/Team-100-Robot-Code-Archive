//PrimeToShoot turns on the shooter wheels while it is held down.
//It takes around one second of the wheels to get up to the proper speed for shooting.
//The shooter motors will stop when the button is released.

package org.usfirst.frc100.OrangaHang.commands;

/**
 *
 * @author Team100
 */
public class PrimeToShoot extends CommandBase {
    
    public PrimeToShoot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //TODO: do this inside shootFrisbees, if PID is enabled
        shooter.enable();
        shooter.setSetpoint(30);//FIXME
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //TODO: select PID or not via preferences, inside shootFrisbees
        //shooter.shootFrisbees(); use if no PID
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;//whileHeld
    }

    // Called once after isFinished returns true
    protected void end() {
        shooter.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
