//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.Preferences;

/**
 * Refreshes preference values to and from the cRIO.
 * WARNING: DO NOT UPDATE PREFERENCES ITERATIVELY!!!
 */
public class  UpdatePreferences extends Command {
    
    public UpdatePreferences() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Preferences.readFromFile();
        if(Preferences.driveTrainTuningMode) {
            Preferences.changePreferenceInFile("driveDistance_kP", SmartDashboard.getNumber("DriveStraight_kP")+"");
            Preferences.changePreferenceInFile("driveDistance_kI", SmartDashboard.getNumber("DriveStraight_kI")+"");
            Preferences.changePreferenceInFile("driveDistance_kD", SmartDashboard.getNumber("DriveStraight_kD")+"");
            Preferences.changePreferenceInFile("driveDistanceLowGear_kP", SmartDashboard.getNumber("DriveStraightLowGear_kP")+"");
            Preferences.changePreferenceInFile("driveAngle_kP", SmartDashboard.getNumber("AutoTurn_kP")+"");
            Preferences.changePreferenceInFile("driveAngle_kI", SmartDashboard.getNumber("AutoTurn_kI")+"");
            Preferences.changePreferenceInFile("driveAngle_kD", SmartDashboard.getNumber("AutoTurn_kD")+"");
            Preferences.changePreferenceInFile("driveAngleLowGear_kP", SmartDashboard.getNumber("AutoTurnLowGear_kP")+"");
            Preferences.changePreferenceInFile("driveMotorMinValue", SmartDashboard.getNumber("DriveMotorMinValue")+"");
            Preferences.changePreferenceInFile("driveLowGearMotorMinValue", SmartDashboard.getNumber("DriveLowGearMotorMinValue")+"");
        }
        if(Preferences.tilterTuningMode) {
            Preferences.changePreferenceInFile("tilter_kP", SmartDashboard.getNumber("Tilter_kP")+"");
            Preferences.changePreferenceInFile("tilter_kI", SmartDashboard.getNumber("Tilter_kI")+"");
            Preferences.changePreferenceInFile("tilter_kD", SmartDashboard.getNumber("Tilter_kD")+"");
        }
        if(Preferences.intakeTuningMode) {
            Preferences.changePreferenceInFile("intakeInSpeed", SmartDashboard.getNumber("IntakeInSpeed")+"");
            Preferences.changePreferenceInFile("intakeOutSpeed", SmartDashboard.getNumber("IntakeOutSpeed")+"");
        }
        Preferences.writeToFile();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
    }
}
