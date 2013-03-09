/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.commands;

import java.util.Vector;

/**
 *
 * @author Sam
 */
public class Reproduce extends CommandBase{
    
    int index;
    int position;
    Vector leftVector;
    Vector rightVector;
    Vector shootbutton;
    Vector primeshootbutton;
    
    public Reproduce(){
        requires(autoMemory);
        requires(driveTrain);
        requires(shooter);
        requires(frisbeeTransport);
    }

    protected void initialize() {
        
        String file;
        file = "file:///autonomous/" + autoMemory.RequestName() + ".sam";
        System.out.println("Reproduce Init :" + file);
        
        autoMemory.read(file);
        leftVector = autoMemory.RequestLeft();
        rightVector = autoMemory.RequestRight();
        shootbutton = autoMemory.RequestShootButton();
        primeshootbutton = autoMemory.RequestPrimeShootButton();
        position = 0;
        this.setInterruptible(true);
        
        if("NoAutonomous".equals(autoMemory.getName())){
            end();
        }
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
        //autoMemory.Reproduce(left.doubleValue(), right.doubleValue(),sb.booleanValue(),psb.booleanValue());
        
        //////////////////////////////////////////////////
        driveTrain.arcadeDrive(left.doubleValue(), right.doubleValue());
        
        if(sb.booleanValue()){
            shooter.shootFrisbees();
        }
        if(psb.booleanValue()){
            frisbeeTransport.shootFrisbees();
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
    }

    protected void interrupted() {
        end();
    }
}