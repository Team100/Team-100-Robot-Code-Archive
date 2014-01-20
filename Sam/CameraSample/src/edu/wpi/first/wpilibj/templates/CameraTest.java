
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.image.RGBImage;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Sample program to use NIVision to find rectangles in the scene that are illuminated
 * by a red ring light (similar to the model from FIRSTChoice). The camera sensitivity
 * is set very low so as to only show light sources and remove any distracting parts
 * of the image.
 * 
 * The CriteriaCollection is the set of criteria that is used to filter the set of
 * rectangles that are detected. In this example we're looking for rectangles with
 * a minimum width of 30 pixels and maximum of 400 pixels. Similar for height (see
 * the addCriteria() methods below.
 * 
 * The algorithm first does a color threshold operation that only takes objects in the
 * scene that have a significant red color component. Then removes small objects that
 * might be caused by red reflection scattered from other parts of the scene. Then
 * a convex hull operation fills all the rectangle outlines (even the partially occluded
 * ones). Finally a particle filter looks for all the shapes that meet the requirements
 * specified in the criteria collection.
 *
 * Look in the VisionImages directory inside the project that is created for the sample
 * images as well as the NI Vision Assistant file that contains the vision command
 * chain (open it with the Vision Assistant)
 */
public class CameraTest extends SimpleRobot {
    
    AxisCamera camera;          // the axis camera object (connected to the switch)
    CriteriaCollection cc;      // the criteria for doing the particle filter operation
    
    public void robotInit() {
        camera = AxisCamera.getInstance("10.1.0.11");  // get an instance ofthe camera
    }

    public void autonomous() {
        //
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        while (isOperatorControl() && isEnabled()&&camera.freshImage()) {
            ColorImage image = null;
            try {
                image = camera.getImage();
            } catch (AxisCameraException ex) {
                ex.printStackTrace();
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            }
            
            
           
            
            
            
            Timer.delay(1);
        }
    }
}
        