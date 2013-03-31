/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.FrisBeast.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.FrisBeast.RobotMap;

/**
 *
 * @author Team100
 */
public class Pneumatics extends Subsystem implements SubsystemControl {
    Compressor compressor = RobotMap.compressor;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void startCompressor(){
        compressor.start();
    }//end startCompressor
    
    public void stopCompressor(){
        compressor.stop();
    }//end stopCompressor

    public void disable() {
        stopCompressor();
    }

    public void enable() {
        compressor.start();
    }

}//end Pneumatics
