//Switches between drives using sendable chooser
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.templates.OI;

public class multiDrive extends CommandBase {

    public SendableChooser driveChooser;

    public multiDrive() {
        requires(drivetrain);
        driveChooser = new SendableChooser();
        driveChooser.addDefault("Tank Drive", "tank");
        driveChooser.addObject("One Joystick Arcade Drive", "arcade1");
        driveChooser.addObject("Two Joystick Arcade Drive", "arcade2");
        SmartDashboard.putData("Drive Mode", driveChooser);
    }

    protected void initialize() {
    }

    protected void execute() {
        if (driveChooser.getSelected().equals("tank")){
            drivetrain.tankdrive(OI.getLeftJoystick(), OI.getRightJoystick());
        }
        if (driveChooser.getSelected().equals("arcade1")){
            drivetrain.arcadedrive1(OI.getLeftJoystick());
        }
        if (driveChooser.getSelected().equals("arcade2")){
            drivetrain.arcadedrive2(OI.getLeftJoystick(), OI.getRightJoystick());
        }
        drivetrain.putdata();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}