
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.DriveWithJoysticks;
import edu.wpi.first.wpilibj.templates.*;
import edu.wpi.first.wpilibj.templates.OI;

/**
 *
 */
public class DriveTrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    RobotDrive drive;
    Encoder rightEncode;
    Encoder leftEncode;


    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveWithJoysticks());
    }

    public DriveTrain()
    {
        drive = new RobotDrive(RobotMap.leftDrive, RobotMap.rightDrive);
        rightEncode = new Encoder(RobotMap.rightEncodeA, RobotMap.rightEncodeB);
        leftEncode = new Encoder(RobotMap.leftEncodeA, RobotMap.leftEncodeB);
        
    }

    public void Drive(double leftSpeed, double rightSpeed)
    {
        drive.tankDrive(leftSpeed, rightSpeed);
    }

    public double getLeftEncode()
    {
        return leftEncode.get();
    }

    public double getRightEncode()
    {
        return rightEncode.get();
    }

    public void Send2Dashboard()
    {

    }
}

