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
import edu.wpi.first.wpilibj.templates.commands.*;

public class RobotTemplate extends IterativeRobot {

    Command autonomousCommand;
    Command teleopDrive;

    public void robotInit() {
        autonomousCommand = new autonomousTest();
        teleopDrive = new multiDrive();
        CommandBase.init();
    }

    public void autonomousInit() {
        autonomousCommand.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        autonomousCommand.cancel();
        teleopDrive.start();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
