/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    
    //Used only for testing on the test board, not neccesary for the loop timing.
    private final Joystick joystick;
    private final Victor victor1;
    private final Victor victor2;
    private final Jaguar jag;
    
    //The actual loop timing happens in counter.
    private final Counter counter;
    
    public RobotTemplate(){
     joystick = new Joystick(1);
     victor1 = new Victor(4);
     victor2 = new Victor(6);
     jag = new Jaguar(5);
     
     //Initialize counter by giving it the current time
     counter = new Counter(System.currentTimeMillis());
     
    }
    
    public void robotInit() {
        

    }

    public void autonomousPeriodic() {

    }
    

    public void teleopPeriodic() {
        
        counter.update();//Smartdashboard values & getting the last delay
       
    }
    
}
