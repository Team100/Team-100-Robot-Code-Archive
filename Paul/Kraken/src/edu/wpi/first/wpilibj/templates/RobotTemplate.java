package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
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
public class RobotTemplate extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    private Jaguar ljag1=new Jaguar(10);
    private Jaguar rjag1=new Jaguar(9);
    private Jaguar frontShooter = new Jaguar(8);
    private Jaguar backShooter = new Jaguar(7);
    private Jaguar transport = new Jaguar(5);
    private Jaguar intakeA = new Jaguar(3);
    private Jaguar intakeB = new Jaguar(4);
    private Jaguar turret = new Jaguar(2);
    private RobotDrive drive=new RobotDrive(ljag1, rjag1);;
    private Joystick leftJoystick = new Joystick(1);
    private Joystick rightJoystick = new Joystick(2);
    private Joystick manipulator = new Joystick(3);
    private SendableChooser driveChooser, joystickChooser;
    private JoystickButton shootButton1 = new JoystickButton (manipulator, 5);
    private JoystickButton shootButton2 = new JoystickButton (leftJoystick, 1);
    private JoystickButton transportButton1 = new JoystickButton (manipulator, 6);
    private JoystickButton transportButton2 = new JoystickButton (rightJoystick, 1);
    private JoystickButton intakeButton1 = new JoystickButton (manipulator, 1);
    private JoystickButton intakeButton2 = new JoystickButton (rightJoystick, 2);
    private JoystickButton toggleTurretButton = new JoystickButton (leftJoystick, 2);
    
    
    public void robotInit() {
        driveChooser = new SendableChooser();
        joystickChooser = new SendableChooser();
        driveChooser.addDefault("Tank Drive", "tank");
        driveChooser.addObject("One Joystick Arcade Drive", "arcade1");
        driveChooser.addObject("Two Joystick Arcade Drive", "arcade2");
        driveChooser.addObject("None", "none");
        joystickChooser.addDefault("Two Joysticks", "two");
        joystickChooser.addObject("One Controller", "one");
        SmartDashboard.putData("Drive Mode", driveChooser);
        SmartDashboard.putData("Joysticks", joystickChooser);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        
    }
    
    
    public void teleopInit(){
        
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        //Driving
        if (driveChooser.getSelected().equals("tank")){
            if (joystickChooser.getSelected().equals("two")){
                drive.tankDrive(leftJoystick.getY(), rightJoystick.getY());
            } 
            else {
                drive.tankDrive(manipulator.getY(), manipulator.getThrottle());
            }
        }
        if (driveChooser.getSelected().equals("arcade1")){
            if (joystickChooser.getSelected().equals("two")){
                drive.arcadeDrive(leftJoystick.getY(), leftJoystick.getX());
            } 
            else {
                drive.arcadeDrive(manipulator.getY(), manipulator.getX());
            }
        }
        if (driveChooser.getSelected().equals("arcade2")){
            if (joystickChooser.getSelected().equals("two")){
                drive.arcadeDrive(leftJoystick.getY(), rightJoystick.getX());
            }
            else{
                drive.arcadeDrive(manipulator.getY(), manipulator.getTwist());
            }
        }
        
        //Shooting
        if (shootButton1.get()||shootButton2.get()){
            frontShooter.set(-1);
            backShooter.set(1);
        }
        else{
            frontShooter.set(0);
            backShooter.set(0); 
        }
        if (transportButton1.get()||transportButton2.get()){
            transport.set(-1);
        }
        else{
            transport.set(0); 
        }
        
        //Intake
        if (intakeButton1.get()||intakeButton2.get()){
            //intakeA.set(-1);BROKEN
            intakeB.set(-1);
        }
        else{
            intakeA.set(0);
            intakeB.set(0); 
        }
        
        //Turret
        if (toggleTurretButton.get()){
            turret.set(leftJoystick.getX());
        }
        else{
            turret.set(manipulator.getX());
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
}