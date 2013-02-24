package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Preferences;
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
    private final double kDefaultShootingSpeed = 0.4;
    private final double kDefaultIntakeSpeed = -0.2;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public FrisbeeTransport(){
        Preferences p = Preferences.getInstance();
        if (!p.containsKey("FrisbeeBeltIntakeSpeed")) {
            p.putDouble("FrisbeeBeltIntakeSpeed", kDefaultIntakeSpeed);
        }
        if (!p.containsKey("FrisbeeBeltShootingSpeed")) {
            p.putDouble("FrisbeeBeltShootingSpeed", kDefaultShootingSpeed);
        }
    }//end constructor
    
    //empty
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    //call to load frisbees, does NOT run shooter wheels
    public void takeFrisbees(){
        //returns true when NOT hitting limit
        if(frisbeeTransportBottomSwitch.get()){
            Preferences p = Preferences.getInstance();
            frisbeeTransportMotor.set(p.getDouble("FrisbeeBeltIntakeSpeed", kDefaultIntakeSpeed));
        }
        else {
            frisbeeTransportMotor.set(0.0);
        }
    }//end takeFrisbees
    
    //slowly moves frisbees into shooter, does NOT run shooter wheels
    public void shootFrisbees(){
        //returns true when NOT hitting limit
        if(frisbeeTransportTopSwitch.get()){
            Preferences p = Preferences.getInstance();
            frisbeeTransportMotor.set(p.getDouble("FrisbeeBeltShootingSpeed", kDefaultShootingSpeed));
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
