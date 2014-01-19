
//Based off of Paul's Universal Drive Train
//Edited for testing the MaxBotix MB1023 Ultrasonic Sensor on Hammerhead...
//...and seeing the data on the new (or old) SmartDashboard.




package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class RobotTemplate extends IterativeRobot {
    double lastTime;
    private Jaguar ljag1;
    private Jaguar rjag1;
    private Jaguar ljag2;
    private Jaguar rjag2;
    private AnalogChannel ultra1;
    private AnalogChannel ultra2BAD;
    private AnalogChannel ultra3good;
    private RobotDrive drive;
    private Joystick leftJoystick = new Joystick(1);
    private Joystick rightJoystick = new Joystick(2);
    private SendableChooser driveChooser, joystickChooser, robotChooser;
    Timer t = new Timer();

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
        t.start();
        lastTime = ultra1.getVoltage()/5*512/2.54;
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
        double range = ultra1.getVoltage()/5*512/2.54;
        if(Math.abs(range-lastTime)<20){
            System.out.println("Inches: " + ultra1.getVoltage()/5*512/2.54);
            SmartDashboard.putNumber("Inches: ", range);
        SmartDashboard.putNumber("Period: ", t.get());
        t.reset();
        SmartDashboard.putNumber("Voltage Graph: ", ultra1.getVoltage());
        SmartDashboard.putNumber("Voltage: ", ultra1.getVoltage());
            lastTime=range;
      }
//        SmartDashboard.putNumber("Inches: ", ultra1.getVoltage()/5*512/2.54);
//        SmartDashboard.putNumber("Inches Graph: ", ultra1.getVoltage()/5*512/2.54);
//         SmartDashboard.putNumber("Inches Graph2: ", ultra3good.getVoltage()/5*512/2.54);
//        SmartDashboard.putNumber("Period: ", t.get());
//        t.reset();
//        SmartDashboard.putNumber("Voltage Graph: ", ultra1.getVoltage());
//        SmartDashboard.putNumber("Voltage: ", ultra1.getVoltage());
    }

    public void assignRobot() {
        if(ljag1!=null){
            return;
        }
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
            ultra3good = new AnalogChannel(4);
            ultra1 = new AnalogChannel(6);
            ultra2BAD = new AnalogChannel(3);
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