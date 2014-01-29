//double check that this is what we want to do
package org.usfirst.frc100.Robot2014.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc100.Robot2014.Preferences;

/**
 * Chooses from a variety of autonomous modes.
 */
public class Autonomous extends CommandGroup {

    public Autonomous() {
        switch (Preferences.autoMode) {
            case 1: //drive and shoot
                addParallel(new TiltToShootHigh());
                addParallel(new ArmShooter());
                addParallel(new DeployLowerRoller());
                addSequential(new AutoDriveStraight(24));//drive to close shooting position
                addSequential(new AutoTurn(0, true));
                addSequential(new TriggerShootReload());
                addSequential(new AutoDriveStraight(24));//drive to wall
                break;
            case 2: //shoot and drive
                addParallel(new TiltToShootHigh());
                addParallel(new ArmShooter());
                addParallel(new DeployLowerRoller());
                addSequential(new Pause(1));//wait for shooter to arm
                addParallel(new TriggerShootReload());
                addSequential(new Pause(1));
                addSequential(new AutoDriveStraight(24));
                break;
            case 3: //2nd ball in front (shoot-drive-intake-driveback-shoot)
                addParallel(new TiltToShootHigh());
                addParallel(new ArmShooter());
                addParallel(new DeployLowerRoller());
                addParallel(new TriggerShootReload());
                addSequential(new Pause(4));//wait for arm to lower
                addSequential(new DeArmShooter());
                addParallel(new RunIntakeIn());
                addSequential(new AutoDriveStraight(48));
                addParallel(new StopIntake());
                addSequential(new AutoTurn(0,true));
                addParallel(new TiltToShootHigh());
                addSequential(new AutoDriveStraight(-48));
                addSequential(new Pause(2));//wait for arm to raise
                addParallel(new ArmShooter());
                addSequential(new AutoTurn(0,true));
                addSequential(new Pause(1));//wait for shooter to arm
                addSequential(new TriggerShootReload());
                break;
        }
    }
}