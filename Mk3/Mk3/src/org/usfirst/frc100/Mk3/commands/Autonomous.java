/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.Mk3.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Student
 */
public class Autonomous extends CommandGroup {
    
    Preferences p = Preferences.getInstance();
    
    public Autonomous() {
        double kInitialDelay = 2.5;//may use a different value
        double kTimeout = 5;//may use a different value
        
        this.addSequential(new LowerIntake());
        this.addParallel(new DriveStraight(-100), .1);
        
        this.addSequential(new TiltUp());
        this.addParallel(new PrimeHighSpeed(), kTimeout);
        this.addSequential(new Shoot(kInitialDelay, kTimeout));
        this.addSequential(new TiltDown());
        
        double setPoint = p.getDouble("AutoDist_0", 0.0);
        this.addSequential(new DriveStraight(setPoint), 2);//may use a different value
        
        this.addParallel(new LoadFrisbeeGroup());
//        this.addSequential(new RaiseIntake());
//        this.addSequential(new UseIntake());
//        this.addParallel(new LowerIntake());
        
        if(true){//5 disc
            setPoint+=p.getDouble("AutoDist_3", 0.0);
            this.addSequential(new DriveStraight(setPoint), 3);//may use a different value
            this.addParallel(new PrimeHighSpeed(), 7);
            this.addSequential(new TiltUp());
            this.addSequential(new Shoot(kInitialDelay, 5));
        }
        else{//7 disc
            setPoint+=p.getDouble("AutoDist_1", 0.0);
            this.addSequential(new DriveStraight(setPoint), 1);//may use a different value
            
            this.addSequential(new RaiseIntake());
            this.addSequential(new UseIntake());
            this.addSequential(new LowerIntake());
            
            setPoint+=p.getDouble("AutoDist_2", 0.0);
            this.addSequential(new DriveStraight(setPoint), 1.5);//may use a different value
            this.addParallel(new PrimeHighSpeed(), 7);
            this.addSequential(new TiltUp());
            this.addSequential(new Shoot(kInitialDelay, 5));
        }

        
        //raise intake command
    }
}