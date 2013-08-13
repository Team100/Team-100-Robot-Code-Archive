package org.usfirst.frc100.Mk3.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.Mk3.RobotMap;

/**
 *
 * @author Team100
 */
public class Pneumatics extends Subsystem implements SubsystemControl {
    Compressor compressor = RobotMap.compressor;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    public void startCompressor(){
        compressor.start();
    }//end startCompressor
    
    public void stopCompressor(){
        compressor.stop();
    }//end stopCompressor

    public void disable() {
        stopCompressor();
    }//end disable

    public void enable() {
        compressor.start();
    }//end enable

}//end Pneumatics
