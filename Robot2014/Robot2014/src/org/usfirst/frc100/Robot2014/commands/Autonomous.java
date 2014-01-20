//double check that this is what we want to do
package org.usfirst.frc100.Robot2014.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomously drive forward and shoot into the 10-point goal.
 */
public class Autonomous extends CommandGroup {

    public Autonomous() {
        addParallel(new TiltToShootHigh());
        addParallel(new ArmShooter());
        addParallel(new DeployLowerRoller());
        addSequential(new AutoDriveStraight(24));
        addSequential(new AutoTurn(0, true));
        addSequential(new TriggerShootReload());
    }
}
