package org.usfirst.frc100.FrisBeast.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Vector;
import org.usfirst.frc100.FrisBeast.OI;
import org.usfirst.frc100.FrisBeast.RobotMap;
import org.usfirst.frc100.FrisBeast.subsystems.AutoMemory;
import org.usfirst.frc100.FrisBeast.subsystems.DriveTrain;
import org.usfirst.frc100.FrisBeast.subsystems.Hanger;
import org.usfirst.frc100.FrisBeast.subsystems.Feeder;
import org.usfirst.frc100.FrisBeast.subsystems.Pneumatics;
import org.usfirst.frc100.FrisBeast.subsystems.Shifter;
import org.usfirst.frc100.FrisBeast.subsystems.Shooter;
import org.usfirst.frc100.FrisBeast.subsystems.Tilter;
import org.usfirst.frc100.FrisBeast.subsystems.SubsystemControl;
//import org.usfirst.frc100.Robot2013.subsystems.ExampleSubsystem;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    public static Hanger hanger = new Hanger();
    public static Shooter shooter = new Shooter();
    public static DriveTrain driveTrain = new DriveTrain();
    public static Feeder feeder = new Feeder();
    public static Pneumatics pneumatics = new Pneumatics();
    public static AutoMemory autoMemory = new AutoMemory();
    public static Shifter shifter = new Shifter();
    public static Tilter tilter = new Tilter();
    public static Vector subsystems = new Vector();
    
    // Create a single static instance of all of your subsystems
    //public static ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();

        subsystems.addElement(hanger);
        subsystems.addElement(shooter);
        subsystems.addElement(driveTrain);
        subsystems.addElement(feeder);
        subsystems.addElement(pneumatics);
        subsystems.addElement(tilter);
        subsystems.addElement(autoMemory);
        subsystems.addElement(shifter);
        
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
        return(Preferences.getInstance().getBoolean("DebugMode", false));
    }//end isDebugMode
    
    public CommandBase() {
        super();
        Preferences p =Preferences.getInstance();
        if(!p.containsKey("DebugMode")){
            p.putBoolean("DebugMode", false);
        }
    }//end constructor

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run. Call end() by default.
    protected void interrupted() {
        end();
    }
}//end CommandBase
