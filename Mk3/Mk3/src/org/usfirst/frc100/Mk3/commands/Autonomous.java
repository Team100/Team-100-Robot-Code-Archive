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
        double kInitialDelay = 1;//may use a different value
        double kTimeout = 3;//may use a different value
        
        this.addSequential(new LowerIntake());
        
        this.addParallel(new PrimeHighSpeed(), kTimeout);
        this.addSequential(new Shoot(kInitialDelay, kTimeout));
        
        double setPoint = p.getDouble("AutoDist_0", 0.0);
        this.addSequential(new DriveStraight(setPoint), 2);//may use a different value
        
        this.addSequential(new RaiseIntake());
        this.addSequential(new UseIntake());
        this.addSequential(new LowerIntake());
        
        setPoint+=p.getDouble("AutoDist_1", 0.0);
        this.addSequential(new DriveStraight(setPoint), 2);//may use a different value
        
        this.addSequential(new RaiseIntake());
        this.addSequential(new UseIntake());
        this.addSequential(new LowerIntake());
        
        setPoint+=p.getDouble("AutoDist_2", 0.0);
        this.addSequential(new DriveStraight(setPoint), 3.5);//may use a different value
        
        this.addParallel(new PrimeHighSpeed(), 5);
        this.addSequential(new Shoot(kInitialDelay, 5));
        
        //raise intake command
    }
}