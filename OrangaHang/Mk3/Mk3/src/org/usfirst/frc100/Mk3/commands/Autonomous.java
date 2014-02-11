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
    boolean fiveDisc=true;
    double autoTime0=3, autoTime1=1.5, autoTime2=3.5, autoTime3=3;
    double kInitialDelay = 2.5;//may use a different value
    double kTimeout = 5;//may use a different value
    
    //Time taken for 5-disc = kTimeout+autoTime0+autoTime3+kTimeOut-(kInitialDelay or autoTime3, whichever is less)
    //Time taken for 7-disc = kTimeout+autoTime0+autoTime1+autoTime2+kTimeOut-(kInitialDelay or autoTime2, whichever is less)
    //Currently, we have 7.5 seconds max for driving, although we should aim for 7
    
    public Autonomous() {
        
        this.addSequential(new LowerIntake());
        this.addParallel(new DriveStraight(-100), .1);//to lower intake
        
        this.addSequential(new TiltUp());
        this.addParallel(new PrimeHighSpeed(), kTimeout);
        this.addSequential(new Shoot(kInitialDelay, kTimeout));
        this.addSequential(new TiltDown());
        
        double setPoint = p.getDouble("AutoDist_0", 0.0);
        this.addSequential(new DriveStraight(setPoint), autoTime0);//may use a different value
        
        this.addParallel(new LoadFrisbeeGroup());
        
        if(fiveDisc){//5 disc
            this.addParallel(new PrimeHighSpeed(), 7+autoTime3);
            
            setPoint+=p.getDouble("AutoDist_3", 0.0);
            this.addSequential(new DriveStraight(setPoint), autoTime3);//may use a different value
            
            this.addSequential(new TiltUp());
            this.addSequential(new Shoot(kInitialDelay-autoTime3, 7));
        }
        else{//7 disc
            setPoint+=p.getDouble("AutoDist_1", 0.0);
            this.addSequential(new DriveStraight(setPoint), autoTime1);//may use a different value
            
            this.addParallel(new LoadFrisbeeGroup());
            
            this.addParallel(new PrimeHighSpeed(), 7+autoTime2);
            
            setPoint+=p.getDouble("AutoDist_2", 0.0);
            this.addSequential(new DriveStraight(setPoint), autoTime2);//may use a different value
            
            this.addSequential(new TiltUp());
            this.addSequential(new Shoot(kInitialDelay-autoTime2, 7));
        }
    }
}