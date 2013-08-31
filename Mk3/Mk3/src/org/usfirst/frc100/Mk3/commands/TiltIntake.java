/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.Mk3.commands;

/**
 *
 * @author Student
 */
public class TiltIntake extends CommandBase {
    
    int position=0;
    boolean done=false;
    
    public TiltIntake(int position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires (intake);
        this.position=position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        intake.tiltToPosition(position);
        if(intake.inPosition){
            done=true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
