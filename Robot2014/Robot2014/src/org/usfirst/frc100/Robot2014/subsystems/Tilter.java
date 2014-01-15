package org.usfirst.frc100.Robot2014.subsystems;

import org.usfirst.frc100.Robot2014.RobotMap;
import org.usfirst.frc100.Robot2014.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls the angle of the arm.
 */
public class Tilter extends Subsystem {

    SpeedController motor = RobotMap.tilterMotor;
    AnalogChannel potentiometer = RobotMap.tilterPotentiometer;

    // Sets the default command to TiltToShootHigh
    public void initDefaultCommand() {
        setDefaultCommand(new TiltToShootHigh());
    }
}
