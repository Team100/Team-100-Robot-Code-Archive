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
    private final SpeedController intakeMotor = RobotMap.intakeMotor;
    private final DigitalInput intakeTopSwitch = RobotMap.intakeTopSwitch;//both switches are normally closed!
    private final DigitalInput intakeBottomSwitch = RobotMap.intakeBottomSwitch;
    //Constants
    private final double shootingSpeed = .4;
    private final double intakeSpeed = -.2;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public FrisbeeTransport(){
        SmartDashboard.putNumber("FrisbeeBeltShootingSpeed", shootingSpeed);
        SmartDashboard.putNumber("FrisbeeBeltIntakeSpeed", intakeSpeed);
    }
    
    //empty
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    //call to load frisbees, does NOT run shooter wheels
    public void takeFrisbees(){
        SmartDashboard.putBoolean("IntakeBottomSwitch", intakeBottomSwitch.get());
        SmartDashboard.putBoolean("IntakeTopSwitch", intakeTopSwitch.get());
        if(intakeBottomSwitch.get()){
            intakeMotor.set(SmartDashboard.getNumber("intakeSpeed", intakeSpeed));
        }
        else {
            intakeMotor.set(0);
        }
    }//end takeFrisbees
    
    //slowly moves frisbees into shooter, does NOT run shooter wheels
    public void shootFrisbees(){
        SmartDashboard.putBoolean("IntakeBottomSwitch", intakeBottomSwitch.get());
        SmartDashboard.putBoolean("IntakeTopSwitch", intakeTopSwitch.get());
        if(intakeTopSwitch.get()){
            intakeMotor.set(SmartDashboard.getNumber("shootingSpeed", shootingSpeed));
        }
        else {
            intakeMotor.set(0);
        }
    }//end shootFrisbees
    
    //sets motor to a given value, has safeties
    public void manualControl(double d){
        if (!intakeTopSwitch.get()&&d>0||!intakeBottomSwitch.get()&&d<0){
            intakeMotor.set(0);
        }
        if (!intakeTopSwitch.get()&&d<0){
            intakeMotor.set(d);
        }
        if (!intakeBottomSwitch.get()&&d>0){
            intakeMotor.set(d);
        }
        if(intakeTopSwitch.get()&&intakeBottomSwitch.get()){
            intakeMotor.set(d);
        }
    }//end manualControl

    //stops the motor
    public void stop() {
        intakeMotor.set(0);
    }//end stop

    public void disable() {
    }

    public void enable() {
    }
}//end Intake
