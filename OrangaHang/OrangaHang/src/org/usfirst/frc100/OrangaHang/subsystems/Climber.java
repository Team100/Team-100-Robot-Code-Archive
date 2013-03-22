/*
 * The climbing subsystem
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.commands.CommandBase;
import org.usfirst.frc100.OrangaHang.commands.ManualClimb;

/**
 *
 * @author Team100
 */
public class Climber extends Subsystem implements SubsystemControl{
    //Robot parts
    private final Encoder climberEncoder = RobotMap.climberEncoder;
    private final SpeedController climberTopMotor = RobotMap.climberTopMotor;
    private final SpeedController climberBottomMotor = RobotMap.climberBottomMotor;
    private final DigitalInput climberTopSwitch = RobotMap.climberTopSwitch;
    private final DigitalInput climberBottomSwitch = RobotMap.climberBottomSwitch;
    //Constants
    //TODO: calibrate all constants
    private final double kDefaultElevatorSpeed = 1.0;//speed that elevator will move
    private final double kDefaultHomingSpeed = 0.5;//speed for homingSequence method
    private final double kDefaultHomingReverseSpeed = 0.1;//not very fast
    private final double kDefaultEncoderMax = 5000;//highest point elevator should reach
    private final double kDefaultEncoderMin = 0;//lowest point elevator should reach
    private final double kDefaultLowerElevatorPartwayLimit = 3500;//how far the robot has to pull itself up the third time
    //other variables
    private int level = 0;//level of the pyramid that the robot is at    
    private boolean homeUp = false;//automatically home elevator to top or bottom
    private boolean homePartOne = true;//used by homing sequence
    public boolean doneHoming=false;

    //sets climberEncoder
    public Climber(){
        //FIXME: figure out real direction
        climberEncoder.setReverseDirection(true);
        climberEncoder.reset();
        climberEncoder.start();
        Preferences p = Preferences.getInstance();
        if (!p.containsKey("ClimberElevatorSpeed")) {
            p.putDouble("ClimberElevatorSpeed", kDefaultElevatorSpeed);
        }
        if (!p.containsKey("ClimberEncoderMax")) {
            p.putDouble("ClimberEncoderMax", kDefaultEncoderMax);
        }
        if (!p.containsKey("ClimberEncoderMin")) {
            p.putDouble("ClimberEncoderMin", kDefaultEncoderMin);
        }
        if (!p.containsKey("ClimberLowerElevatorPartwayLimit")) {
            p.putDouble("ClimberLowerElevatorPartwayLimit", kDefaultLowerElevatorPartwayLimit);
        }
        if (!p.containsKey("ClimberHomingSpeed")) {
            p.putDouble("ClimberHomingSpeed", kDefaultHomingSpeed);
        }
        if (!p.containsKey("ClimberHomingReverseSpeed")) {
            p.putDouble("ClimberHomingReverseSpeed", kDefaultHomingReverseSpeed);
        }
    }//end constructor

    //empty
    public void initDefaultCommand() {
        setDefaultCommand(new ManualClimb());
    }//end initDefaultCommand
    
    //sets climber speed to given value, has built-in safeties
    public void manualControl(double speed){
        System.out.println(speed);
//        if (!climberTopSwitch.get()&&speed>0||!climberBottomSwitch.get()&&speed<0){
//            climberTopMotor.set(0);
//            climberBottomMotor.set(0);
//        }
//        if (!climberTopSwitch.get()&&speed<0){
//            climberTopMotor.set(speed);
//            climberBottomMotor.set(-speed);
//        }
//        if (!climberBottomSwitch.get()&&speed>0){
//            climberTopMotor.set(speed);
//            climberBottomMotor.set(-speed);
//        }
//        if(climberTopSwitch.get()&&climberBottomSwitch.get()){
//            climberTopMotor.set(speed);
//            climberBottomMotor.set(-speed);
//        }
        climberTopMotor.set(-speed);
        climberBottomMotor.set(speed);
        putData();
    }//end manualControl
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //auto climb!
    
    //raises the elevator, tries again if hooks don't catch
    public void raiseElevator(){
        Preferences p = Preferences.getInstance();
        final double kElevatorSpeed = p.getDouble("ClimberElevatorSpeed", 0.0);
        if (!getUpperLimit()){
            if (!getError(true)){
                climberTopMotor.set(kElevatorSpeed);
                climberBottomMotor.set(kElevatorSpeed);
            }
            else {
                if (!getLowerLimit()){
                    climberTopMotor.set(-kElevatorSpeed);
                    climberBottomMotor.set(-kElevatorSpeed);
                }
            }
        }
        else {
            climberTopMotor.set(0);
            climberBottomMotor.set(0);
        }
        putData();
    }//end raiseElevator
    
    //lowers the elevator, tries again if hooks don't catch
    public void lowerElevator(){
        Preferences p = Preferences.getInstance();
        final double kElevatorSpeed = p.getDouble("ClimberElevatorSpeed", 0.0);
        if (!getLowerLimit()){
            if (!getError(false)){
                climberTopMotor.set(-kElevatorSpeed);
                climberBottomMotor.set(-kElevatorSpeed);
            }
            else {
                if (!getUpperLimit()){
                    climberTopMotor.set(kElevatorSpeed);
                    climberBottomMotor.set(kElevatorSpeed);
                }
            }
        }
        else {
            climberTopMotor.set(0);
            climberBottomMotor.set(0);
        }
        putData();
    }//end lowerElevator
    
    //lowers elevator partway for last pull-up at end
    public void lowerElevatorPartway(){
        Preferences p = Preferences.getInstance();
        final double kElevatorSpeed = p.getDouble("ClimberElevatorSpeed", 0.0);
        final double kLowerElevatorPartwayLimit = p.getDouble("ClimberLowerElevatorPartwayLimit", 0.0);
        if (climberEncoder.get() > kLowerElevatorPartwayLimit){
            if (!getError(false)){
                climberTopMotor.set(-kElevatorSpeed);
                climberBottomMotor.set(-kElevatorSpeed);
            }
            else {
                if (!getUpperLimit()){
                    climberTopMotor.set(kElevatorSpeed);
                    climberBottomMotor.set(kElevatorSpeed);
                }
            }
        }
        else {
            climberTopMotor.set(0);
            climberBottomMotor.set(0);
        }
        putData();
    }//end lowerElevatorPartway
    
    //whether the elevator has reached the bottom
    public boolean getLowerLimit(){
        Preferences p = Preferences.getInstance();
        final double kEncoderMin = p.getDouble("ClimberEncoderMin", 0.0);
        return !climberBottomSwitch.get()||climberEncoder.get() < kEncoderMin;
    }//end getLowerLimit
    
    //whether the elevator has reached the partway limit (for climbing to third level of pyramid)
    public boolean getPartwayLimit(){
        Preferences p = Preferences.getInstance();
        final double kLowerElevatorPartwayLimit = p.getDouble("ClimberLowerElevatorPartwayLimit", 0.0);
        return climberEncoder.get() < kLowerElevatorPartwayLimit;
    }//end getPartwayLimit
    
    //whether the elevator has reached the top
    public boolean getUpperLimit(){
        Preferences p = Preferences.getInstance();
        final double kEncoderMax = p.getDouble("ClimberEncoderMax", 0.0);
        return !climberTopSwitch.get()||climberEncoder.get() > kEncoderMax;
    }//end getUpperLimit
    
    //called when robot goes up a level of the pyramid
    public void nextLevel(){
        level += 1;
    }//end nextLevel
    
    //which level of the pyramid the robot is on
    public int getLevel(){
        return level;
    }//end getLevel
    
    //resets level to zero
    public void resetLevel(){
        level = 0;
    }//end resetLevel
    
    //moves elevator to starting position and sets climberEncoder
    public void homingSequence() {
        Preferences p = Preferences.getInstance();
        final double kHomingSpeed = p.getDouble("ClimberHomingSpeed", 0.0);
        final double kHomingReverseSpeed = p.getDouble("ClimberHomingReverseSpeed", 0.0);
        if (homeUp) {
            if (!climberTopSwitch.get() && homePartOne) {
                climberTopMotor.set(kHomingSpeed);
                climberBottomMotor.set(kHomingSpeed);
            }
            if (climberTopSwitch.get()) {
                climberTopMotor.set(-kHomingReverseSpeed);
                climberBottomMotor.set(-kHomingReverseSpeed);
                homePartOne = false;
            }
            if (!climberTopSwitch.get() && !homePartOne) {
                climberTopMotor.set(0);
                climberBottomMotor.set(0);
                climberEncoder.reset();
            }
        } else {
            if (!climberBottomSwitch.get() && homePartOne) {
                climberTopMotor.set(-kHomingSpeed);
                climberBottomMotor.set(-kHomingSpeed);
            }
            if (climberBottomSwitch.get()) {
                climberTopMotor.set(kHomingReverseSpeed);
                climberBottomMotor.set(kHomingReverseSpeed);
                homePartOne = false;
            }
            if (!climberBottomSwitch.get() && !homePartOne) {
                climberTopMotor.set(0);
                climberBottomMotor.set(0);
                climberEncoder.reset();
                doneHoming=true;
            }
        }
        putData();
    }//end homingSequence
    
    //displays data on smartdashboard
    //TODO: conform to convention! (no spaces)
    public void putData(){
        Preferences p=Preferences.getInstance();
        double max=p.getDouble("ClimberEncoderMax", kDefaultEncoderMax);
        double min=p.getDouble("ClimberEncoderMin", kDefaultEncoderMin);
        SmartDashboard.putNumber("ClimberLevel", level);
        SmartDashboard.putNumber("ClimberPosition", (climberEncoder.get()-min)/(max-min));
        SmartDashboard.putBoolean("ClimberUpperLimit", getUpperLimit());
        SmartDashboard.putBoolean("ClimberLowerLimit", getLowerLimit());
    }//end putData

    //returns whether an error occured; not yet implemented
    public boolean getError(boolean goingUp){
        return false;
    }//end getError

    //resets the climberEncoder
    public void resetEncoder() {
        climberEncoder.reset();
    }//end resetEncoder
    
    public void disable(){
        climberTopMotor.set(0);
        climberBottomMotor.set(0);
    }//end disable
    
    public void enable(){

    }//end enable

    public void writePreferences() {
    }//end writePreferences
    
}//end Climber
