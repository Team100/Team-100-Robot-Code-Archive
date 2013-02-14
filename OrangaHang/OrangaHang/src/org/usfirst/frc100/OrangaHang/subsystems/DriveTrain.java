package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.commands.Drive;

/**
 *
 * @author Team100
 */
public class DriveTrain extends Subsystem {
    
    private final SpeedController rightMotor = RobotMap.driveRightMotor;
    private final SpeedController leftMotor = RobotMap.driveLeftMotor;
    private final Encoder rightEncoder = RobotMap.driveRightEncoder;
    private final Encoder leftEncoder = RobotMap.driveLeftEncoder;
    private final Gyro gyro = RobotMap.driveGyro;
    private final AnalogChannel ultraDist = RobotMap.driveUltrasonic;
    private final DoubleSolenoid shifter = RobotMap.driveGear;
    
    private boolean highGear = true;
    private boolean lowGear = false;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Drive());
    }//end initDefaultCommand
    
    public void tankDrive(double leftSpeed, double rightSpeed){
        leftMotor.set(leftSpeed);
        rightMotor.set(rightSpeed);
    }//end tankDrive
    
    public void shift(){
        if(highGear){
            lowGear = true;
            highGear = false;
            shifter.set(DoubleSolenoid.Value.kReverse);
        } else if (lowGear){
            highGear = true;
            lowGear = false;
            shifter.set(DoubleSolenoid.Value.kForward);
        }
    }//end shift
    
}//end DriveTrain
