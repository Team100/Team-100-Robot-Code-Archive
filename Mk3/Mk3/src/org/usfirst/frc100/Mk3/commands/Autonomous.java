package org.usfirst.frc100.Mk3.commands;

import edu.wpi.first.wpilibj.Preferences;

/**
 * @author Team100
 */

public class Autonomous extends CommandBase {

    int state = 0;
    int shotNumber = 0;
    Preferences pref = Preferences.getInstance();
    
    public Autonomous() {
        requires(driveTrain);
        requires(shooter);
        requires(intake);
    }

    protected void initialize() {
        intake.runIntake();
    }

    protected void execute() {
        intake.tiltToPosition(2);
        switch (state) {
            case (0):
                shooter.primeHighSpeed();
                if(shooter.getCurrentCommand().toString().equals("ShooterOff")){
                    if (shotNumber<3){
                        new Shoot(20,180).start();
                        shotNumber++;
                    }
                    else{
                        shotNumber=0;
                        shooter.disable();
                        state++;
                    }
                }
                break;
            case (1):
                if (driveTrain.driveStraight(pref.getDouble("AutoDist_0", 0.0))) {
                    state++;
                }
                break;
            case (2):
                state++;
                break;
            case (3):
                if (driveTrain.driveStraight(pref.getDouble("AutoDist_1", 0.0))) {
                    state++;
                }
                break;
            case (4):
                state++;
                break;
            case (5):
                if (driveTrain.driveStraight(pref.getDouble("AutoDist_2", 0.0))) {
                    state++;
                }
                break;
            case (6):
                shooter.primeHighSpeed();
                if(shooter.getCurrentCommand().toString().equals("ShooterOff")){
                    if (shotNumber<4){
                        new Shoot(20,180).start();
                        shotNumber++;
                    }
                    else{
                        shotNumber=0;
                        shooter.disable();
                        state++;
                    }
                }
                break;
            case (7):
                if (driveTrain.driveStraight(pref.getDouble("AutoDist_3", 0.0))) {
                    state++;
                }
                break;
            case (8):
                state++;
                break;
            case (9):
                if (driveTrain.driveStraight(pref.getDouble("AutoDist_4", 0.0))) {
                    state++;
                }
                break;
            case (10):
                shooter.primeHighSpeed();
                if(shooter.getCurrentCommand().toString().equals("ShooterOff")){
                    if (shotNumber<2){
                        new Shoot(20,180).start();
                        shotNumber++;
                    }
                    else{
                        shotNumber=0;
                        shooter.disable();
                        state++;
                    }
                }
                new Shoot(10,180).start();
                break;
            default:
                driveTrain.arcadeDrive(0, 0);
        }
    }

    protected boolean isFinished() {
        return state>10;
    }

    protected void end() {
        driveTrain.arcadeDrive(0, 0);
        intake.disable();
    }

    protected void interrupted() {
        end();
    }
}//end Autonomous
