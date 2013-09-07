
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.RobotMap;

public class Arm extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private final Jaguar upperjaw = new Jaguar(RobotMap.pwm_highjawmotor);
    private final Jaguar lowerjaw = new Jaguar(RobotMap.pwm_lowjawmotor);
    private final Jaguar arm = new Jaguar(RobotMap.pwm_shouldermotor);

    public void initDefaultCommand() {
    }

    public void tiltTubeUp(){
        upperjaw.set(0.5);
        lowerjaw.set(-0.25);
    }
    public void tiltTubeDown(){
        lowerjaw.set(0.5);
        upperjaw.set(-0.25);
    }
    public void grabTube(){
        upperjaw.set(0.5);
        lowerjaw.set(0.5);
    }
    public void dropTube(){
        upperjaw.set(-0.5);
        lowerjaw.set(-0.5);
    }
    public void controlMouth(Joystick j1, Joystick j2){
        upperjaw.set(j1.getY()/90);
        lowerjaw.set(j2.getY()/90);
    }
}