/*
 * The climbing subsystem
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
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
    private final DigitalInput topSwitch = RobotMap.climberTopSwitch;
    private final DigitalInput bottomSwitch = RobotMap.climberBottomSwitch;
    private final DigitalInput poleSwitch = RobotMap.climberPoleSwitch;
    //Constants
    private final double kClimberDistRatio = 1440 / ((18.0/30.0)*(7.5/12.0*3.14159));//encoder ticks*(quadrature)/gearRatio*circumference*conversion to feet
    boolean homeUp=false;//automatically home elevator to top or bottom
    double elevatorSpeed=1;//speed that elevator will move
    double homingSpeed=.5;//speed for homingSequence method
    private double homingReverseSpeed=.1;//not very fast
    private int encoderMax=5000;//highest point elevator should reach
    private int encoderMin=0;//lowest point elevator should reach
    private int lowerElevatorPartwayLimit;//how far the robot has to pull itself up the third time

    int level=0;//level of the pyramid that the robot is at    

    //sets encoder
    public Climber(){
        encoder.setReverseDirection(true);
        encoder.reset();
        encoder.start();
    }//end constructor

    //empty
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    //tells SmartDashboard if the climber has reached the pole
    public void readyClimber(){
        if(poleSwitch.get()){
            SmartDashboard.putString("Ready?", "YES");
        }
    }//end readyClimber
    
    //sets climber speed to given value, has built-in safeties
    public void manualControl(double speed){
        if (!topSwitch.get()&&speed>0||!bottomSwitch.get()&&speed<0){
            motorTop.set(0);
            motorBottom.set(0);
        }
        if (!topSwitch.get()&&speed<0){
            motorTop.set(speed);
            motorBottom.set(speed);
        }
        if (!bottomSwitch.get()&&speed>0){
            motorTop.set(speed);
            motorBottom.set(speed);
        }
        if(topSwitch.get()&&bottomSwitch.get()){
            motorTop.set(speed);
            motorBottom.set(speed);
        }
        putData();
    }//end manualControl
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //auto climb!
    
    //raises the elevator, tries again if hooks don't catch
    public void raiseElevator(){
        if (!getUpperLimit()){
            if (!getError(true)){
                motorTop.set(elevatorSpeed);
                motorBottom.set(elevatorSpeed);
            }
            else {
                if (!getLowerLimit()){
                    motorTop.set(-elevatorSpeed);
                    motorBottom.set(-elevatorSpeed);
                }
            }
        }
        else {
            motorTop.set(0);
            motorBottom.set(0);
        }
        putData();
    }//end raiseElevator
    
    //lowers the elevator, tries again if hooks don't catch
    public void lowerElevator(){
        if (!getLowerLimit()){
            if (!getError(false)){
                motorTop.set(-elevatorSpeed);
                motorBottom.set(-elevatorSpeed);
            }
            else {
                if (!getUpperLimit()){
                    motorTop.set(elevatorSpeed);
                    motorBottom.set(elevatorSpeed);
                }
            }
        }
        else {
            motorTop.set(0);
            motorBottom.set(0);
        }
        putData();
    }//end lowerElevator
    
    //lowers elevator partway for last pull-up at end
    public void lowerElevatorPartway(){
        if (encoder.get()>lowerElevatorPartwayLimit){
            if (!getError(false)){
                motorTop.set(-elevatorSpeed);
                motorBottom.set(-elevatorSpeed);
            }
            else {
                if (!getUpperLimit()){
                    motorTop.set(elevatorSpeed);
                    motorBottom.set(elevatorSpeed);
                }
            }
        }
        else {
            motorTop.set(0);
            motorBottom.set(0);
        }
        putData();
    }//end lowerElevatorPartway
    
    //whether the elevator has reached the bottom
    public boolean getLowerLimit(){
        return !bottomSwitch.get()||encoder.get()<encoderMin;
    }//end getLowerLimit
    
    //whether the elevator has reached the partway limit (for climbing to third level of pyramid)
    public boolean getPartwayLimit(){
        return encoder.get()<lowerElevatorPartwayLimit;
    }//end getPartwayLimit
    
    //whether the elevator has reached the top
    public boolean getUpperLimit(){
        return !topSwitch.get()||encoder.get()>encoderMax;
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
            while (!topSwitch.get()){
                motorTop.set(homingSpeed);
                motorBottom.set(homingSpeed);
            }
            while (topSwitch.get()){
                motorTop.set(-homingReverseSpeed);
                motorBottom.set(-homingReverseSpeed);
            }
            encoder.reset();
        }
        
        else{
            while (!bottomSwitch.get()){
                motorTop.set(-homingSpeed);
                motorBottom.set(-homingSpeed);
            }
            while (bottomSwitch.get()){
                motorTop.set(homingReverseSpeed);
                motorBottom.set(homingReverseSpeed);
            }
            motorTop.set(0);
            motorBottom.set(0);
            encoder.reset();
        }
    }//end homingSequence
    
    //displays data on smartdashboard
    public void putData(){
        SmartDashboard.putNumber("Level", level);
        SmartDashboard.putNumber("Encoder", encoder.get()*.0004);
        SmartDashboard.putBoolean("Upper Limit", getUpperLimit());
        SmartDashboard.putBoolean("Lower Limit", getLowerLimit());
        //System.out.println("putdata");
    }//end putData

    //returns whether an error occured; not yet implemented
    public boolean getError(boolean goingUp){
        return false;
    }//end getError

    //resets the encoder
    public void resetEncoder() {
        encoder.reset();
    }//end resetEncoder
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
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
    }//end disable
    
    public void enable(){
        pidClimber.enable();
    }//end enable

}//end Climber
