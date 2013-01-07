//Subsystem controlling the drivetrain
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.*;
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
    //How far the robot overshoots in autodrivestraight:
    public final double distanceOvershoot=2.5;
    //Constant used to drive straight in autodrivestraight:
    public final double errorConstant=25;
    //How much faster right motors naturally drive than left motors:
    public final double speedOffset=.08;
    //Constant used to turn accurately in autodriveturn:
    public final double angleErrorConstant=20;

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

    public boolean autodrivestraight(double distance){
        //Both right jaguars are flipped, so give them negative values to move forwards
        //Be sure to reset gyro before using
        //the constant in error needs to be tuned
        double speed=.5;
        if ((leftEncoder.getDistance()+rightEncoder.getDistance())/2<distance-distanceOvershoot){
            double error=gyro.getAngle()/errorConstant;
            ljag1.set(speed-error);
            rjag1.set(-speed-error+speedOffset);
            ljag2.set(speed-error);
            rjag2.set(-speed-error+speedOffset);
            putdata();
            return false;
        }
        else {
            return true;
        }
    }

    public boolean autodriveturn (double targetAngle){
        //Both right jaguars are flipped, so give them negative values to move forwards
        //You may want to reset gyro before using
        double speed=.5;
        double angleError=targetAngle-gyro.getAngle();
        double error=angleError/angleErrorConstant;
        if (error>1) {
            error=1;
        }
        if (error<-1){
            error=-1;
        }
        if (error<.5&&error>-.5){
            if (error<0){
                error=-.5;
            }
            else {
                error=.5;
            }
        }
        if (angleError<1&&angleError>-1){
            error=0;
        }
        SmartDashboard.putNumber("Error:", error);
        SmartDashboard.putNumber("Angle Error:", angleError);
        if((gyro.getAngle()<targetAngle-1)||(gyro.getAngle()>targetAngle+1)){
            ljag1.set(speed*error);
            rjag1.set(speed*error);
            ljag2.set(speed*error);
            rjag2.set(speed*error);
            putdata();
            SmartDashboard.putNumber("Speed:", ljag1.get());
            return false;
        }
        else {
            return true;
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
        SmartDashboard.putNumber("Gyro value:", gyro.getAngle());
        SmartDashboard.putNumber("Left encoder value:", leftEncoder.getDistance());
        SmartDashboard.putNumber("Right encoder value:", rightEncoder.getDistance());
    }
}