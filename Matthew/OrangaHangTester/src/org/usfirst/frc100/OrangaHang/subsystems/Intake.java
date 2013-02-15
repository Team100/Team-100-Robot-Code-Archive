package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.OrangaHang.RobotMap;

/**
 *
 * @author Team100
 */
public class Intake extends Subsystem {
    //Robot parts
    private final SpeedController motor = RobotMap.intakeMotor;
    private final DigitalInput topSwitch = RobotMap.intakeTopSwitch;
    private final DigitalInput bottomSwitch = RobotMap.intakeBottomSwitch;
    //Constants
    private final double shootingSpeed = 0.5;
    private final double intakeSpeed = -0.2;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand

    public void shootFrisbees() {
        if (topSwitch.get()) {
            motor.set(shootingSpeed);
        }
    }//end shootFrisbees

    public void takeFrisbees() {
        if (!bottomSwitch.get()) {
            motor.set(intakeSpeed);
        } else {
            motor.set(0.0);
        }
    }//end takeFrisbees
    
}//end Intake
