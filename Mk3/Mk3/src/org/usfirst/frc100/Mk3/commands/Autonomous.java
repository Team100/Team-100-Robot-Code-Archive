package org.usfirst.frc100.Mk3.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;

/**
 * This program will be called during Autonomous. It will drive forward, with
 * the intake running so as to pick up discs, then it will drive back to get
 * into scoring position before actually shooting. This command is no longer
 * used now that we have taken off the intake. It has been replaced by code
 * written directly into the autonomousInit method.
 */
public class Autonomous extends CommandBase {

    int state = 0;
    int shotNumber = 0;
    double backDuration;
    double forwardDuration;
    Preferences p = Preferences.getInstance();
    private Timer timer = new Timer();

    //Requires necessary subsystems
    public Autonomous() {
        requires(driveTrain);
        requires(shooter);
        requires(intake);
        requires(feeder);
    }

    //Runs the intake for the duration of the command and sets the timer
    protected void initialize() {
        driveTrain.enable();
        shotNumber=0;
        state=0;
        timer.reset();
        timer.start();
        shooter.primeHighSpeed();
        intake.tiltToPosition(.5);//warning: will not stop!!!
        pause(3); //Will wait 1 sec. for the shooter to get up to speed
        timer.reset();
    }

    //Executes a step in the autonomous sequence based on state
    protected void execute() {
        //In hindsight we should have made this sequence a commandGroup
        System.out.println(state);
        switch (state) {
            case (0):
                intake.tiltToPosition(.5);
                shoot(3); //Will automatically increase state and turn off the shooter when completed
                break;
            case (1):
                intake.tiltToPosition(.5);
                if (driveTrain.driveStraight(p.getDouble("AutoDist_0", 0.0))) { //The if statement returns true once the driveStraight method finishes
                    System.out.println("Distance reached");
                    state++;
                    //pause(0.020); //Will wait 20 miliseconds for the robot to fully stop after driving
                    timer.reset();
                }
                break;
            case (2):
                intake.tiltToPosition(.5);
                if(driveTrain.alignStraight()&&intake.tiltMotor.get()==0){
                    state++;
                }
                break;
            case (3):
                intake.tiltToPosition(.5);
                intake.runIntake();
                if (driveTrain.driveStraight(p.getDouble("AutoDist_1", 0.0))) {
                    state++;
                    shooter.primeHighSpeed();
                    pause(1);
                    timer.reset();
                }
                break;
            case (4):
                intake.tiltToPosition(.5);
                if(driveTrain.alignStraight()){
                    state++;
                }
                break;
            case (5):
                intake.disable();
                shoot(4);
                break;
//            case (6):
//                intake.runIntake();
//                if (driveTrain.driveStraight(p.getDouble("AutoDist_2", 0.0))) {
//                    state++;
//                    pause(0.020);
//                    timer.reset();
//                }
//                break;
//            case (5):
//                intake.disable();
//                intake.manualTilt(-.2);
//                if (driveTrain.driveStraight(p.getDouble("AutoDist_3", 0.0))) {
//                    state++;
//                    shooter.primeHighSpeed();
//                    pause(0.020);
//                    timer.reset();
//                }
//                break;
//            case (6):
//                shoot(2);
//                break;
            default:
                driveTrain.disable();
                intake.disable();
                shooter.disable();
        }
    }

    //determines if past the last state
    protected boolean isFinished() {
        return state > 6;
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
        System.out.println("Shoot");
        backDuration = feeder.getShootBackDuration();
        forwardDuration = feeder.getShootForwardDuration();
        System.out.println(timer.get());
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
            shotNumber=0;
        }
    }

    //makes the command wait an amount of time and pauses the timer
    public void pause(double time) {
        timer.delay(time);
    }
}//end Autonomous
