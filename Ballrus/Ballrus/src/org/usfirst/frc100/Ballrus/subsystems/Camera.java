//vision processing is not yet implemented
package org.usfirst.frc100.Ballrus.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import org.usfirst.frc100.Ballrus.RobotMap;

/**
 * Controls the camera and its light ring.
 */
public class Camera extends Subsystem {

    private AxisCamera camera;
    private final Relay cameraLights = RobotMap.cameraLights;
    private ColorImage image;
    private boolean leftTargetHot = true;

    // Tests if there is a camera
    public void Camera(){
        if(AxisCamera.getInstance()!=null){
            camera = AxisCamera.getInstance();
        } else {
            System.out.println("No axis camera!");
        }
    }
    
    // No default command
    public void initDefaultCommand() {
    }

    // Returns whether the left target is lit up. Returns true by default.
    public boolean leftTargetHot() {
        if(camera == null){
            System.out.println("No axis camera!");
            return true;
        }
        if (!camera.freshImage()) {
            System.out.println("No fresh camera image!");
            return true;
        }
        try {
            image = camera.getImage();
        } catch (AxisCameraException ex) {
            System.out.println("Obtaining image failed: AxisCameraException!");
            return true;
        } catch (NIVisionException ex) {
            System.out.println("Obtaining image failed: NIVisionException!");
            return true;
        }
        try {
            
            // Put vision processing here to set leftTargetHot
            
            image.free();
        } catch (NIVisionException ex) {
            System.out.println("Vision processing failed!");
            return true;
        }
        return leftTargetHot;
    }

    // Turns the camera lights on or off
    public void setCameraLights(boolean on) {
        if (on) {
            cameraLights.set(Relay.Value.kOn);
        } else {
            cameraLights.set(Relay.Value.kOff);
        }
    }
}
