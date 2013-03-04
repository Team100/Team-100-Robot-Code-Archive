/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 * @author Daniel
 */
public class WritePreferences extends CommandBase {
    
    Preferences p;
    NetworkTable table;
    
    public WritePreferences() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        p = Preferences.getInstance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //The syntax for naming keys of PID Widgets is      name + (kP | kI | kD | kMaxOutput | kMinOutput | kMaxVeloc)
        table = NetworkTable.getTable("SmartDasbhaord/FrontShooter");
        writePIDPreferences(table, "FrontShooter");
        table = NetworkTable.getTable("SmartDashboard/BackShooter");
        writePIDPreferences(table, "BackShooter");
        //Save the Preferences
        p.save();
    }
    
    private void writePIDPreferences(NetworkTable t, String s) {
        p.putDouble(s + "P", t.getNumber("P"));
        p.putDouble(s + "I", t.getNumber("I"));
        p.putDouble(s + "D", t.getNumber("D"));
        p.putDouble(s + "MaxOutput", t.getNumber("MaxOutput"));
        p.putDouble(s + "MinOutput", t.getNumber("MinOutput"));
        p.putDouble(s + "MaxVelocity", t.getNumber("MaxVelocity"));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
