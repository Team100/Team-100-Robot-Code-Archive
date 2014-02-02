
//UpdateWidgets initializes widget tables on first run, 
//sends some information such as time periodically to the widgets through Network Tables

package org.usfirst.frc100.OrangaHang.commands;


import edu.wpi.first.wpilibj.ADXL345_I2C;
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
    
    NetworkTable positionTable;
    NetworkTable artificialHorizonTable;
    Gyro myGyro;
    Encoder leftEncoder;
    Encoder rightEncoder;
    
    public UpdateWidgets() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //Widget Tables
        positionTable = NetworkTable.getTable("PositionData");
        artificialHorizonTable = NetworkTable.getTable("ArtificialHorizon");
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
    
    private void initializePositionTable(NetworkTable table) {
        table.putNumber("Heading", 0.0);
        table.putNumber("EncoderDistance", 0.0);
    }
    
    private void updateArtificialHorizonTable(NetworkTable table){
        //table.putNumber("Pitch", RobotMap.driveAccelerometer.getAcceleration(ADXL345_I2C.Axes.kY));
        //table.putNumber("Roll", RobotMap.driveAccelerometer.getAcceleration(ADXL345_I2C.Axes.kX));
    }
    
    private void updatePositionTable(NetworkTable table) {
        table.putNumber("EncoderDistance", ((-leftEncoder.getRate()) + (rightEncoder.getRate())) / 2);
        table.putNumber("Heading", myGyro.getAngle());
    }
}
