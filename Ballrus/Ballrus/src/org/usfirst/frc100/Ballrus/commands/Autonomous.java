//figure out correct distances
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Chooses from a variety of autonomous modes.
 */
public class Autonomous extends CommandGroup {

    public Autonomous() {
        int mode = (int)DriverStation.getInstance().getAnalogIn(1);
        addSequential(new ResetGyro());
        addSequential(new Pause(0.1));//allows gyro time to reset
        switch (mode) {
            case 0: //shoot
                addSequential(new TriggerShootReload());
                break;
            case 1: //drive and shoot
                addParallel(new TiltToShootHigh());
                addParallel(new ArmShooter());
                addSequential(new AutoDriveStraight(48.0, 5));//drive to close shooting position
                addSequential(new AutoTurn(0.0, true, 2));
                addSequential(new Pause(1.5));//wait for shooter to lower
                addParallel(new TriggerShootReload());
                addSequential(new Pause(0.5));//wait for shot
                addSequential(new AutoDriveStraight(36.0, 5.0));//drive to wall
                break;
            case 2: //shoot and drive
                addParallel(new TiltToShootLow());
                addParallel(new ArmShooter());
                addSequential(new Pause(3.0));//wait for shooter to arm and raise
                addParallel(new TriggerShootReload());
                addSequential(new Pause(1.0));//wait for shot
                addSequential(new AutoDriveStraight(60.0, 6.0));
                break;
            case 3: //2nd ball in front (shoot-drive-intake-driveback-shoot)
                addParallel(new TiltToShootHigh());
                addParallel(new ArmShooter());
                addSequential(new Pause(3.0));//wait for shooter to arm and raise
                addParallel(new TriggerShootReload());
                addSequential(new Pause(1.0));//wait for shot
                addSequential(new DeArmShooter());
                addSequential(new Pause(4.0));//wait for arm to lower
                addParallel(new RunIntakeIn());
                addSequential(new AutoDriveStraight(48.0, 3.0));
                addParallel(new StopIntake());
                addSequential(new AutoTurn(0.0, true, 2.0));
                addParallel(new TiltToShootHigh());
                addSequential(new AutoDriveStraight(-48.0, 3.0));
                addSequential(new Pause(2.0));//wait for arm to raise
                addParallel(new ArmShooter());
                addSequential(new AutoTurn(0.0, true, 2.0));
                addSequential(new Pause(1.0));//wait for shooter to arm
                addSequential(new TriggerShootReload());
                break;
            case 4: //vision (drive-turn-shoot-drive)
                addParallel(new TiltToShootHigh());
                addParallel(new ArmShooter());
                addSequential(new AutoDriveStraight(48.0, 3.0));//drive to close shooting position
                addSequential(new AutoTurn(0.0, true, 0.5));
                addSequential(new CameraAim());//will have terminated at 4.5 seconds
                addSequential(new Pause(2.0));//wait for camera aim to turn
                addSequential(new TriggerShootReload());//shoots at 6.5 seconds
                addSequential(new Pause(1.0));//wait for shot
                addSequential(new AutoTurn(0.0, true, 2.0));
                addSequential(new AutoDriveStraight(36.0, 5.0));//drive to wall
                break;
        }
    }
}