package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    int count=0;
    int tensecs=0;
    boolean done=false;
    boolean firsttime=true;
    Timer t=new Timer();
    
    public void robotInit() {
        t.reset();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        if (firsttime){
            t.start();
            firsttime=false;
        }
        count+=1;
        if (t.get()>=10&&!done){
            tensecs=count;
            done=true;
        }
        SmartDashboard.putNumber("Count:", count);
        SmartDashboard.putNumber("Time:", t.get());
        SmartDashboard.putNumber("Count at 10 seconds:", tensecs);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
}