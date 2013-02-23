/*
 * The climbing subsystem
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;

/**
 *
 * @author Team100
 */
public class Climber extends Subsystem implements SubsystemControl{
    //Robot parts
    private final Encoder encoder = RobotMap.climberEncoder;
    private final SpeedController motorTop = RobotMap.climberTopMotor;
    private final SpeedController motorBottom = RobotMap.climberBottomMotor;
    private final DigitalInput topSwitch = RobotMap.climberTopSwitch;
    private final DigitalInput bottomSwitch = RobotMap.climberBottomSwitch;
    //Constants
    boolean homeUp=false;//automatically home elevator to top or bottom
    double elevatorSpeed=1;//speed that elevator will move
    double homingSpeed=.5;//speed for homingSequence method
    private double homingReverseSpeed=.1;//not very fast
    private double encoderMax=5000;//highest point elevator should reach
    private double encoderMin=0;//lowest point elevator should reach
    private double lowerElevatorPartwayLimit;//how far the robot has to pull itself up the third time
    //other variables
    int level=0;//level of the pyramid that the robot is at    
    private boolean homePartOne=true;//used by homing sequence

    //sets encoder
    public Climber(){
        //FIXME: figure out real direction
        encoder.setReverseDirection(true);
        encoder.reset();
        encoder.start();
        SmartDashboard.putNumber("climberHomingSpeed", homingSpeed);
        SmartDashboard.putNumber("climberHomingReverseSpeed", homingReverseSpeed);
        SmartDashboard.putNumber("climberElevatorSpeed", elevatorSpeed);
        SmartDashboard.putNumber("climberEncoderMax", encoderMax);
        SmartDashboard.putNumber("climberEncoderMin", encoderMin);
        SmartDashboard.putNumber("climberLowerElevatorPartwayLimit", lowerElevatorPartwayLimit);
    }//end constructor

    //empty
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    //sets climber speed to given value, has built-in safeties
    public void manualControl(double speed){
        getDashboardValues();
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
    
    public void getDashboardValues(){
        homingSpeed=SmartDashboard.getNumber("climberHomingSpeed", homingSpeed);
        homingReverseSpeed=SmartDashboard.getNumber("climberHomingReverseSpeed", homingReverseSpeed);
        elevatorSpeed=SmartDashboard.getNumber("climberElevatorSpeed", elevatorSpeed);
        encoderMax=SmartDashboard.getNumber("climberEncoderMax", encoderMax);
        encoderMin=SmartDashboard.getNumber("climberEncoderMin", encoderMin);
        lowerElevatorPartwayLimit=SmartDashboard.getNumber("climberLowerElevatorPartwayLimit", lowerElevatorPartwayLimit);
    }//end getDashboardValues
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //auto climb!
    
    //raises the elevator, tries again if hooks don't catch
    public void raiseElevator(){
        getDashboardValues();
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
        getDashboardValues();
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
        getDashboardValues();
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
        getDashboardValues();
        return !bottomSwitch.get()||encoder.get()<encoderMin;
    }//end getLowerLimit
    
    //whether the elevator has reached the partway limit (for climbing to third level of pyramid)
    public boolean getPartwayLimit(){
        getDashboardValues();
        return encoder.get()<lowerElevatorPartwayLimit;
    }//end getPartwayLimit
    
    //whether the elevator has reached the top
    public boolean getUpperLimit(){
        getDashboardValues();
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
        getDashboardValues();
        if (homeUp){
            if (!topSwitch.get()&&homePartOne){
                motorTop.set(homingSpeed);
                motorBottom.set(homingSpeed);
            }
            if (topSwitch.get()){
                motorTop.set(-homingReverseSpeed);
                motorBottom.set(-homingReverseSpeed);
                homePartOne=false;
            }
            if (!topSwitch.get()&&!homePartOne){
                motorTop.set(0);
                motorBottom.set(0);
                encoder.reset();
            }
        }
        
        else{
            if (!bottomSwitch.get()&&homePartOne){
                motorTop.set(-homingSpeed);
                motorBottom.set(-homingSpeed);
            }
            if (bottomSwitch.get()){
                motorTop.set(homingReverseSpeed);
                motorBottom.set(homingReverseSpeed);
                homePartOne=false;
            }
            if (!bottomSwitch.get()&&!homePartOne){
                motorTop.set(0);
                motorBottom.set(0);
                encoder.reset();
            }
        }
    }//end homingSequence
    
    //displays data on smartdashboard
    public void putData(){
        SmartDashboard.putNumber("Climber Level", level);
        SmartDashboard.putNumber("Climber Encoder", encoder.get()*.0004);
        SmartDashboard.putBoolean("Climber Upper Limit", getUpperLimit());
        SmartDashboard.putBoolean("Climber Lower Limit", getLowerLimit());
    }//end putData

    //returns whether an error occured; not yet implemented
    public boolean getError(boolean goingUp){
        return false;
    }//end getError

    //resets the encoder
    public void resetEncoder() {
        encoder.reset();
    }//end resetEncoder
    
    public void disable(){
        stop();
    }//end disable
    
    public void enable(){

    }//end enable

    public void stop() {
        motorTop.set(0);
        motorBottom.set(0);
    }//end stop

    public void writePreferences() {
    }//end writePreferences
    
}//end Climber
