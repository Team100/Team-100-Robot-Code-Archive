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
    private final Talon tiltMotor = RobotMap.intakeTiltMotor;
    private final AnalogChannel tiltPotentiometer = RobotMap.intakeTiltPotentiometer;
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

    public void tiltToPosition(int endPosition) {
        inPosition = false;
        Preferences p = Preferences.getInstance();
        double tiltSpeed = p.getDouble("IntakeTiltSpeed", 0.0);
        double error = p.getDouble("IntakePotentiometerError", 0.0);
        int start = tiltPotentiometer.getValue();
        int end = start;
        switch (endPosition) {
            case 1:
                end = (int) p.getDouble("IntakeUpPosition", 0.0);
                break;
            case 2:
                end = (int) p.getDouble("IntakeDownPosition", 0.0);
                break;
            case 3:
                end = (int) p.getDouble("IntakeTestPosition", 0.0);
                break;
        }
        if (end <= start + error && end >= start - error) {
            tiltMotor.set(0.0);
            inPosition = true;
        }
        if (end > start + error) {
            tiltMotor.set(tiltSpeed);
        }
        if (end < start - error) {
            tiltMotor.set(-tiltSpeed);
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