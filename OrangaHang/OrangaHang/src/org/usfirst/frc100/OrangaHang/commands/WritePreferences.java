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
        table = NetworkTable.getTable("PIDSystems");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //The syntax for naming keys of PID Widgets is      name + (kP | kI | kD | kMaxOutput | kMinOutput | kMaxVeloc)
        table = NetworkTable.getTable("PIDSystems/FrontShooterPID");
        writePIDPreferences(table, "FrontShooter");
        table = NetworkTable.getTable("PIDSystems/BackShooterPID");
        writePIDPreferences(table, "BackShooter");
        //Save the Preferences
        p.save();
    }
    
    private void writePIDPreferences(NetworkTable t, String s) {
        p.putDouble(s + "kP", t.getNumber("kP"));
        p.putDouble(s + "kI", t.getNumber("kI"));
        p.putDouble(s + "kD", t.getNumber("kD"));
        p.putDouble(s + "kMaxOutput", t.getNumber("kMaxOutput"));
        p.putDouble(s + "kMinOutput", t.getNumber("kMinOutput"));
        p.putDouble(s + "kMaxVeloc", t.getNumber("kMaxVeloc"));
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
