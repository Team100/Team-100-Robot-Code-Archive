/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.templates.commands.Accel_test;
import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 *
 * @author Bryan
 */
public class Accel1 extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    ADXL345_I2C Accele;
    Jaguar mot;
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Accel_test());
         }
    public Accel1() {
        Accele = new ADXL345_I2C (RobotMap.digitalmodule,ADXL345_I2C.DataFormat_Range.k16G);
        mot= new Jaguar (2);
    }
    public void SendToSmartDashboard() {
        SmartDashboard.putNumber ("Accelerometer X-axis", Accele.getAcceleration(ADXL345_I2C.Axes.kX));
        SmartDashboard.putNumber ("Accelerometer Y-axis", Accele.getAcceleration(ADXL345_I2C.Axes.kY));
        SmartDashboard.putNumber ("Accelerometer Z-axis", Accele.getAcceleration(ADXL345_I2C.Axes.kZ));
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "testing");
        mot.set(1.0);
    }
}

