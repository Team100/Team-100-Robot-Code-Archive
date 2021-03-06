//ready
package org.usfirst.frc100.Robot2014.subsystems;

import org.usfirst.frc100.Robot2014.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Creates and starts the compressor.
 */
public class Compressor extends Subsystem {

    edu.wpi.first.wpilibj.Compressor compressor = RobotMap.compressor;

    public void startCompressor() {
        compressor.start();
    }

    public void stopCompressor() {
        compressor.stop();
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void initDefaultCommand() {
    }
}
