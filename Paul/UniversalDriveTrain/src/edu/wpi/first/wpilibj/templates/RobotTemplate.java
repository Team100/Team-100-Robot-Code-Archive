package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class RobotTemplate extends IterativeRobot {

    private Jaguar ljag1;
    private Jaguar rjag1;
    private Jaguar ljag2;
    private Jaguar rjag2;
    private RobotDrive drive;
    private Joystick leftJoystick = new Joystick(1);
    private Joystick rightJoystick = new Joystick(2);
    private SendableChooser driveChooser, joystickChooser, robotChooser;

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
        robotChooser.addObject("Orangahang", "hang");
        robotChooser.addObject("Kraken", "kraken");
        robotChooser.addDefault("G-Wrath", "gwrath");
        robotChooser.addObject("Hammerhead", "hammer");
        robotChooser.addObject("Kitbot", "kit");
        SmartDashboard.putData("Drive Mode", driveChooser);
        SmartDashboard.putData("Joysticks", joystickChooser);
        SmartDashboard.putData("Robot", robotChooser);
    }

    public void teleopInit() {
        assignRobot();
    }

    public void teleopPeriodic() {
        if (driveChooser.getSelected().equals("tank")) {
            if (joystickChooser.getSelected().equals("two")) {
                drive.tankDrive(leftJoystick.getY(), rightJoystick.getY());
            } else {
                drive.tankDrive(leftJoystick.getY(), leftJoystick.getThrottle());
            }
        }
        if (driveChooser.getSelected().equals("arcade1")) {
            drive.arcadeDrive(leftJoystick.getY(), leftJoystick.getX());
        }
        if (driveChooser.getSelected().equals("arcade2")) {
            if (joystickChooser.getSelected().equals("two")) {
                drive.arcadeDrive(leftJoystick.getY(), rightJoystick.getX());
            } else {
                drive.arcadeDrive(leftJoystick.getY(), leftJoystick.getTwist());
            }
        }
    }

    public void assignRobot() {
        if (robotChooser.getSelected().equals("gwrath")) {
            ljag1 = new Jaguar(6);
            ljag2 = new Jaguar(7);
            rjag1 = new Jaguar(8);
            rjag2 = new Jaguar(9);
            drive = new RobotDrive(ljag1, ljag2, rjag1, rjag2);
            drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
            drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
            drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
            drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        }
        if (robotChooser.getSelected().equals("hammer")) {
            ljag1 = new Jaguar(4);
            ljag2 = new Jaguar(5);
            rjag1 = new Jaguar(2);
            rjag2 = new Jaguar(3);
            drive = new RobotDrive(ljag1, ljag2, rjag1, rjag2);
            drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
            drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
            drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
            drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        }
        if (robotChooser.getSelected().equals("kraken")) {
            ljag1 = new Jaguar(10);
            rjag1 = new Jaguar(9);
            drive = new RobotDrive(ljag1, rjag1);
            drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        }
        if (robotChooser.getSelected().equals("kit")) {
            ljag1 = new Jaguar(2);
            rjag1 = new Jaguar(3);
            drive = new RobotDrive(ljag1, rjag1);
            drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        }
        if (robotChooser.getSelected().equals("hang")) {
            ljag1 = new Jaguar(2);
            rjag1 = new Jaguar(1);
            drive = new RobotDrive(ljag1, rjag1);
            //may need to invert motors
        }
    }
}