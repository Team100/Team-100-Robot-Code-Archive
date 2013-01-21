// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.
package org.usfirst.frc100.Robot2013.subsystems;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.Robot2013.RobotMap;
/**
 *
 */
public class Climber extends Subsystem {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    Encoder climberEncoder = RobotMap.climberClimberEncoder;
    SpeedController climberMotor = RobotMap.climberClimberMotor;
    DigitalInput topClimberSwitch = RobotMap.climberTopClimberSwitch;
    DigitalInput bottomClimberSwitch = RobotMap.climberBottomClimberSwitch;
    SpeedController climberTilter = RobotMap.climberClimberTilter;
    AnalogChannel climberAnglePot = RobotMap.climberClimberAnglePot;
    Encoder climberAngleEncoder = RobotMap.climberClimberAngleEncoder;
    Compressor compressor = RobotMap.climberCompressor;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void raiseElevator(){
        if (!getUpperLimit()){
            climberMotor.set(1);
        }
        else {
            climberMotor.set(0);
        }
    }
    
    public void lowerElevator(){
        if (!getLowerLimit()){
            climberMotor.set(-1);
        }
        else {
            climberMotor.set(0);
        }
    }
    
    public void tiltElevatorForward(){
        if (!getTiltForwardLimit()){
            climberTilter.set(.2);
        }
        else {
            climberTilter.set(0);
        }
    }
    
    public void tiltElevatorBack(){
        if (!getTiltBackLimit()){
            climberTilter.set(-.2);
        }
        else {
            climberTilter.set(0);
        }
    }
    
    public boolean getLowerLimit(){
        return bottomClimberSwitch.get();
    }
    
    public boolean getUpperLimit(){
        return topClimberSwitch.get();
    }
    public boolean getTiltForwardLimit() {
        //Change this number!
        return (climberAnglePot.getValue()>60);
    }
    
    public boolean getTiltBackLimit() {
        //Change this number!
        return (climberAnglePot.getValue()<30);
    }
}
