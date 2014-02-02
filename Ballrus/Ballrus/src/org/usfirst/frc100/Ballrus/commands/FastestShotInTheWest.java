//needs more testing
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * This class allows us to shoot while moving (using the ultrasonic sensor to
 * tell us when to shoot)! It's the fastest shot in the West! It is called while
 * held.
 */
public class FastestShotInTheWest extends Command {

    int state;
    boolean distanceReached;
    double speed;

    public FastestShotInTheWest() {
        requires(Ballrus.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.driveTrain.resetRangefinder();
        state = 0;
        distanceReached = false;

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        //rotate to face forward CHECK
        //drive straight CHECK
        //wait until the distance is apropos CHECK  
        //shoot while still driving straight CHECK
        //end when button released CHECK
        if (state == 0) {
            Ballrus.driveTrain.setDirection();
            state = 1;
//            System.out.println("STATE now equals 1");
        }

        if (state == 1 && distanceReached == false) {
            Ballrus.driveTrain.driveStraight(Ballrus.oi.getDriverRight().getY());

            speed = Ballrus.driveTrain.getEncoderSpeed();
//            System.out.println("........speed " + speed);

//            if (Ballrus.driveTrain.getRangeInches() <= (Preferences.ultraActualShootDistance + (0.5 * speed))) { //if MB1023
            if (Ballrus.driveTrain.getRangeInches() <= (Preferences.ultraActualShootDistance + (0.12 * speed))) {  //if MB1220
                
                distanceReached = true;
//                System.out.println("DIST is apropos " + Robot.driveTrain.getRangeInches());
            }
            

        }

        if (distanceReached == true) {
            new TriggerShootReload().start();
            Ballrus.driveTrain.stop();
            //Robot.driveTrain.driveStraight(Robot.oi.getDriverRight().getY());
            //System.out.println("SHOT...DEAD");
        }

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
