package org.usfirst.frc100.OrangaHang.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.OI;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.subsystems.AutoMemory;
import org.usfirst.frc100.OrangaHang.subsystems.Climber;
import org.usfirst.frc100.OrangaHang.subsystems.DriveTrain;
import org.usfirst.frc100.OrangaHang.subsystems.FixedArms;
import org.usfirst.frc100.OrangaHang.subsystems.FrisbeeTransport;
import org.usfirst.frc100.OrangaHang.subsystems.Pneumatics;
import org.usfirst.frc100.OrangaHang.subsystems.Shifter;
import org.usfirst.frc100.OrangaHang.subsystems.Shooter;
import org.usfirst.frc100.OrangaHang.subsystems.Tower;
//import org.usfirst.frc100.Robot2013.subsystems.ExampleSubsystem;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    public static Climber climber = new Climber();
    public static Shooter shooter = new Shooter();
    public static DriveTrain driveTrain = new DriveTrain();
    public static FrisbeeTransport intake = new FrisbeeTransport();
    public static Pneumatics pneumatics = new Pneumatics();
    public static Tower tower = new Tower();
    public static AutoMemory autoMemory = new AutoMemory();
    public static Shifter shifter = new Shifter();
    public static FixedArms fixedArms = new FixedArms();
    
    // Create a single static instance of all of your subsystems
    //public static ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();

        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(climber);
        SmartDashboard.putData(shooter);
        SmartDashboard.putData(driveTrain);
        SmartDashboard.putData(intake);
        SmartDashboard.putData(pneumatics);
        SmartDashboard.putData(tower);
        SmartDashboard.putData(autoMemory);
        SmartDashboard.putData(shifter);
        SmartDashboard.putData(fixedArms);
        
    }//end init
    
    public static void safeAll(){
        driveTrain.safe();
        RobotMap.safe();
    }//end safeAll
    
    public static void unSafeAll(){
        driveTrain.unSafe();
        RobotMap.unSafe();
    }//end unSafeAll
    
    public static void disableAll(){
        shooter.disable();
        climber.disable();
        driveTrain.disable();
        tower.disable();
        pneumatics.stopCompressor();
    }//end disable
    
    public CommandBase(String name) {
        super(name);
    }//end constructor

    public CommandBase() {
        super();
    }//end constructor
    
}//end CommandBase
