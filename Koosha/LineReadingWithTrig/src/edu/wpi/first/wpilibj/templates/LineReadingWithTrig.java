/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class LineReadingWithTrig extends IterativeRobot
{
    final AnalogChannel left = new AnalogChannel(6);
    final AnalogChannel right = new AnalogChannel(7);
    final AnalogTrigger lTrigger = new AnalogTrigger(left);
    final AnalogTrigger rTrigger = new AnalogTrigger(right);
    final Counter lCount = new Counter(lTrigger);
    final Counter rCount = new Counter(rTrigger);
    boolean lTriggered = true;
    boolean rTriggered = true;
    final Jaguar leftA = new Jaguar(6);
    final Jaguar leftB = new Jaguar(7);
    final Jaguar rightA = new Jaguar(8);
    final Jaguar rightB = new Jaguar(9);
    final RobotDrive drive = new RobotDrive(leftA, leftB, rightA, rightB);
    final Encoder lEncoder = new Encoder(4, 3);
    final Encoder rEncoder = new Encoder(1, 2);
    final Gyro gyro = new Gyro(1);
    final Joystick dualshock = new Joystick(1);
    final JoystickButton alignPress = new JoystickButton(dualshock, 4);
    final JoystickButton reverseDrive = new JoystickButton(dualshock, 6);
    final double width = 24.5;
    boolean prev6 = false;
    boolean runAlign = false;
    boolean reverse = true;
    double leftVal = 0.0;
    double rightVal = 0.0;
    boolean leftIsReady = false;
    boolean rightIsReady = false;
    private double distError;
    private double distOutput;
    private double angleError;
    private double angleOutput;
    private double direction;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        lTrigger.setLimitsRaw(900, 910);
        rTrigger.setLimitsRaw(900, 910);
        lCount.setUpSourceEdge(true, true);
        rCount.setUpSourceEdge(true, true);
        
    }
    
    public void disabledPeriodic()
    {
        SmartDashboard.putNumber("Left Value", left.getValue());
        SmartDashboard.putNumber("right Value", right.getValue());
        SmartDashboard.putBoolean("Left Black Line", lTriggered);
        SmartDashboard.putBoolean("Right Black Line", rTriggered);
        SmartDashboard.putNumber("Left Count", lCount.get());
        SmartDashboard.putNumber("Right Count", rCount.get());  
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    public void teleopInit()
    {
        lCount.start();
        rCount.start();
        lEncoder.start();
        rEncoder.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        lTriggered = (0==lCount.get()%2);
        rTriggered = (0==rCount.get()%2);

        if(!prev6 && reverseDrive.get())
            reverse = !reverse;
        
        if(alignPress.get())
            runAlign = true;

        if(runAlign)
        {
            align();
        }
        else
        {
            if(reverse)
            {
                leftVal = 0.75*dualshock.getThrottle();
                rightVal = 0.75*dualshock.getY();
            }
            else
            {
                leftVal = -0.75*dualshock.getY();
                rightVal = -0.75*dualshock.getThrottle();
            }

            drive.tankDrive(leftVal, rightVal);
            System.out.println("Align not runing");
            SmartDashboard.putBoolean("Is Aligning", false);
        }

        SmartDashboard.putNumber("Left Value", left.getValue());
        SmartDashboard.putNumber("right Value", right.getValue());
        SmartDashboard.putBoolean("Left Black Line", lTriggered);
        SmartDashboard.putBoolean("Right Black Line", rTriggered);
        SmartDashboard.putNumber("Left Count", lCount.get());
        SmartDashboard.putNumber("Right Count", rCount.get());

        prev6 = reverseDrive.get();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    public void align()
    {
        System.out.println("Align is runing");
        System.out.print("Left Count:" + lCount.get() + ", ");
        System.out.print("Right Count:" + rCount.get() + ", ");
        System.out.print("Left:" + lTriggered + ", ");
        System.out.print("Right:" + rTriggered + ", ");
        System.out.print("Left Encoder:" + lEncoder.get() + ", ");
        System.out.print("Right Encoder:" + rEncoder.get() + ", ");
        SmartDashboard.putBoolean("Is Aligning", true);
        
        if(lTriggered && rTriggered)
        {
            autoDriveStraight(216.0);
        }
        else if(!lTriggered && rTriggered)
        {
            if(!leftIsReady)
            {
                lEncoder.reset();
                rEncoder.reset();
            }
            else if(rightIsReady)
            {
                
            }
            leftIsReady = true;
        }

            System.out.println("Left Motor: " + leftA.get() + ", " + "Right Motor:" + rightA.get());
            
//            if(alignPress.get())
//            {
//                runAlign = false;
//                return;
//            }
    }

    // param is in inches 
    // returns true when distance is reached
    public boolean autoDriveStraight(double distance)
    {
        // Distance output
        distError = distance - (lEncoder.get() + rEncoder.get())/80;

        if (Math.abs(distError) > 1.0) // false if distance goal has been reached
        {
            distOutput = distError*SmartDashboard.getNumber("DriveStraight_kP", 0);
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
        angleError = direction - gyro.getAngle();
        while(angleError<0)
        {
            angleError+=360;
        }
        angleError = (angleError+180)%360-180;
        angleOutput = angleError*SmartDashboard.getNumber("AutoTurn_kP", 0);
        // Setting motors
        drive.arcadeDrive(distOutput, angleOutput);
        //updateDashboard();
        return false;
    }
}
