package edu.wpi.first.wpilibj.templates;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    
    public static final int kRightMotor1 = 9;
    //public static final int kRightMotor2 = 1;
    public static final int kLeftMotor1 = 10;
    //public static final int kLeftMotor2 = 2;
    public static final int kBeltMotor = 8;
    
    public static final int kRightEncoder1 = 3;
    public static final int kRightEncoder2 = 4;
    public static final int kLeftEncoder1 = 5;
    public static final int kLeftEncoder2 = 6;
    
    public static final int kFrisbeeHook = 3; // on arduino
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
}
