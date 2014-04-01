//fix 2-ball mode
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc100.Ballrus.Preferences;

/**
 * Chooses from a variety of autonomous modes.
 * Mode key:
 * 0: None
 * 1: Drive and shoot
 * 2: Shoot and drive
 * 3: 2-ball
 * 4: Turning vision
 * 5: Waiting vision
 * 6: Test light ring
 * 7: Test vision
 * 8: Drive only
 * 9: Dead reckoning drive only
 * 10: Shoot only
 * 11: Dead reckoning drive and shoot
 */
public class Autonomous extends CommandGroup {

    public Autonomous() {
        int mode = (int)DriverStation.getInstance().getAnalogIn(1)+(int)DriverStation.getInstance().getAnalogIn(2)+(int)DriverStation.getInstance().getAnalogIn(3);
        addSequential(new ResetGyro());
        addSequential(new Pause(0.1));//allows gyro time to reset
        System.out.println("Mode: "+mode);
        if((!Preferences.cameraEnabled)&&(mode == 4 || mode == 5 || mode == 7)){ //if camera is off, don't use camera modes
            mode = 1;
            System.out.println("Mode changed to 1 due to lack of camera!");
        }
        switch (mode) {
            case 0: //none
                break;
            case 1: //drive and shoot
                addParallel(new TiltToShootHigh());
                addParallel(new ArmShooter());
                addSequential(new AutoDriveStraight(120.0, 2.5));//drive to close shooting position
                addSequential(new AutoTurn(0.0, true, 1.5));
                addSequential(new TriggerShootReload());
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
                addSequential(new Pause(2.0));//wait for shooter to arm and raise
                addParallel(new TriggerShootReload());
                addSequential(new Pause(1.0));//wait for shot
                addParallel(new DeArmShooter());
                addSequential(new Pause(2.0));//wait for arm to lower
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
                addSequential(new AutoDriveStraight(96.0, 2.5));//drive to close shooting position
                addSequential(new AutoTurn(-Preferences.cameraAngle, true, 1));
                addSequential(new CameraAim());//will have terminated at 4.5 seconds
                addSequential(new Pause(2.0));//wait for camera aim to turn
                addParallel(new TriggerShootReload());//shoots at 6.5 seconds
                addSequential(new Pause(1.0));//wait for shot
                addSequential(new AutoTurn(0.0, true, 2.0));
                break;
            case 5: //vision (drive-delay-shoot)
                double lowerCamera = 2.0, takePic = 0.1, raiseShooter = 2.0;
                addParallel(new TiltToCameraAim());//0 sec
                addParallel(new CameraDelay(lowerCamera, lowerCamera+takePic+raiseShooter, 6.0));
                addSequential(new Pause(lowerCamera+takePic));//time for camera to tilt down and take a picture
                addParallel(new ArmShooter());
                addParallel(new TiltToShootHigh());
                addSequential(new AutoDriveStraight(120.0, 7));//drive to close shooting position
                break;
            case 6: //light ring test
                addSequential(new ActivateLightRing());
                break;
            case 7: //camera only, no motion
                addSequential(new CameraTest(3,6));
                break;
            case 8: //drive only
                addSequential(new AutoDriveStraight(120.0, 2.5));
                break;
            case 9: //dead reckoning drive only
                addSequential(new DeadReckoningDrive(0.5));
                break;
            case 10: //shoot only
                addParallel(new TiltToShootHigh());
                addParallel(new ArmShooter());
                addSequential(new Pause(1.5));
                addSequential(new TriggerShootReload());
                break;
            case 11: //dead reckoning drive and shoot
                addParallel(new TiltToShootHigh());
                addSequential(new DeadReckoningDriveStraight(-.5, 1.2));//drive to close shooting position
                addSequential(new AutoTurn(0.0, true, 1.5));
                addSequential(new TriggerShootReload());
                break;
        }
    }
}