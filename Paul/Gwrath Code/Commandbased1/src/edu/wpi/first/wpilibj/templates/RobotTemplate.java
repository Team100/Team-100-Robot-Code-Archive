//Starts all commands
/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.*;

public class RobotTemplate extends IterativeRobot {

    Command autonomousCommand;
    Command teleopDrive;
    SendableChooser AutonomousChooser;

    public void robotInit() {
        AutonomousChooser=new SendableChooser();
        AutonomousChooser.addDefault("None", "none");
        AutonomousChooser.addObject("Drive 2 feet", "2");
        teleopDrive = new multiDrive();
        CommandBase.init();
        SmartDashboard.putData("Teleop", new multiDrive());
        SmartDashboard.putData("Straight", new autoDrive(48));
        SmartDashboard.putData("Right", new autoTurn(90));        
        SmartDashboard.putData("Left", new autoTurn(-90));        
    }

    public void autonomousInit() {
        if (AutonomousChooser.getSelected().equals("2")){
            autonomousCommand=new autoDrive(24);
            autonomousCommand.start();
        }
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        //autonomousCommand.cancel();
        teleopDrive.start();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
