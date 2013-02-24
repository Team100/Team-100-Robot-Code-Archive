
//Climb creates and starts the command group that will autonomously traverse the pyramid.
//It stops when we get to the top.

package org.usfirst.frc100.OrangaHang.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Paul
 */
public class Climb extends CommandGroup {
    //adds commands to scheduler
    public Climb() {
        
        addSequential(new RaiseElevator());
        addSequential(new LowerElevator());
        addSequential(new RaiseElevator());
        addSequential(new LowerElevator());
        addSequential(new RaiseElevator());
        addSequential(new LowerElevator());
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        
    }//end constructor
    
    //prepares climber subsystem
    protected void initialize() {
        CommandBase.climber.resetLevel();
        //CommandBase.climber.enable();
    }//end initialize

    //stops climber
    protected void end() {
        CommandBase.climber.disable();
    }//end end
    
    //calls end
    protected void interrupted() {
        end();
    }//end interrupted

}//end Climb CommandGroup
