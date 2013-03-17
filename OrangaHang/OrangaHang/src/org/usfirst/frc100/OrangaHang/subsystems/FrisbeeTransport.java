package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.commands.FrisbeeTransportOff;

/**
 *
 * @author Paul
 */
public class FrisbeeTransport extends Subsystem implements SubsystemControl {
    //Robot parts
    private final SpeedController frisbeeTransportMotor = RobotMap.frisbeeTransportMotor;
    private final Counter frisbeeTransportTopSwitch = RobotMap.frisbeeTransportTopSwitch;//both switches are normally closed!
    private final Counter frisbeeTransportBottomSwitch = RobotMap.frisbeeTransportBottomSwitch;
    private final Timer timer = new Timer();
    //Constants
    private final double kDefaultShootingSpeed = 0.9;
    private final double kDefaultIntakeSpeed = -0.15;
    
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
        frisbeeTransportTopSwitch.reset();
        frisbeeTransportBottomSwitch.reset();
        frisbeeTransportTopSwitch.start();
        frisbeeTransportBottomSwitch.start();
    }//end constructor
    
    //empty
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand (new FrisbeeTransportOff());
    }//end initDefaultCommand
    
    //call to load frisbees, does NOT run shooter wheels
    public void takeFrisbees(){
        //Hall-effect switch returns true when NOT hitting limit. 
        //Non-zero counter implies we hit the switch.
      if(frisbeeTransportBottomSwitch.get() == 0){
            Preferences p = Preferences.getInstance();
            frisbeeTransportMotor.set(p.getDouble("FrisbeeBeltIntakeSpeed", kDefaultIntakeSpeed));
        }
        else {
            frisbeeTransportMotor.set(0.0);
        }
    }//end takeFrisbees
    
    //slowly moves frisbees into shooter, does NOT run shooter wheels
    public void shootFrisbees(){
        //Hall-effect switch returns true when NOT hitting limit.
        //Non-zero counter implies we hit the switch.
        if(frisbeeTransportTopSwitch.get() == 0){
            Preferences p = Preferences.getInstance();
            frisbeeTransportMotor.set(p.getDouble("FrisbeeBeltShootingSpeed", kDefaultShootingSpeed));
        }
        else {
            frisbeeTransportMotor.set(0.0); 
        }
    }//end shootFrisbees

    public void resetTop(){
        frisbeeTransportTopSwitch.reset();
    }
    
    public void resetBottom(){
        frisbeeTransportBottomSwitch.reset();
    }
    
    //stops the motor
    public void disable() {
        frisbeeTransportMotor.set(0.0);
    }//end disable

    public void enable() {
    }//end enable

    public void writePreferences() {
    }//end writePreferences
    
}//end Intake
