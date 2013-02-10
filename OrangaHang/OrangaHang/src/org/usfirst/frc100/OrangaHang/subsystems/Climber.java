/*
 * The climbing subsystem
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.commands.Climb;
import org.usfirst.frc100.OrangaHang.subsystems.PIDBundle.PositionSendablePID;

/**
 *
 * @author Team100
 */
public class Climber extends Subsystem {
    //Robot parts
    private final Encoder encoder = RobotMap.climberEncoder;
    private final Victor motorTop = RobotMap.climberTopMotor;
    private final Victor motorBottom = RobotMap.climberBottomMotor;
    //Constants
    private final double kClimberDistRatio = 1440 / ((18.0/30.0)*(7.5/12.0*3.14159));
    //encoder ticks*(quadrature)/gearRatio*circumference*conversion to feet    
    
    public Climber(){
        motorTop.setExpiration(1.0);
        motorBottom.setExpiration(1.0);
        motorTop.setSafetyEnabled(true);
        motorBottom.setSafetyEnabled(true);
        encoder.setReverseDirection(true);
        encoder.reset();
        encoder.start();
    }//end constructor
    
    Encoder climberEncoder = RobotMap.climberEncoder;
    SpeedController climberTopMotor = RobotMap.climberTopMotor;//why is it called climberTopMotor? Is there a climberBottomMotor?
    DigitalInput climberTopSwitch = RobotMap.climberTopSwitch;
    DigitalInput climberBottomSwitch = RobotMap.climberBottomSwitch;
    DigitalInput climberPoleSwitch = RobotMap.climberPoleSwitch;
    
    int level=0;//level of the pyramid that the robot is at
    
    //preferences
    boolean homeUp=true;//automatically home elevator to top or bottom
    double elevatorSpeed=1;//speed that elevator will move
    double homingSpeed=.2;//speed for homingSequence method
    private double homingReverseSpeed=.1;//not very fast
    private int encoderMax=5000;//highest point elevator should reach
    private int encoderMin=0;//lowest point elevator should reach
    private int lowerElevatorPartwayLimit;//how far the robot has to pull itself up the third time

    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    //empty
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Climb());
    }//end initDefaultCommand
    
    //add in state machine/manual control here!
    
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
    
    //sets climber speed to given value, has built-in safeties
    public void manualControl(double d){
        if (climberTopSwitch.get()&&d>0||climberBottomSwitch.get()&&d<0){
            climberTopMotor.set(0);
        }
        if (climberTopSwitch.get()&&d<0){
            climberTopMotor.set(d);
        }
        if (climberBottomSwitch.get()&&d>0){
            climberTopMotor.set(d);
        }
        if(!climberTopSwitch.get()&&!climberBottomSwitch.get()){
            climberTopMotor.set(d);
        }
        putData();
    }
    
    //raises the elevator, tries again if hooks don't catch
    public void raiseElevator(){
        if (!getUpperLimit()){
            if (!getError(true)){
                climberTopMotor.set(elevatorSpeed);
            }
            else {
                if (!getLowerLimit()){
                    climberTopMotor.set(-elevatorSpeed);
                }
            }
        }
        else {
            climberTopMotor.set(0);
        }
        putData();
    }
    
    //lowers the elevator, tries again if hooks don't catch
    public void lowerElevator(){
        if (!getLowerLimit()){
            if (!getError(false)){
                climberTopMotor.set(-elevatorSpeed);
            }
            else {
                if (!getUpperLimit()){
                    climberTopMotor.set(elevatorSpeed);
                }
            }
        }
        else {
            climberTopMotor.set(0);
        }
        putData();
    }
    
    //lowers elevator partway for last pull-up at end
    public void lowerElevatorPartway(){
        if (climberEncoder.get()>lowerElevatorPartwayLimit){
            if (!getError(false)){
                climberTopMotor.set(-elevatorSpeed);
            }
            else {
                if (!getUpperLimit()){
                    climberTopMotor.set(elevatorSpeed);
                }
            }
        }
        else {
            climberTopMotor.set(0);
        }
        putData();
    }
    
    //whether the elevator has reached the bottom
    public boolean getLowerLimit(){
        return climberBottomSwitch.get()||climberEncoder.get()<encoderMin;
    }
    
    //whether the elevator has reached the top
    public boolean getUpperLimit(){
        return climberTopSwitch.get()||climberEncoder.get()>encoderMax;
    }
    
    //called when robot goes up a level of the pyramid
    public void nextLevel(){
        level+=1;
    }
    
    //which level of the pyramid the robot is on
    public int getLevel(){
        return level;
    }
    
    //resets level to zero
    public void resetLevel(){
        level=0;
    }
    
    //moves elevator to starting position and sets encoder
    public void homingSequence() {
        if (homeUp){
            while (!getUpperLimit()){
                climberTopMotor.set(homingSpeed);
        }
            }
    }; //end anonym class PIDOutput
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
    
}// end Climber
            while (getUpperLimit()){
                climberTopMotor.set(-homingReverseSpeed);
            }
            climberEncoder.reset();
        }
        else{
            while (!getLowerLimit()){
                climberTopMotor.set(-homingSpeed);
            }
            while (getLowerLimit()){
                climberTopMotor.set(homingReverseSpeed);
            }
            climberEncoder.reset();
        }
    }
    
    //displays data on smartdashboard
    public void putData(){
        SmartDashboard.putNumber("Level", level);
        SmartDashboard.putNumber("Encoder", climberEncoder.get());
        SmartDashboard.putBoolean("Upper Limit", getUpperLimit());
        SmartDashboard.putBoolean("Lower Limit", getLowerLimit());
    }

    //returns whether an error occured; not yet implemented
    public boolean getError(boolean goingUp){
        return false;
    }
}//end Climber
