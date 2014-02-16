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

    private Preferences pref;

    private int state;
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
        pref = Preferences.getInstance();
        SmartDashboard.putData("Drive Mode", driveChooser);
        SmartDashboard.putData("Joysticks", joystickChooser);
        SmartDashboard.putData("Robot", robotChooser);
        assignRobot();
        addPref();
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
        state = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        switch(state)
        {
            case(0):
                state++;
                break;
            case(1):
                if(driveStraight(pref.getDouble("AutoDist_0", 0.0))) state++;
                break;
            case(2):
                state++;
                break;
            case(3):
                if(driveStraight(pref.getDouble("AutoDist_1", 0.0))) state++;
                break;
            case(4):
                state++;
                break;
            case(5):
                if(driveStraight(pref.getDouble("AutoDist_2", 0.0))) state++;
                break;
            case(6):
                state++;
                break;
            case(7):
                if(driveStraight(pref.getDouble("AutoDist_3", 0.0))) state++;
                break;
            case(8):
                state++;
                break;
            case(9):
                if(driveStraight(pref.getDouble("AutoDist_4", 0.0))) state++;
                break;
            case(10):
                state++;
                break;
            default:
                drive.arcadeDrive(0, 0);
                break;
        }

        SmartDashboard.putNumber("Left drive", L_encoder.get());
        SmartDashboard.putNumber("Right drive", R_encoder.get());
        SmartDashboard.putNumber("Gyro Value", gyro.getAngle());
        SmartDashboard.putNumber("Gyro Voltage", AnalogModule.getInstance(1).getVoltage(1));
        SmartDashboard.putNumber("isFIn", state);
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
         * uses sendable chooser to use any control configuration the driver wants
         */
        if (driveChooser.getSelected().equals("tank"))
        {
            if (joystickChooser.getSelected().equals("two"))
            {
                drive.tankDrive(-leftJoystick.getY(), -rightJoystick.getY()); // The Joysticks have their Y Axes reversed
            }
            else
            {
                drive.tankDrive(-leftJoystick.getY(), -leftJoystick.getThrottle()); // The Joysticks have their Y Axes reversed
            }
        }
        if (driveChooser.getSelected().equals("arcade1"))
        {
            if(!leftButton1.get())
            {
                drive.arcadeDrive(-leftJoystick.getY(), -leftJoystick.getX()); // The Joysticks have their Y Axes reveresed and Arcade drive uses reversed rotate values
            }
            else
            {
                drive.arcadeDrive(-leftJoystick.getY(), gyro.getAngle() / 22.0); // The Joysticks have their Y Axes reversed and Arcade drive uses reversed rotate values
            }
        }
        if (driveChooser.getSelected().equals("arcade2"))
        {
            if (joystickChooser.getSelected().equals("two"))
            {
                drive.arcadeDrive(-leftJoystick.getY(), -rightJoystick.getX()); // The Joysticks have their Y Axes reversed and Arcade drive uses reversed rotate values
            }
            else
            {
                drive.arcadeDrive(-leftJoystick.getY(), -leftJoystick.getTwist()); // The Joysticks have their Y Axes reversed and Arcade drive uses reversed rotate values
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
     * If the preference doesn't exist it will create it with a default value
     */
    public void addPref()
    {
        if (!pref.containsKey("LeftEncoderRatio"))
        {
            pref.putDouble("LeftEncoderRatio", 233.0);
        }
        if (!pref.containsKey("RightEncoderRatio"))
        {
            pref.putDouble("RightEncoderRatio", 158.0);
        }
        if (!pref.containsKey("Encoder_kP"))
        {
            pref.putDouble("Encoder_kP", 0.5);
        }
        if (!pref.containsKey("OutputMin"))
        {
            pref.putDouble("OutputMin", .2);
        }
        if (!pref.containsKey("DistBuffer"))
        {
            pref.putDouble("DistBuffer", 0.06);
        }

        if (!pref.containsKey("Gyro_kP"))
        {
            pref.putDouble("Gyro_kP", 1/18);
        }
        if (!pref.containsKey("AngleBuffer"))
        {
            pref.putDouble("AngleBuffer", 4.5);
        }

        if (!pref.containsKey("AutoDist_0"))
        {
            pref.putDouble("AutoDist_0", 0.0);
        }
        if (!pref.containsKey("AutDist_1"))
        {
            pref.putDouble("AutoDist_1", 0.0);
        }
        if (!pref.containsKey("AutoDist_2"))
        {
            pref.putDouble("AutoDist_2", 0.0);
        }
        if (!pref.containsKey("AutoDist_3"))
        {
            pref.putDouble("AutoDist_3", 0.0);
        }
        if (!pref.containsKey("AutoDist_4"))
        {
            pref.putDouble("AutoDist_4", 0.0);
        }
    }

    /**
     * this method uses a basic proportion control loop to get the robot to
     * drive a certain distance forward
     * @param dist distance is measured in feet
     */
    public boolean driveStraight(double dist)
    {
        L_encoderVal = L_encoder.get() / pref.getDouble("LeftEncoderRatio", 0.0); // converts encoder value to feet
        R_encoderVal = R_encoder.get() / pref.getDouble("RightEncoderRatio", 0.0); // converts encoder value to feet
        encoderVal = (L_encoderVal + R_encoderVal) / 2; // averages out encoder values
        encoderErr = dist - encoderVal;
        distOut = encoderErr*pref.getDouble("Encoder_kP", 0.0) + (Math.abs(dist)/dist)*pref.getDouble("OutputMin",0.0); // Encoder kP = 0.5; abs(x)/x returns sign of x; 0.2 is the min. magnitude
        gyroErr = gyro.getAngle(); // Setpoint is always 0

        SmartDashboard.putNumber("Encoder Value", encoderVal);
        SmartDashboard.putNumber("Error", encoderErr);
        SmartDashboard.putNumber("Speed Output", distOut);
        SmartDashboard.putNumber("Gyro Value", gyroErr);

        if(Math.abs(encoderErr) < pref.getDouble("DistBuffer", 0.0) && Math.abs(gyroErr) < pref.getDouble("AngleBuffer", 0.0))
        {
            L_encoder.reset();
            R_encoder.reset();
            return true; // returns true when robot gets to its goal
        }
        else
        {
            drive.arcadeDrive(distOut, gyroErr * pref.getDouble("gyro_kP", 0.0)); // Gyro kP = 1/18.0; Arcade Drive uses reversed rotate values (neg. goes Left / pos. goes Right)
            return false; // returns false if robot still hasn't reached its goal yet
        }
    }
}