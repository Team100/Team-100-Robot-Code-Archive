//Subsystem controlling the drivetrain
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.templates.RobotMap;

public class DriveTrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private final Jaguar ljag1 = new Jaguar(RobotMap.pwm_leftdriveA);
    private final Jaguar rjag1 = new Jaguar(RobotMap.pwm_rightdriveA);
    private final RobotDrive drive = new RobotDrive(ljag1, rjag1);
    private final Jaguar ljag2 = new Jaguar(RobotMap.pwm_leftdriveB);
    private final Jaguar rjag2 = new Jaguar(RobotMap.pwm_rightdriveB);
    private final RobotDrive drive2 = new RobotDrive(ljag2, rjag2);
    private final Gyro gyro = new Gyro(RobotMap.ai_gyro);
    private final Encoder rightEncoder = new Encoder(RobotMap.dio_rightencoderA, RobotMap.dio_rightencoderB);
    private final Encoder leftEncoder = new Encoder(RobotMap.dio_leftencoderA, RobotMap.dio_leftencoderB);
    public SendableChooser numOfJoysticks;

    public DriveTrain(){
        numOfJoysticks = new SendableChooser();
        numOfJoysticks.addDefault("1 Logitech Controller", "one");
        numOfJoysticks.addObject("2 Joysticks", "two");
        SmartDashboard.putData("Number of joysticks", numOfJoysticks);
        //Distance is given in inches
        leftEncoder.setDistancePerPulse(.053676);
        rightEncoder.setDistancePerPulse(.053676);
        //Encoders are reversed
        leftEncoder.setReverseDirection(true);
        rightEncoder.setReverseDirection(true);
        leftEncoder.start();
        rightEncoder.start();
    }

    public void initDefaultCommand() {
    }

    public void tankdrive(Joystick left, Joystick right){
        //Tankdrive needs negative values for both joysticks to work correctly
        if (numOfJoysticks.getSelected().equals("two")){
            drive.tankDrive(left.getY()*-1, right.getY()*-1);
            drive2.tankDrive(left.getY()*-1, right.getY()*-1);
        }
        else {
            drive.tankDrive(left.getY()*-1, left.getThrottle()*-1);
            drive2.tankDrive(left.getY()*-1, left.getThrottle()*-1);
        }
    }

    public void arcadedrive1(Joystick joystick){
        drive.arcadeDrive(joystick.getY()*-1, joystick.getX()*-1);
        drive2.arcadeDrive(joystick.getY()*-1, joystick.getX()*-1);
    }

    public void arcadedrive2(Joystick joystick, Joystick joystick2){
        //Left joystick controls speed, right joystick turns
        if (numOfJoysticks.getSelected().equals("one")){
            drive.arcadeDrive(joystick.getY()*-1, joystick.getTwist()*-1);
            drive2.arcadeDrive(joystick.getY()*-1, joystick.getTwist()*-1);
        }
        else {
            drive.arcadeDrive(joystick.getY()*-1, joystick2.getX()*-1);
            drive2.arcadeDrive(joystick.getY()*-1, joystick2.getX()*-1);
        }
    }

    public void autodrivestraight(double speed, double distance){
        //Both right jaguars are flipped, so give them negative values to move forwards
        //Be sure to reset gyro before using
        //2.5 is about the amount it overshoots
        while ((leftEncoder.getDistance()+rightEncoder.getDistance())/2<distance-2.5){
            if (gyro.getAngle() > .1) {
                ljag1.set(speed-.1);
                rjag1.set(-speed);
                ljag2.set(speed-.1);
                rjag2.set(-speed);
            }
            if (gyro.getAngle()<=.1&&gyro.getAngle()>=-.1){
                ljag1.set(speed);
                rjag1.set(-speed);
                ljag2.set(speed);
                rjag2.set(-speed);
            }
            if (gyro.getAngle()<-.1){
                ljag1.set(speed);
                rjag1.set(-(speed-.1));
                ljag2.set(speed);
                rjag2.set(-(speed-.1));
            }
            //Autonomous command doesn't call putData, so call it here
            putdata();
        }
    }

    public void autodriveturn (double targetAngle){
        //Both right jaguars are flipped, so give them negative values to move forwards
        //You may want to reset gyro before using
        double speed=.4;
        while((gyro.getAngle()<targetAngle-5)||(gyro.getAngle()>targetAngle+5)){
            if (gyro.getAngle()>targetAngle+5){
                ljag1.set(-speed);
                rjag1.set(-speed);
                ljag2.set(-speed);
                rjag2.set(-speed);
            }
            if (gyro.getAngle()<targetAngle-5){
                ljag1.set(speed);
                rjag1.set(speed);
                ljag2.set(speed);
                rjag2.set(speed);
            }
            //Autonomous command doesn't call putData, so call it here
            putdata();
        }
    }

    public void resetgyro (){
        gyro.reset();
    }

    public void resetencoders (){
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public void resetsensors (){
        leftEncoder.reset();
        rightEncoder.reset();
        gyro.reset();
    }
    
    public double getgyro (){
        return gyro.getAngle();
    }

    public void stop(){
        drive.stopMotor();
        drive2.stopMotor();
    }

    public void putdata(){
        //Puts all data to smartdashboard
        SmartDashboard.putDouble("Gyro value:", gyro.getAngle());
        SmartDashboard.putDouble("Left encoder value:", leftEncoder.getDistance());
        SmartDashboard.putDouble("Right encoder value:", rightEncoder.getDistance());
    }
}