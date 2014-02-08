/* 
 * @author Paul
 * This class can locate a 2013 3-point goal using vision and display the position of the target on the smartdashboard using Paul's CameraAim widget.
 * It requires the target to be lit up to work.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.camera.*;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionSampleProject2013 extends SimpleRobot {

    AxisCamera camera = AxisCamera.getInstance();
    CriteriaCollection cc = new CriteriaCollection();

    public void robotInit() {
        cc.addCriteria(MeasurementType.IMAQ_MT_AREA, 300, 65535, false);
        if (!Preferences.getInstance().containsKey("threshold1lower")) {
            Preferences.getInstance().putDouble("threshold1lower", 0);
            Preferences.getInstance().putDouble("threshold1upper", 255);
            Preferences.getInstance().putDouble("threshold2lower", 0);
            Preferences.getInstance().putDouble("threshold2upper", 255);
            Preferences.getInstance().putDouble("threshold3lower", 210);
            Preferences.getInstance().putDouble("threshold3upper", 255);
        }
    }

    public void autonomous() {
        while (isAutonomous() && isEnabled()) {
            if (!camera.freshImage()) {
                Timer.delay(1);
                autonomous();
                return;
            }
            try {
                ColorImage image;
                try {
                    image = camera.getImage();
                } catch (AxisCameraException ex) {
                    Timer.delay(1);
                    autonomous();
                    return;
                } catch (NIVisionException ex) {
                    Timer.delay(1);
                    autonomous();
                    return;
                }
                Preferences p = Preferences.getInstance();
                BinaryImage thresholdImage = image.thresholdHSV((int) p.getDouble("threshold1lower", 0), (int) p.getDouble("threshold1upper", 0), (int) p.getDouble("threshold2lower", 0), (int) p.getDouble("threshold2upper", 0), (int) p.getDouble("threshold3lower", 0), (int) p.getDouble("threshold3upper", 0));

                BinaryImage filteredImage = thresholdImage.particleFilter(cc);          // fill in occluded rectangles
                BinaryImage convexHullImage = filteredImage.convexHull(false);           // filter out small particles
                BinaryImage filteredImage2 = convexHullImage.particleFilter(cc);
                BinaryImage filteredImage3 = filteredImage2.particleFilter(cc);
                BinaryImage filteredImage4 = filteredImage3.particleFilter(cc);

                for (int i = 0; i < filteredImage4.getNumberParticles(); i++) {

                    ParticleAnalysisReport report = filteredImage4.getParticleAnalysisReport(i);
                    if (report.boundingRectHeight * report.boundingRectWidth * .8 < report.particleArea && report.boundingRectWidth < report.boundingRectHeight * 2.4 && report.boundingRectWidth > report.boundingRectHeight * 1.6) {
                        System.out.println("particle: " + i + " is a Goal!!! Width: " + report.boundingRectWidth + " Height: " + report.boundingRectHeight + " Area " + report.particleArea);
                        double x = report.center_mass_x_normalized, y = report.center_mass_y_normalized;
                        SmartDashboard.putNumber("CameraTarget", 1000 * (int) ((x + 1) * 500) + (int) ((y + 1) * 500));
                    }
                }
                filteredImage.free();
                filteredImage2.free();
                filteredImage3.free();
                filteredImage4.free();
                convexHullImage.free();
                thresholdImage.free();
                image.free();
            } catch (NIVisionException ex) {
                Timer.delay(1);
                autonomous();
                return;
            }
        }
    }
}