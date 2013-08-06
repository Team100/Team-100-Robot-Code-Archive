package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot
{
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    private Jaguar ljag1;
    private Jaguar rjag1;
    private Jaguar ljag2;
    private Jaguar rjag2;
    private RobotDrive drive;
    private Joystick leftJoystick = new Joystick(1);
    private Joystick rightJoystick = new Joystick(2);
    private JoystickButton leftButton1 = new JoystickButton(leftJoystick, 1);
    private SendableChooser driveChooser, joystickChooser, robotChooser;
    private Gyro gyro = new Gyro(1);
    private Encoder L_encoder = new Encoder(4, 3, true);
    private Encoder R_encoder = new Encoder(1, 2, true);
    
    private boolean isFin1;
    private boolean isFin2;
    private double L_encoderVal;
    private double R_encoderVal;
    private double encoderVal;
    private double encoderErr;
    private double distOut;
    private double gyroErr;


    public void robotInit()
    {
    /**
     * Sets up sendable chooser so the driver can tell the cRIO which robot
     * it's running and which drive configuration to use.
     */
        driveChooser = new SendableChooser();
        joystickChooser = new SendableChooser();
        robotChooser = new SendableChooser();
        driveChooser.addDefault("Tank Drive", "tank");
        driveChooser.addObject("One Joystick Arcade Drive", "arcade1");
        driveChooser.addObject("Two Joystick Arcade Drive", "arcade2");
        driveChooser.addObject("None", "none");
        joystickChooser.addDefault("Two Joysticks", "two");
        joystickChooser.addObject("One Controller", "one");
        robotChooser.addDefault("G-Wrath", "gwrath");
        robotChooser.addObject("Hammerhead", "hammer");
        robotChooser.addObject("Kraken", "kraken");
        robotChooser.addObject("Kitbot", "kit");
        SmartDashboard.putData("Drive Mode", driveChooser);
        SmartDashboard.putData("Joysticks", joystickChooser);
        SmartDashboard.putData("Robot", robotChooser);
        assignRobot();
        L_encoder.start();
        R_encoder.start();
    }
    
    public void disabledInit()
    {
        //gyro.reset();
        L_encoder.reset();
        R_encoder.reset();
    }
    
    public void disabledPeriodic()
    {
        SmartDashboard.putNumber("Left drive", L_encoder.get()); // 1st feet: 234.0; 2nd feet: 236.0; 3rd feet: 228.0; about 233 per foot
        SmartDashboard.putNumber("Right drive", R_encoder.get()); // 1st feet: 163.0; 2nd feet: 153.0; 3rd feet: 159.0; about about 158 per foot
        SmartDashboard.putNumber("Gyro Value", gyro.getAngle());
        SmartDashboard.putNumber("Gyro Voltage", AnalogModule.getInstance(1).getVoltage(1));
    }
    
    public void autonomousInit()
    {
        gyro.reset();
        L_encoder.reset();
        R_encoder.reset();
        isFin1 = false;
        isFin2 = false;
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        if(!isFin1)
        {
            isFin1 = driveStraight(6);
        }
        if(!isFin2 && isFin1)
        {
            isFin2 = driveStraight(-6);
        }
        if(isFin1 && isFin2)
        {
            drive.arcadeDrive(0, 0);
        }

        SmartDashboard.putNumber("Left drive", L_encoder.get());
        SmartDashboard.putNumber("Right drive", R_encoder.get());
        SmartDashboard.putNumber("Gyro Value", gyro.getAngle());
        SmartDashboard.putNumber("Gyro Voltage", AnalogModule.getInstance(1).getVoltage(1));
        SmartDashboard.putBoolean("isFIn1", isFin1);
        SmartDashboard.putBoolean("isFIn2", isFin2);
    }
    
    
    public void teleopInit()
    {
        gyro.reset();
        L_encoder.reset();
        R_encoder.reset();
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        /**
         * uses sendable chooser to use any control config. the driver wants
         */
        if (driveChooser.getSelected().equals("tank"))
        {
            if (joystickChooser.getSelected().equals("two"))
            {
                drive.tankDrive(-leftJoystick.getY(), -rightJoystick.getY()); // The Joysticks have their Y Axes inverted
            } 
            else
            {
                drive.tankDrive(-leftJoystick.getY(), -leftJoystick.getThrottle()); // The Joysticks have their Y Axes inverted
            }
        }
        if (driveChooser.getSelected().equals("arcade1"))
        {
            if(!leftButton1.get())
            {
                drive.arcadeDrive(-leftJoystick.getY(), -leftJoystick.getX()); // The Joysticks have their Y Axes inverted and Arcade drive uses inverted rotate values
            }
            else
            {
                drive.arcadeDrive(-leftJoystick.getY(), gyro.getAngle() / 22.0); // The Joysticks have their Y Axes inverted and Arcade drive uses inverted rotate values
            }
        }
        if (driveChooser.getSelected().equals("arcade2"))
        {
            if (joystickChooser.getSelected().equals("two"))
            {
                drive.arcadeDrive(-leftJoystick.getY(), -rightJoystick.getX()); // The Joysticks have their Y Axes inverted and Arcade drive uses inverted rotate values
            }
            else
            {
                drive.arcadeDrive(-leftJoystick.getY(), -leftJoystick.getTwist()); // The Joysticks have their Y Axes inverted and Arcade drive uses inverted rotate values
            }
        }
        
        SmartDashboard.putNumber("Left drive", L_encoder.get());
        SmartDashboard.putNumber("Right drive", R_encoder.get());
        SmartDashboard.putNumber("Gyro Value", gyro.getAngle());
        SmartDashboard.putNumber("Gyro Voltage", AnalogModule.getInstance(1).getVoltage(1));
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic()
    {
    
    }
    
    public void assignRobot()
    {
        if (robotChooser.getSelected().equals("gwrath")){
            //ljag1 = new Jaguar(6); motor is unplugged
            ljag2 = new Jaguar(7);
            //rjag1 = new Jaguar(8); motor is unplugged
            rjag2 = new Jaguar(9);
            ljag2.enableDeadbandElimination(true);
            rjag2.enableDeadbandElimination(true);
            drive = new RobotDrive(ljag2, rjag2);
        }
        if (robotChooser.getSelected().equals("hammer")){
            ljag1=new Jaguar(4);
            ljag2=new Jaguar(5);
            rjag1=new Jaguar(2);
            rjag2=new Jaguar(3);
            drive=new RobotDrive(ljag1, ljag2, rjag1, rjag2);
            System.out.println("hammer");
            drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
            drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
            drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
            drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        }
        if (robotChooser.getSelected().equals("kraken")){
            ljag1=new Jaguar(10);
            rjag1=new Jaguar(9);
            drive=new RobotDrive(ljag1, rjag1);
            drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        }
        if (robotChooser.getSelected().equals("kit")){
            ljag1=new Jaguar(2);
            rjag1=new Jaguar(3);
            drive=new RobotDrive(ljag1, rjag1);
            drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        }
    }
    
    /**
     * this method uses a basic proportion control loop to get the robot to
     * drive a certain distance forward
     * @param dist distance is measured in feet
     */
    public boolean driveStraight(double dist)
    {
        L_encoderVal = L_encoder.get() / 239.0; // converts encoder value to feet
        R_encoderVal = R_encoder.get() / 164.0; // converts encoder value to feet
        encoderVal = (L_encoderVal + R_encoderVal) / 2; // averages out encoder values
        encoderErr = dist - encoderVal;
        distOut = encoderErr*0.5 + (Math.abs(dist)/dist)*0.2; // Encoder kP = 0.5; abs(x)/x returns sign of x; 0.2 is the min. magnitude
        gyroErr = gyro.getAngle(); // Setpoint is always 0
        
        SmartDashboard.putNumber("Encoder Value", encoderVal);
        SmartDashboard.putNumber("Error", encoderErr);
        SmartDashboard.putNumber("Speed Output", distOut);
        SmartDashboard.putNumber("Gyro Value", gyroErr);
        
        if(Math.abs(encoderErr) < 0.06 && Math.abs(gyroErr) < 4.5)
        {
            L_encoder.reset();
            R_encoder.reset();
            return true; // returns true when robot gets to its goal
        }
        else
        {
            drive.arcadeDrive(distOut, gyroErr / 20.0); // Gyro kP = 1/20.0;
            return false; // returns false if robot still hasn't reached its goal yet
        }
    }
}