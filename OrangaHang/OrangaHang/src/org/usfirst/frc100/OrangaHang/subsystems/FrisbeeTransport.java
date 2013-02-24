package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;

/**
 *
 * @author Paul
 */
public class FrisbeeTransport extends Subsystem implements SubsystemControl {
    //Robot parts
    private final SpeedController frisbeeTransportMotor = RobotMap.frisbeeTransportMotor;
    private final DigitalInput frisbeeTransportTopSwitch = RobotMap.frisbeeTransportTopSwitch;//both switches are normally closed!
    private final DigitalInput frisbeeTransportBottomSwitch = RobotMap.frisbeeTransportBottomSwitch;
    //Constants
    private final double kShootingSpeed = .4;
    private final double kIntakeSpeed = -.2;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public FrisbeeTransport(){
        SmartDashboard.putNumber("FrisbeeBeltShootingSpeed", kShootingSpeed);
        SmartDashboard.putNumber("FrisbeeBeltIntakeSpeed", kIntakeSpeed);
    }
    
    //empty
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    //call to load frisbees, does NOT run shooter wheels
    public void takeFrisbees(){
        //returns true when NOT hitting limit
        if(frisbeeTransportBottomSwitch.get()){
            frisbeeTransportMotor.set(SmartDashboard.getNumber("FrisbeeBeltIntakeSpeed", kIntakeSpeed));
        }
        else {
            frisbeeTransportMotor.set(0.0);
        }
    }//end takeFrisbees
    
    //slowly moves frisbees into shooter, does NOT run shooter wheels
    public void shootFrisbees(){
        //returns true when NOT hitting limit
        if(frisbeeTransportTopSwitch.get()){
            frisbeeTransportMotor.set(SmartDashboard.getNumber("FrisbeeBeltShootingSpeed", kShootingSpeed));
        }
        else {
            frisbeeTransportMotor.set(0.0);
        }
    }//end shootFrisbees

    //stops the motor
    public void disable() {
        frisbeeTransportMotor.set(0.0);
    }//end disable

    public void enable() {
    }//end enable

    public void writePreferences() {
    }//end writePreferences
}//end Intake
