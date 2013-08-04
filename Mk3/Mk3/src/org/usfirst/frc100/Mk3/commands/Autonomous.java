/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.Mk3.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Team100
 */
public class Autonomous extends CommandGroup {
    
    public Autonomous(double initialDelay, double timeout) {
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
        //addParallel(new PrimeHighSpeed(), timeout);//second parameter is timeout. Stops shooter wheels so we don't kill the battery
        //addSequential(new Shoot());
    }//end constructor
    
}//end Autonomous
