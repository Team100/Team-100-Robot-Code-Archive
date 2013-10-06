package org.usfirst.frc100.Mk3.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.*;
import org.usfirst.frc100.Mk3.RobotMap;
import org.usfirst.frc100.Mk3.commands.ManualTilt;

/**
 * Controls angle of intake and runs intake rollers.
 */
public class Intake extends Subsystem implements SubsystemControl {

    private final Talon frisbeeMotor = RobotMap.intakeFrisbeeMotor;
    public final Talon tiltMotor = RobotMap.intakeTiltMotor;
    //private final AnalogChannel tiltPotentiometer = RobotMap.intakeTiltPotentiometer;
    private final DigitalInput limit = RobotMap.intakeLimit;
    private final double kDefaultIntakeSpeed = .5;
    private final double kDefaultIntakePotentiometerError = 5;
    private final double kDefaultIntakeTiltSpeed = .5;
    private final double kDefaultIntakeUpPosition = 0;
    private final double kDefaultIntakeDownPosition = 200;
    private final double kDefaultIntakeTestPosition = 100;
    public boolean inPosition = true;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new ManualTilt());
    }

    public Intake() {
        Preferences p = Preferences.getInstance();
        if (!p.containsKey("IntakeSpeed")) {
            p.putDouble("IntakeSpeed", kDefaultIntakeSpeed);
        }
        if (!p.containsKey("IntakePotentiometerError")) {
            p.putDouble("IntakePotentiometerError", kDefaultIntakePotentiometerError);
        }
        if (!p.containsKey("IntakeTiltSpeed")) {
            p.putDouble("IntakeTiltSpeed", kDefaultIntakeTiltSpeed);
        }
        if (!p.containsKey("IntakeUpPosition")) {
            p.putDouble("IntakeUpPosition", kDefaultIntakeUpPosition);
        }
        if (!p.containsKey("IntakeDownPosition")) {
            p.putDouble("IntakeDownPosition", kDefaultIntakeDownPosition);
        }
        if (!p.containsKey("IntakeTestPosition")) {
            p.putDouble("IntakeTestPosition", kDefaultIntakeTestPosition);
        }
    }

    public void runIntake() {
        Preferences p = Preferences.getInstance();
        frisbeeMotor.set(p.getDouble("IntakeSpeed", 0.0));
    }

    public void tiltToPosition() {
        inPosition = false;
        Preferences p = Preferences.getInstance();
        //System.out.println(limit.get());
        double tiltSpeed = p.getDouble("IntakeTiltSpeed", 0.0);
        if(!limit.get()){
            tiltMotor.set(tiltSpeed);
        }
        else{
            tiltMotor.set(0);
        }
     }
    
    public void tiltToPosition(double i) {
        inPosition = false;
        Preferences p = Preferences.getInstance();
        //System.out.println(limit.get());
        if(!limit.get()||i<0){//normally false
            tiltMotor.set(i);
        }
        else{
            tiltMotor.set(0);
        }
     }

    public void disable() {
        frisbeeMotor.set(0.0);
        tiltMotor.set(0.0);
    }

    public void manualTilt(double i) {
        tiltMotor.set(i);
    }

    public void enable() {
    }
}