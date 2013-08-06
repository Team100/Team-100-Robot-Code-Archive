
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionSampleProject2013 extends SimpleRobot {

    final int XMAXSIZE = 24;
    final int XMINSIZE = 24;
    final int YMAXSIZE = 24;
    final int YMINSIZE = 48;

    final int RECTANGULARITY_LIMIT = 60;
    final int ASPECT_RATIO_LIMIT = 75;
    final int X_EDGE_LIMIT = 40;
    final int Y_EDGE_LIMIT = 60;
    
    final int X_IMAGE_RES = 320;          //X Image resolution in pixels, should be 160, 320 or 640
    final double VIEW_ANGLE = 43.5;       //Axis 206 camera
//    final double VIEW_ANGLE = 48;       //Axis M1011 camera
    
    AxisCamera camera=AxisCamera.getInstance();          // the axis camera object (connected to the switch)
    CriteriaCollection cc;      // the criteria for doing the particle filter operation
    
    public class Scores {
        double rectangularity;
        double aspectRatioInner;
        double aspectRatioOuter;
        double xEdge;
        double yEdge;
    }
    
    public void robotInit() {
        cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(MeasurementType.IMAQ_MT_AREA, 500, 65535, false);
    }

    public void autonomous() {
        while (isAutonomous() && isEnabled()) {
            if(!camera.freshImage()){
                autonomous();
                return;
            }
            try {
                ColorImage image=null;     
                try {
                    image = camera.getImage(); 
                } catch (AxisCameraException ex) {
                    ex.printStackTrace();
                    autonomous();
                    return;
                } catch (NIVisionException ex) {
                    ex.printStackTrace();
                }
                BinaryImage thresholdImage = image.thresholdRGB(220, 255, 200, 255, 200, 255);   // keep only red objects
                //BinaryImage thresholdImage = image.thresholdHSV(60, 100, 90, 255, 20, 255);   // keep only red objects
                BinaryImage filteredImage = thresholdImage.particleFilter(cc);          // fill in occluded rectangles
                BinaryImage convexHullImage = filteredImage.convexHull(false);           // filter out small particles
                BinaryImage filteredImage2 = convexHullImage.particleFilter(cc);    
                BinaryImage filteredImage3 = filteredImage2.particleFilter(cc);    
                BinaryImage filteredImage4 = filteredImage3.particleFilter(cc);    
                BinaryImage filteredImage5 = filteredImage4.particleFilter(cc);    
                BinaryImage filteredImage6 = filteredImage5.particleFilter(cc);    
                BinaryImage filteredImage7 = filteredImage6.particleFilter(cc);    
                BinaryImage filteredImage8 = filteredImage7.particleFilter(cc);    
                
                Scores scores[] = new Scores[filteredImage8.getNumberParticles()];
                System.out.println(scores.length);
                for (int i = 0; i < scores.length; i++) {
                    
                    ParticleAnalysisReport report = filteredImage8.getParticleAnalysisReport(i);
                    //scores[i] = new Scores();
                    
                    if(report.boundingRectHeight*report.boundingRectWidth*.8<report.particleArea){
                        System.out.println("particle: " + i + " is a Goal!!!  centerX: " + report.center_mass_x_normalized + "centerY: " + report.center_mass_y_normalized);
                        System.out.println("Distance: " + computeDistance(filteredImage8, report, i, false));
                        double x=report.center_mass_x_normalized,y=report.center_mass_y_normalized;
                        SmartDashboard.putNumber("CameraTarget", 1000*(int)((x+1)*500)+(int)((y+1)*500));
                    }
                }
                filteredImage.free();
                filteredImage2.free();
                filteredImage3.free();
                filteredImage4.free();
                filteredImage5.free();
                filteredImage6.free();
                filteredImage7.free();
                filteredImage8.free();
                convexHullImage.free();
                thresholdImage.free();
                image.free();
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        double x=.25,y=.25;
        SmartDashboard.putNumber("TEST", 1000*(int)((x+1)*500)+(int)((y+1)*500));
        while (isOperatorControl() && isEnabled()) {
            Timer.delay(1);
            SmartDashboard.putNumber("TEST", 1000*(int)((x+1)*500)+(int)((y+1)*500));
        }
    }
    
    /**
     * Computes the estimated distance to a target using the height of the particle in the image. For more information and graphics
     * showing the math behind this approach see the Vision Processing section of the ScreenStepsLive documentation.
     * 
     * @param image The image to use for measuring the particle estimated rectangle
     * @param report The Particle Analysis Report for the particle
     * @param outer True if the particle should be treated as an outer target, false to treat it as a center target
     * @return The estimated distance to the target in Inches.
     */
    double computeDistance (BinaryImage image, ParticleAnalysisReport report, int particleNumber, boolean outer) throws NIVisionException {
            double rectShort, height;
            int targetHeight;

            rectShort = NIVision.MeasureParticle(image.image, particleNumber, false, MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
            //using the smaller of the estimated rectangle short side and the bounding rectangle height results in better performance
            //on skewed rectangles
            height = Math.min(report.boundingRectHeight, rectShort);
            targetHeight = outer ? 29 : 21;

            return X_IMAGE_RES * targetHeight / (height * 12 * 2 * Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
    }
}