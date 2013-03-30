/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.FrisBeast.commands;

import edu.wpi.first.wpilibj.Timer;
import java.util.Vector;

/**
 *
 * @author Sam
 */
public class Reproduce extends CommandBase{
    //FIXME: "BROKEN" when transferred to new project; check command calls 
    //against new commands AND make sure nothing is commented out that needs
    //to be in the code.
    int index;
    int position;
    Vector leftVector;
    Vector rightVector;
    Vector shootbutton;
    Vector primeshootbutton;
    Vector timestamp;
    
    Timer timer = new Timer();
    
    public Reproduce(){
        requires(autoMemory);
        requires(driveTrain);
        requires(shooter);
        requires(feeder);
    }

    protected void initialize() {
        timer.reset();
        timer.start();
        
        String file;
        file = "file:///autonomous/" + autoMemory.RequestName() + ".sam";
        System.out.println("Reproduce Init :" + file);
        
        autoMemory.read(file);
        leftVector = autoMemory.RequestLeft();
        rightVector = autoMemory.RequestRight();
        shootbutton = autoMemory.RequestShootButton();
        primeshootbutton = autoMemory.RequestPrimeShootButton();
        timestamp = autoMemory.RequestTimeStamp();
        position = 0;
        this.setInterruptible(true);
    }

    /**
     *
     * Is called every tick
     * This must be adapted to the actual robot.
     */
    protected void execute() {
        try{
        if(position > leftVector.size()-2){
            this.cancel();
        }
        Double left = (Double) leftVector.elementAt(position);
        Double right = (Double) rightVector.elementAt(position);
        Boolean sb = (Boolean) shootbutton.elementAt(position);
        Boolean psb = (Boolean) primeshootbutton.elementAt(position);
        Double time = (Double) timestamp.elementAt(position);
        
        //////////////////////////////////////////////////
        driveTrain.arcadeDrive(left.doubleValue(), right.doubleValue());
        
//        if(psb.booleanValue()){
//            shooter.shootFrisbees();
//        }
//        if(sb.booleanValue()){
//            frisbeeTransport.shootFrisbees();
//        }
        
        if(time.doubleValue() < timer.get()){
                try {
                    Thread.sleep((long)(timer.get()-time.doubleValue()));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
        }
        
        ////////////////////////////////////////////////////////////////////////
    
        position++;
        
        }catch(ArrayIndexOutOfBoundsException ex){
            end();
        }
    }

    protected boolean isFinished() {
        return false;
    }
    
    /**
     * Sets all autoMemory.Reproduce inputs to zero and false
     * @see autoMemory.Reproduce
     */
    protected void end() {
        //autoMemory.Reproduce(0,0,false,false);
        position = 0;
        timer.stop();
    }

    protected void interrupted() {
        end();
    }
}