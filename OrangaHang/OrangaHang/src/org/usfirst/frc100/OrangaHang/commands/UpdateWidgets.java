
//UpdateWidgets initializes widget tables on first run, 
//sends some information such as time periodically to the widgets through Network Tables

package org.usfirst.frc100.OrangaHang.commands;


import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc100.OrangaHang.RobotMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel
 */
public class UpdateWidgets extends CommandBase {
    
    NetworkTable frontShooterTable;
    NetworkTable backShooterTable;
    NetworkTable positionTable;
    Gyro myGyro;
    Encoder leftEncoder;
    Encoder rightEncoder;
    DigitalInput frontHall;
    DigitalInput backHall;
    Counter frontCounter;
    Counter backCounter;
    
    public UpdateWidgets() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        backShooterTable = NetworkTable.getTable("BackShooterPID");
        initializePIDTable(backShooterTable);
        frontShooterTable = NetworkTable.getTable("FrontShooterPID");
        initializePIDTable(frontShooterTable);
        positionTable = NetworkTable.getTable("PositionData");
        initializePositionTable(positionTable);
        myGyro = RobotMap.driveGyro;
        leftEncoder = RobotMap.driveLeftEncoder;
        rightEncoder = RobotMap.driveRightEncoder;
        frontHall = RobotMap.shooterFrontHallEffect;
        backHall = RobotMap.shooterBackHallEffect;
        frontCounter = new Counter(frontHall);
        backCounter = new Counter(backHall);
        frontCounter.start();
        backCounter.start();
        leftEncoder.start();
        rightEncoder.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        frontShooterTable.putNumber("time", System.currentTimeMillis());
        frontShooterTable.putNumber("velocity", frontCounter.getPeriod());
        backShooterTable.putNumber("time", System.currentTimeMillis());
        backShooterTable.putNumber("velocity", backCounter.getPeriod());
        updatePositionTable(positionTable);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    private void initializePIDTable(NetworkTable table) {
        table.putNumber("p", 0.0);
        table.putNumber("i", 0.0);
        table.putNumber("d", 0.0);
        table.putNumber("setpoint", 0.0);
        table.putNumber("velocity", 0.0);
        table.putNumber("maxOutput", 0.0);
        table.putNumber("minOutput", 0.0);
        table.putNumber("time", 0.0);
    }

    private void initializePositionTable(NetworkTable table) {
        table.putNumber("Heading", 0.0);
        table.putNumber("EncoderDistance", 0.0);
    }
    
    private void updatePositionTable(NetworkTable table) {
        table.putNumber("EncoderDistance", (-leftEncoder.getRate() + rightEncoder.getRate()) / 2);
        table.putNumber("Heading", myGyro.getAngle());
    }
}
