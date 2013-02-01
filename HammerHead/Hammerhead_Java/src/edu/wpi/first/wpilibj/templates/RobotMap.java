package edu.wpi.first.wpilibj.templates;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
    //Jaguars
    public static final int PWM2_rightDriveA = 2;
    public static final int PWM3_rightDriveB = 3;
    public static final int PWM4_leftDriveA = 4;
    public static final int PWM5_leftDriveB = 5;
    public static final int PWM6_kicker = 6;
    public static final int PWM7_roller = 7;
    
    public static final int PWM9_winch = 9;
    public static final int PWM10_arm = 10;
    
    //Analog Modules
    public static final int AI1_Gyro = 1;
    public static final int AI2_DeploymentPot = 2;
    
    //Digital Inputs
    public static final int DIO5_KickerPhotogate = 5;
    public static final int DIO6_RightEncoderA = 6;
    public static final int DIO7_RightEncoderB = 7;
    public static final int DIO8_LeftEncoderA = 8;
    public static final int DIO9_LeftEncoderB = 9;
    public static final int DIO10_KickerReady = 10;
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
}
