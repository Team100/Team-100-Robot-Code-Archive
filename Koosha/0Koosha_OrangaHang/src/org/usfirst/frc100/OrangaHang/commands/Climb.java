/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Team100
 */
public class Climb extends CommandGroup {
    boolean firstTime=true;
    public Climb() {
        //if elevator starts at bottom add another RaiseElevator here
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
        
    }
    public void execute(){
        //do not move this to another method
        if (firstTime){
            CommandBase.climber.resetLevel();
        }
        firstTime=false;
    }//end execute
    
}//end Climb CommandGroup
