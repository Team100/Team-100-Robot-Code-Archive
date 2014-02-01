//ready
package org.usfirst.frc100.Robot2014.subsystems;

import org.usfirst.frc100.Robot2014.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Creates and starts the compressor.
 */
public class Compressor extends Subsystem {

    edu.wpi.first.wpilibj.Compressor compressor = RobotMap.compressor;

    // No default command
    public void initDefaultCommand() {
    }

    // Starts the compressor (using sensor to auto stop when full)
    public void startCompressor() {
        compressor.start();
    }

    // Stops the compressor
    public void stopCompressor() {
        compressor.stop();
    }
}
