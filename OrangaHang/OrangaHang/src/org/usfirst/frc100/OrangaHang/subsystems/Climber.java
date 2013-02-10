/*
 * The climbing subsystem
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.commands.ClimbAuto;
import org.usfirst.frc100.OrangaHang.subsystems.PIDBundle.PositionSendablePID;

/**
 *
 * @author Team100
 */
public class Climber extends Subsystem {
    //Robot parts
    private final Encoder encoder = RobotMap.climberEncoder;
    private final SpeedController motorTop = RobotMap.climberTopMotor;
    private final SpeedController motorBottom = RobotMap.climberBottomMotor;
    private final DigitalInput climberTopSwitch = RobotMap.climberTopSwitch;
    private final DigitalInput climberBottomSwitch = RobotMap.climberBottomSwitch;
    private final DigitalInput climberPoleSwitch = RobotMap.climberPoleSwitch;
    //Constants
    private final double kClimberDistRatio = 1440 / ((18.0/30.0)*(7.5/12.0*3.14159));//encoder ticks*(quadrature)/gearRatio*circumference*conversion to feet
    boolean homeUp=true;//automatically home elevator to top or bottom
    double elevatorSpeed=1;//speed that elevator will move
    double homingSpeed=.2;//speed for homingSequence method
    private double homingReverseSpeed=.1;//not very fast
    private int encoderMax=5000;//highest point elevator should reach
    private int encoderMin=0;//lowest point elevator should reach
    private int lowerElevatorPartwayLimit;//how far the robot has to pull itself up the third time
    int level=0;//level of the pyramid that the robot is at    

    public Climber(){
        encoder.setReverseDirection(true);
        encoder.reset();
        encoder.start();
    }//end constructor

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new ClimbAuto());
    }//end initDefaultCommand
    
    //sets climber speed to given value, has built-in safeties
    public void manualControl(double speed){
        if (climberTopSwitch.get()&&speed>0||climberBottomSwitch.get()&&speed<0){
            motorTop.set(0);
            motorBottom.set(0);
        }
        if (climberTopSwitch.get()&&speed<0){
            motorTop.set(speed);
        }
        if (climberBottomSwitch.get()&&speed>0){
            motorTop.set(speed);
        }
        if(!climberTopSwitch.get()&&!climberBottomSwitch.get()){
            motorTop.set(speed);
        }
        putData();
    }//end manualControl
    
    //raises the elevator, tries again if hooks don't catch
    public void raiseElevator(){
        if (!getUpperLimit()){
            if (!getError(true)){
                motorTop.set(elevatorSpeed);
            }
            else {
                if (!getLowerLimit()){
                    motorTop.set(-elevatorSpeed);
                }
            }
        }
        else {
            motorTop.set(0);
        }
        putData();
    }//end raiseElevator
    
    //lowers the elevator, tries again if hooks don't catch
    public void lowerElevator(){
        if (!getLowerLimit()){
            if (!getError(false)){
                motorTop.set(-elevatorSpeed);
            }
            else {
                if (!getUpperLimit()){
                    motorTop.set(elevatorSpeed);
                }
            }
        }
        else {
            motorTop.set(0);
        }
        putData();
    }//end lowerElevator
    
    //lowers elevator partway for last pull-up at end
    public void lowerElevatorPartway(){
        if (encoder.get()>lowerElevatorPartwayLimit){
            if (!getError(false)){
                motorTop.set(-elevatorSpeed);
            }
            else {
                if (!getUpperLimit()){
                    motorTop.set(elevatorSpeed);
                }
            }
        }
        else {
            motorTop.set(0);
        }
        putData();
    }//end lowerElevaotrPartway
    
    //whether the elevator has reached the bottom
    public boolean getLowerLimit(){
        return climberBottomSwitch.get()||encoder.get()<encoderMin;
    }//end getLowerLimt
    
    //whether the elevator has reached the top
    public boolean getUpperLimit(){
        return climberTopSwitch.get()||encoder.get()>encoderMax;
    }//end getUpperLimit
    
    //called when robot goes up a level of the pyramid
    public void nextLevel(){
        level+=1;
    }//end nextLevel
    
    //which level of the pyramid the robot is on
    public int getLevel(){
        return level;
    }//end getLevel
    
    //resets level to zero
    public void resetLevel(){
        level=0;
    }//end resetLevel
    
    //moves elevator to starting position and sets encoder
    public void homingSequence() {
        if (homeUp){
            while (!getUpperLimit()){
                motorTop.set(homingSpeed);
            }
            while (getUpperLimit()){
                motorTop.set(-homingReverseSpeed);
            }
            encoder.reset();
        }
        
        else{
            while (!getLowerLimit()){
                motorTop.set(-homingSpeed);
            }
            while (getLowerLimit()){
                motorTop.set(homingReverseSpeed);
            }
            encoder.reset();
        }
    }//end homingSequence
    
    //displays data on smartdashboard
    public void putData(){
        SmartDashboard.putNumber("Level", level);
        SmartDashboard.putNumber("Encoder", encoder.get());
        SmartDashboard.putBoolean("Upper Limit", getUpperLimit());
        SmartDashboard.putBoolean("Lower Limit", getLowerLimit());
    }//end putData

    //returns whether an error occured; not yet implemented
    public boolean getError(boolean goingUp){
        return false;
    }//end getError
    
    
    //PID control
    PIDSource sourceClimber = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("encoderClimber_raw", encoder.getRaw());
            return encoder.getRaw();
        }
    }; //end anonym class PIDSource
    PIDOutput outputClimber = new PIDOutput(){
        public void pidWrite(double output){
            motorTop.set(output);
            motorBottom.set(output);
        }
    };//end anonym class PIDOutput
    private PositionSendablePID pidClimber = new PositionSendablePID("Climber",sourceClimber, outputClimber, kClimberDistRatio);   
   
    public void setSetpoint(double setpoint){
        pidClimber.setSetpoint(setpoint);
    }//end setSetpoint
    
    public void disable(){
        pidClimber.disable();
        encoder.setReverseDirection(true);
        encoder.reset();
    }//end disable
    
    public void enable(){
        encoder.setReverseDirection(true);
        encoder.reset();
        encoder.start();
        pidClimber.enable();
    }//end enable

}//end Climber
