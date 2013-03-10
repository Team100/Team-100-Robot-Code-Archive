package org.usfirst.frc100.OrangaHang.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Vector;
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
import org.usfirst.frc100.OrangaHang.subsystems.SubsystemControl;
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
    public static FrisbeeTransport frisbeeTransport = new FrisbeeTransport();
    public static Pneumatics pneumatics = new Pneumatics();
    public static Tower tower = new Tower();
    public static AutoMemory autoMemory = new AutoMemory();
    public static Shifter shifter = new Shifter();
    public static FixedArms fixedArms = new FixedArms();
    public static Vector subsystems = new Vector();
    public static SendableChooser modeChooser = new SendableChooser();
    
    // Create a single static instance of all of your subsystems
    //public static ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();

        subsystems.addElement(climber);
        subsystems.addElement(shooter);
        subsystems.addElement(driveTrain);
        subsystems.addElement(frisbeeTransport);
        subsystems.addElement(pneumatics);
        subsystems.addElement(tower);
        subsystems.addElement(autoMemory);
        subsystems.addElement(shifter);
        subsystems.addElement(fixedArms);
        
        // Show what command your subsystem is running on the SmartDashboard
        for (int i=0; i<subsystems.size(); i++){
            SmartDashboard.putData((Subsystem)subsystems.elementAt(i));
        }

    }//end init
    
    public static void safeAll(){
        RobotMap.safe();
    }//end safeAll
    
    public static void unSafeAll(){
        RobotMap.unSafe();
    }//end unSafeAll
    
    public static void disableAll(){
        for (int i=0; i<subsystems.size(); i++){
            ((SubsystemControl)subsystems.elementAt(i)).disable();
        }
    }//end disable
    
    public static boolean isDebugMode(){
        //return(modeChooser.getSelected()=="d");
        return true;
    }
    
    public CommandBase(String name) {
        super(name);
        modeChooser.addDefault("Competition Mode", "c");
        modeChooser.addObject("Debug Mode", "d");
        //SmartDashboard.putData("ModeChooser", modeChooser);
    }//end constructor

    public CommandBase() {
        super();
        modeChooser.addDefault("Competition Mode", "c");
        modeChooser.addObject("Debug Mode", "d");
        SmartDashboard.putData("ModeChooser", modeChooser);
    }//end constructor

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run. Call end() by default.
    protected void interrupted() {
        end();
    }
}//end CommandBase
