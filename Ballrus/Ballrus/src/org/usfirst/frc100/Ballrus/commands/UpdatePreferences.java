//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.Preferences;

/**
 * Refreshes preference values to and from the cRIO. WARNING: DO NOT UPDATE
 * PREFERENCES ITERATIVELY!!!
 */
public class UpdatePreferences extends Command {

    boolean allPrefs;

    public UpdatePreferences(boolean allPrefs) {
        this.allPrefs = allPrefs;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        if (allPrefs) {
            Preferences.readFromFile(true);
        }
        else{
            Preferences.readFromFile(false);
        }
        if (Preferences.driveTrainTuningMode) {
            Preferences.changePreferenceInGeneralFile("driveDistance_kP", SmartDashboard.getNumber("DriveStraight_kP") + "");
            Preferences.changePreferenceInGeneralFile("driveDistance_kI", SmartDashboard.getNumber("DriveStraight_kI") + "");
            Preferences.changePreferenceInGeneralFile("driveDistance_kD", SmartDashboard.getNumber("DriveStraight_kD") + "");
            Preferences.changePreferenceInGeneralFile("driveDistanceLowGear_kP", SmartDashboard.getNumber("DriveStraightLowGear_kP") + "");
            Preferences.changePreferenceInGeneralFile("driveAngle_kP", SmartDashboard.getNumber("AutoTurn_kP") + "");
            Preferences.changePreferenceInGeneralFile("driveAngle_kI", SmartDashboard.getNumber("AutoTurn_kI") + "");
            Preferences.changePreferenceInGeneralFile("driveAngle_kD", SmartDashboard.getNumber("AutoTurn_kD") + "");
            Preferences.changePreferenceInGeneralFile("driveAngleLowGear_kP", SmartDashboard.getNumber("AutoTurnLowGear_kP") + "");
            Preferences.changePreferenceInGeneralFile("driveMotorMinValue", SmartDashboard.getNumber("DriveMotorMinValue") + "");
            Preferences.changePreferenceInGeneralFile("driveLowGearMotorMinValue", SmartDashboard.getNumber("DriveLowGearMotorMinValue") + "");
        }
        if (Preferences.tilterTuningMode) {
            Preferences.changePreferenceInGeneralFile("tilter_kP", SmartDashboard.getNumber("Tilter_kP") + "");
            Preferences.changePreferenceInGeneralFile("tilter_kI", SmartDashboard.getNumber("Tilter_kI") + "");
            Preferences.changePreferenceInGeneralFile("tilter_kD", SmartDashboard.getNumber("Tilter_kD") + "");
        }
        if (Preferences.intakeTuningMode) {
            Preferences.changePreferenceInGeneralFile("intakeInSpeed", SmartDashboard.getNumber("IntakeInSpeed") + "");
            Preferences.changePreferenceInGeneralFile("intakeOutSpeed", SmartDashboard.getNumber("IntakeOutSpeed") + "");
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
