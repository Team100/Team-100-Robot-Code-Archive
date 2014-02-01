// RobotBuilder Version: 1.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.GeeWrathwithLineReader.subsystems;

import org.usfirst.frc100.GeeWrathwithLineReader.RobotMap;
import org.usfirst.frc100.GeeWrathwithLineReader.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class DriveTrain extends Subsystem {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final Encoder lEncoder = RobotMap.driveTrainlEncoder;
    private final Encoder rEncoder = RobotMap.driveTrainrEncoder;
    private final Gyro gyro = RobotMap.driveTraingyro;
    private final AnalogChannel lReader = RobotMap.driveTrainlReader;
    private final AnalogChannel rReader = RobotMap.driveTrainrReader;
    private final AnalogTrigger lTrigger = RobotMap.driveTrainlTrigger;
    private final AnalogTrigger rTrigger = RobotMap.driveTrainrTrigger;
    private final Counter lCount = new Counter(lTrigger);
    private final Counter rCount = new Counter(rTrigger);
    private final SpeedController leftA = RobotMap.driveTrainleftA;
    private final SpeedController leftB = RobotMap.driveTrainleftB;
    private final SpeedController rightA = RobotMap.driveTrainrightA;
    private final SpeedController rightB = RobotMap.driveTrainrightB;
    private final RobotDrive drive = RobotMap.driveTraindrive;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    private boolean reverse = true;
    private double distError;
    private double distOutput;
    private double angleError;
    private double angleOutput;
    private double bearing;
    private int prevLCount;
    private int prevRCount;
    private double maxSpeed;
    private long prevTime = System.currentTimeMillis();

    public DriveTrain()
    {
        lTrigger.setLimitsRaw(900, 915);
        rTrigger.setLimitsRaw(900, 915);
        lCount.setUpSourceEdge(true, true);
        rCount.setUpSourceEdge(true, true);
    }
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        setDefaultCommand(new DrivewithJoysticks());
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    // Encoders have to be started
    public void startEncoder()
    {
        lEncoder.start();
        rEncoder.start();
    }

    // Resets encoder values to Zero
    public void resetEncoder()
    {
        lEncoder.reset();
        rEncoder.reset();
    }

    // Inverse of the Start method
    public void stopEncoder()
    {
        lEncoder.stop();
        rEncoder.stop();
    }
    
    // Returns distance traveled in inches (if going straight)
    public double getDistance()
    {
        return -(lEncoder.getDistance() + rEncoder.getDistance()) / 2;
    }

    // Returns instantaneous velocity in inches/second (if going straight)
    public double getRate()
    {
        return -(lEncoder.getRate() + rEncoder.getRate()) / 2;
    }

    // Counters have to be started
    public void startCounter()
    {
        lCount.start();
        rCount.start();
    }

    // Returns the angle of the Gyro
    public double getAngle()
    {
        return gyro.getAngle();
    }

    // Resets the gyro values to Zero
    public void resetGyro()
    {
        gyro.reset();
    }

    // inverts the reverse varible so the Drive Train will run backwards
    public void reverseDriveTrain()
    {
        reverse = !reverse;
    }

    public boolean getLeftTrigger()
    {
        return 1 == lCount.get()%2;
    }
    
    public boolean getRightTrigger()
    {
        return 1 == rCount.get()%2;
    }
    
    public void setBearing(double angle)
    {
        bearing = angle;
    }
    
    public boolean getReverse()
    {
        return reverse;
    }
    
    public void tankDrive(double leftSpeed, double rightSpeed)
    {
        drive.tankDrive(leftSpeed, rightSpeed);
    }
    
    public void arcadeDrive(double forwardSpeed, double rotateSpeed)
    {
        drive.arcadeDrive(forwardSpeed, rotateSpeed);
    }

    public void arcadeDrive(double forwardSpeed, double rotateSpeed, boolean squared)
    {
        drive.arcadeDrive(forwardSpeed, rotateSpeed, squared);
    }

    // param is in inches 
    // returns true when distance is reached
    public boolean autoDriveStraight(double distance, double speedLimit)
    {
        speedLimit = Math.abs(speedLimit);
        // Distance output
        distError = distance - this.getDistance();

        if (Math.abs(distError) > 1.0) // false if distance goal has been reached
        {
            distOutput = distError * 0.15;
            if(Math.abs(distOutput) > speedLimit)
                distOutput = (Math.abs(distOutput) / distOutput) * speedLimit;
        }
        else // if distance goal has been reached
        {
            distOutput = 0;
            if (Math.abs(angleError) < 5.0) // if correct angle has been reached
            {
                drive.stopMotor();
                angleOutput=0;
                //updateDashboard();
                return true;
            }
        }

        // Angle output
        angleError = bearing - gyro.getAngle();
        while(angleError<0)
        {
            angleError+=360;
        }
        angleError = (angleError+180)%360-180;
        angleOutput = angleError * 0.15;
        // Setting motors
        drive.arcadeDrive(distOutput, angleOutput);
        //updateDashboard();
        return false;
    }

    // Rotates by an angle in degrees clockwise of straight, returns true when angle reached
    public boolean autoTurnByAngle(double angle)
    {
        angleError = angle - getAngle();
        System.out.print("Angle:" + angle + " ");
//        System.out.print("Gyro:" + getAngle() + " ");
        System.out.print("OldErr:" + angleError + " ");

        while (angleError < 0)
        {
            angleError += 360;
        }

        angleError = (angleError+180)%360-180;
        System.out.print("NewErr:" + angleError + " ");
        System.out.print("AngleOut:" + angleOutput + " ");
        if(Math.abs(angleError) < 0.9)
        {
            tankDrive(0.0, 0.0);
            angleOutput = 0;
            //updateDaangleOutput = angleError * 0.60;shboard();
            SmartDashboard.putBoolean("Is Turning", false);
            return true;
        }
        angleOutput = angleError * -0.07;
        
        arcadeDrive(0, angleOutput, false);
        //updateDashboard();
        SmartDashboard.putNumber("Angle Output", angleOutput);
        SmartDashboard.putBoolean("Is Turning", true);
        return false;
    }

    // Rotates to a specified angle in degrees relative to starting position, returns true when angle reached
    public boolean autoTurnToAngle(double angle)
    {
        angleError = angle - getAngle();
        System.out.print("Angle:" + angle + " ");

        while (angleError < 0)
        {
            angleError += 360;
        }

        angleError = (angleError+180)%360-180;
//        if(Math.abs(angleError) < 0.5)
//        {
//            tankDrive(0.0, 0.0);
//            angleOutput = 0;
//            //updateDaangleOutput = angleError * 0.60;shboard();
//            SmartDashboard.putBoolean("Is Turning", false);
//            return true;
//        }
        angleOutput = angleError * 1.5;
        
        arcadeDrive(0, angleOutput, false);
        //updateDashboard();
        SmartDashboard.putNumber("Angle Output", angleOutput);
        SmartDashboard.putBoolean("Is Turning", true);
        return false;
    }
    
    public void updateDashboard()
    {
        System.out.print("Gyro:" + getAngle() + " ");
//        System.out.print("AngleErr:" + angleError + " ");
//        System.out.print("AngleOut:" + angleOutput + " ");
        System.out.print("Left:" + getLeftTrigger() + " ");
        System.out.print("Right:" + getRightTrigger() + " ");
        System.out.print("Displacement:" + this.getDistance() + " ");
        System.out.print("Velocity:" + getRate() + " ");
        System.out.print("MaxSpeed:" + maxSpeed + " ");
        System.out.println("TimePassed:" + (System.currentTimeMillis() - prevTime));

        SmartDashboard.putNumber("Gyro Angle", getAngle());
        SmartDashboard.putNumber("Left Value", lReader.getValue());
        SmartDashboard.putNumber("Right Value", rReader.getValue());
        SmartDashboard.putNumber("Left Count", lCount.get());
        SmartDashboard.putNumber("Right Count", rCount.get());
        SmartDashboard.putBoolean("Left Black Line", getLeftTrigger());
        SmartDashboard.putBoolean("Right Black Line", getRightTrigger());
        SmartDashboard.putNumber("Velocity", getRate());

        if((((lCount.get() - prevLCount) > 0) || ((rCount.get() - prevRCount) > 0))
                && (getRate() > maxSpeed)) {
            maxSpeed = getRate();
        }
        prevLCount = lCount.get();
        prevRCount = rCount.get();
        prevTime = System.currentTimeMillis();
    }
}
