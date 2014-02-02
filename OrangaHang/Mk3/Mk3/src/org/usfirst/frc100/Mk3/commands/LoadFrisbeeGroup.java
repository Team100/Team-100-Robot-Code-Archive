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
public class LoadFrisbeeGroup extends CommandGroup {
    
    public LoadFrisbeeGroup() {
        this.addSequential(new RaiseIntake());
        this.addSequential(new UseIntake());
        this.addParallel(new LowerIntake());
    }
}
