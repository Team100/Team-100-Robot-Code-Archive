package org.usfirst.frc100.Mk3.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;

/**
 * @author Team100
 */

public class Autonomous extends CommandBase {

    int state = 0;
    int shotNumber = 0;
    double backDuration;
    double forwardDuration;
    Preferences pref = Preferences.getInstance();
    private Timer timer = new Timer(), pauseTimer = new Timer();
    int delay = 0;
    
    //requires necessary subsystems
    public Autonomous() {
        requires(driveTrain);
        requires(shooter);
        requires(intake);
        requires(feeder);
    }

    //runs the intake for the duration of the command and sets the timer
    protected void initialize() {
        intake.runIntake();
        timer.reset();
        pauseTimer.reset();
        timer.start();
        shooter.primeHighSpeed();
        pause(20);
    }

    //executes a step in the autonomous sequence based on state
    protected void execute() {
        //intake.tiltToPosition(2);
        if(pauseTimer.get()<delay){
            return;
        }
        delay=0;
        timer.start();
        switch (state) {
            case (0):
                shoot(3);//will automatically increase state when completed and turn off shooter
                break;
            case (1):
                if (driveTrain.driveStraight(pref.getDouble("AutoDist_0", 0.0))) {//code in this block is called once when the drive command ends
                    state++;
                    pause(20);
                }
                break;
            case (2):
                if (driveTrain.driveStraight(pref.getDouble("AutoDist_1", 0.0))) {
                    state++;
                    pause(20);
                    shooter.primeHighSpeed();
                    timer.reset();
                }
                break;
            case (3):
                shoot(4);
                break;
            case (4):
                if (driveTrain.driveStraight(pref.getDouble("AutoDist_2", 0.0))) {
                    state++;
                    pause(20);
                }
                break;
            case (5):
                if (driveTrain.driveStraight(pref.getDouble("AutoDist_3", 0.0))) {
                    state++;
                    pause(20);
                    shooter.primeHighSpeed();
                    timer.reset();
                }
                break;
            case (6):
                shoot(2);
                break;
            default:
                driveTrain.disable();
                intake.disable();
                shooter.disable();
        }
    }

    //determines if past the last state
    protected boolean isFinished() {
        return state>6;
    }

    //disables subsytems
    protected void end() {
        driveTrain.disable();
        intake.disable();
        shooter.disable();
    }

    //calls end
    protected void interrupted() {
        end();
    }
    
    //fires an amount of shots, then increases state
    public void shoot(int times) {
        backDuration = feeder.getShootBackDuration();
        forwardDuration = feeder.getShootForwardDuration();
        shooter.primeHighSpeed();
        if (timer.get() <= backDuration) {
            feeder.pullBack();
        } else if (timer.get() <= forwardDuration + backDuration) {
            feeder.pushForward();
        } else {
            timer.reset();
            shotNumber++;
        }
        if (shotNumber == times) {
            state++;
            shooter.disable();
        }
    }
    
    //makes the command wait an amount of time and pauses the timer
    public void pause(int time){
        pauseTimer.reset();
        pauseTimer.start();
        delay=time;
        timer.stop();
    }
}//end Autonomous
