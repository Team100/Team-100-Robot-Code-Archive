
//UpdateWidgets initializes widget tables on first run, 
//sends some information such as time periodically to the widgets through Network Tables

package org.usfirst.frc100.OrangaHang.commands;


import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Preferences;
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
    NetworkTable towerTable;
    NetworkTable artificialHorizonTable;
    Gyro myGyro;
    Encoder leftEncoder;
    Encoder rightEncoder;
    Preferences p;
    
    public UpdateWidgets() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //Preferences pointer
        p = Preferences.getInstance();
        //Widget Tables
        backShooterTable = NetworkTable.getTable("PIDSystems/BackShooterPID");
        frontShooterTable = NetworkTable.getTable("PIDSystems/FrontShooterPID");
        positionTable = NetworkTable.getTable("PositionData");
        towerTable = NetworkTable.getTable("SmartDashboard");
//        towerTable = NetworkTable.getTable("PIDSystems/Tower");
        artificialHorizonTable =NetworkTable.getTable("ArtificialHorizon");
        initializePIDTable(backShooterTable, "BackShooter");
        initializePIDTable(frontShooterTable, "FrontShooter");
        initializePositionTable(positionTable);
        //Other Stuff
        myGyro = RobotMap.driveGyro;
        leftEncoder = RobotMap.driveLeftEncoder;
        rightEncoder = RobotMap.driveRightEncoder;
        leftEncoder.start();
        rightEncoder.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        updatePIDWidget(frontShooterTable, "FrontShooter");
        updatePIDWidget(backShooterTable, "BackShooter");
        updatePositionTable(positionTable);
        updateArtificialHorizonTable(artificialHorizonTable);
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
    
    private void initializePIDTable(NetworkTable table, String name) {
        //Setpoint gets loaded by individual PID systems in ___
        table.putNumber(name + "kP", p.getDouble(name + "kP", 0.0));
        table.putNumber(name + "kI", p.getDouble(name + "kI", 0.0));
        table.putNumber(name + "kD", p.getDouble(name + "kD", 0.0));
        table.putNumber(name + "InstVeloc", 0.0);
        table.putNumber(name + "kMaxVeloc", p.getDouble(name + "kMaxVeloc", 0.0));
        table.putNumber(name + "kMaxOutput", p.getDouble(name + "kMaxOutput", 0.0));
        table.putNumber(name + "kMinOutput", p.getDouble(name + "kMinOutput", 0.0));
        table.putNumber(name + "Output", 0.0);
        table.putNumber(name + "Time", 0.0);
        table.putBoolean(name + "Enabled", false);
    }

    private void initializePositionTable(NetworkTable table) {
        table.putNumber("Heading", 0.0);
        table.putNumber("EncoderDistance", 0.0);
    }
    
    private void updateArtificialHorizonTable(NetworkTable table){
        table.putNumber("pitch", RobotMap.driveAccelerometer.getAcceleration(ADXL345_I2C.Axes.kY));
        table.putNumber("roll", RobotMap.driveAccelerometer.getAcceleration(ADXL345_I2C.Axes.kX));
    }
    
    private void updatePIDWidget(NetworkTable table, String name) {
        table.putNumber(name + "Time", System.currentTimeMillis()/1000.0);
        //PID Widget Data is being handled by PID Sendables (Other than Time!)
    }
    
    private void updatePositionTable(NetworkTable table) {
        table.putNumber("EncoderDistance", ((-leftEncoder.getRate()) + (rightEncoder.getRate())) / 2);
        table.putNumber("Heading", myGyro.getAngle());
    }
}
