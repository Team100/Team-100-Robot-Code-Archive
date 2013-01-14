package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
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
    private SendableChooser driveChooser, joystickChooser, robotChooser;
    boolean initialized=false;
    
    public void robotInit() {
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
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        if (initialized==false){
            assignRobot();
            initialized=true;
        }
        if (driveChooser.getSelected().equals("tank")){
            if (joystickChooser.getSelected().equals("two")){
                drive.tankDrive(leftJoystick.getY(), rightJoystick.getY());
            } 
            else {
                drive.tankDrive(leftJoystick.getY(), leftJoystick.getThrottle());
            }
        }
        if (driveChooser.getSelected().equals("arcade1")){
            drive.arcadeDrive(leftJoystick.getY(), leftJoystick.getX());
        }
        if (driveChooser.getSelected().equals("arcade2")){
            if (joystickChooser.getSelected().equals("two")){
                drive.arcadeDrive(leftJoystick.getY(), rightJoystick.getX());
            }
            else{
                drive.arcadeDrive(leftJoystick.getY(), leftJoystick.getTwist());
            }
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    public void assignRobot(){
        if (robotChooser.getSelected().equals("gwrath")){
            ljag1=new Jaguar(6);
            ljag2=new Jaguar(7);
            rjag1=new Jaguar(8);
            rjag2=new Jaguar(9);
            drive=new RobotDrive(ljag1, ljag2, rjag1, rjag2);
            drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
            drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
            drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
            drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
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
}