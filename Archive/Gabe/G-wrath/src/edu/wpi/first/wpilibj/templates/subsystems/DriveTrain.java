package edu.wpi.first.wpilibj.templates.subsystems;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.DriveWithJoystick;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
/**
 *
 * @author Student
 */
public class DriveTrain extends Subsystem
{
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    RobotDrive drive;
    Gyro gyro;
    Encoder encoder;
    DigitalInput dig1;
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        //setDefaultCommand(new DriveWithJoystick());
    }

    public DriveTrain()
    {
        drive = new RobotDrive(6,7,8,9);
        gyro = new Gyro(new AnalogChannel(1));
        encoder = new Encoder(3,4);
        encoder.setDistancePerPulse(3);
        encoder.start();
        dig1 = new DigitalInput(1);

    }
    public void straight(){
        drive.tankDrive(1.0, 0.0);
    }
    public void turnLeft(){
        drive.tankDrive(0.0, 1.0);
    }
    public void driveWithJoystick(Joystick stick){
        drive.tankDrive(-stick.getY(), -stick.getThrottle());
        SmartDashboard.putDouble("Left Joystick:", stick.getY());
        SmartDashboard.putDouble("Right Joystick:", stick.getThrottle());
        SmartDashboard.putDouble("Gyro Value:", gyro.getAngle());
        SmartDashboard.putDouble("Distance from Start:", encoder.getDistance());
        SmartDashboard.putBoolean("Moving?", dig1.get());
    }
    public void resetGyro(){
        gyro.reset();
    }
    public void driveWithGyro(){

        if(gyro.getAngle()>.5){
            drive.tankDrive(0.5, 0.6);
        }
        else if (gyro.getAngle()<-.5){
            drive.tankDrive(0.6, 0.5);
        }
        else {
            drive.tankDrive(0.5, 0.5);
        }
}
}