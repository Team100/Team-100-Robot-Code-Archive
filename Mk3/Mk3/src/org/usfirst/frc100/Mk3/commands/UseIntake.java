/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.Mk3.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Student
 */
public class UseIntake extends CommandBase {
    
    Timer t = new Timer();
    double delay1, delay2, delay3;
    
    public UseIntake() {
        requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        t.start();
        delay1=Preferences.getInstance().getDouble("IntakeDelay1", 0);
        delay2=Preferences.getInstance().getDouble("IntakeDelay2", 0);
        delay3=Preferences.getInstance().getDouble("IntakeDelay3", 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (t.get()>delay1&&t.get()<delay2){
            intake.setGripper(1, false);
        }
        if (t.get()>delay2){
            intake.setGripper(2, false);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return t.get()>delay1+delay2+delay3;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
