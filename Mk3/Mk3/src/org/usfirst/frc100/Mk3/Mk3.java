/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc100.Mk3;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Mk3.commands.Autonomous;
import org.usfirst.frc100.Mk3.commands.CommandBase;
import org.usfirst.frc100.Mk3.commands.LastSecondHang;
import org.usfirst.frc100.Mk3.commands.PrimeHighSpeed;
import org.usfirst.frc100.Mk3.commands.Shoot;
import org.usfirst.frc100.Mk3.commands.TiltDown;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Mk3 extends IterativeRobot {
    // Get modules once, because it's expensive

    DigitalModule digitalModule = DigitalModule.getInstance(1);
    AnalogModule analogModule = AnalogModule.getInstance(1);
    DriverStationLCD dsLCD = DriverStationLCD.getInstance();
    DriverStation ds = DriverStation.getInstance();
    Autonomous autoCommand = new Autonomous();
    // Loop period periodTimer
    Timer periodTimer = new Timer();
    Timer testIOTimer = new Timer();
    //Shooting delays
    private final double kDefaultInitialDelay = 1.0;
    private final double kDefaultTimeout = 7.0;
    private final boolean kDefaultLastSecondOn = true;
    private final double kDefaultLastSecondTimeout = 10.0;
    private LastSecondHang hang = null;

    public void robotInit() {
        // Initialize all devices and subsystems
        RobotMap.init();
        CommandBase.init();
        //Add autonomous prefs
        Preferences p = Preferences.getInstance(); //Preferences are used to store and change constants without having to reboot the robot.
        if (!p.containsKey("AutonInitialDelay")) {
            p.putDouble("AutonInitialDelay", kDefaultInitialDelay);
        }
        if (!p.containsKey("AutonTimeout")) {
            p.putDouble("AutonTimeout", kDefaultTimeout);
        }
        if (!p.containsKey("HangerLastSecondOn")) {
            p.putBoolean("HangerLastSecondOn", kDefaultLastSecondOn);
        }
        if (!p.containsKey("HangerLastSecondTimeout")) {
            p.putDouble("HangerLastSecondTimeout", kDefaultLastSecondTimeout);
        }
    }//end robotInit

    public void disabledInit() {
        CommandBase.disableAll();
    }//end disabledInit

    public void autonomousInit() {
        initializeAll();
        if (false) { //Returns false so autoCommand won't run
            autoCommand.start();
        } else { //This else block will shoot all the Discs in the hooper.
            final double kInitialDelay = Preferences.getInstance().getDouble("AutonInitialDelay", kDefaultInitialDelay);
            final double kTimeout = Preferences.getInstance().getDouble("AutonTimeout", kDefaultTimeout);
            TiltDown down = new TiltDown();
            down.start();
            PrimeHighSpeed high = new PrimeHighSpeed();
            high.start();
            Shoot shoot = new Shoot(kInitialDelay, kTimeout);
            shoot.start();
        }
        //Timing
        periodTimer.reset();
        periodTimer.start();
    }//end autonomousInit

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Period", periodTimer.get());
        printDataToDriverStation();
        periodTimer.reset();
    }//end autonomousPeriodic

    public void teleopInit() {
        hang = null;
        Scheduler.getInstance().removeAll();

        // Redo full init sequence, in case we didn't run autonomous
        initializeAll();

        // Teleop relies on button-originated commands and default commands

        periodTimer.reset();
        periodTimer.start();
        testIOTimer.reset();
        testIOTimer.start();
    }//end teleopInit

    public void teleopPeriodic() {
        Preferences p = Preferences.getInstance();
        final boolean kLastSecondOn = p.getBoolean("HangerLastSecondOn", kDefaultLastSecondOn);
        final double kLastSecondTimeout = p.getDouble("HangerLastSecondTimeout", kDefaultLastSecondTimeout);
        double matchTime = ds.getMatchTime();
        if (matchTime >= kLastSecondTimeout && hang == null && kLastSecondOn) { //Will attempt to hang during the final seconds of the match
            hang = new LastSecondHang();
            hang.start();
        }
        SmartDashboard.putNumber("MatchTime", matchTime);
        Scheduler.getInstance().run();
        testIO();
        SmartDashboard.putNumber("Period", periodTimer.get());
        printDataToDriverStation();
        periodTimer.reset();
    }//end teleopPeriodic

    public void testPeriodic() {
        LiveWindow.run();
        // Scheduler is not invoked
    }//end testPeriodic

    public void testInit() {
        Scheduler.getInstance().removeAll();
        CommandBase.disableAll();
        CommandBase.unSafeAll();
        CommandBase.pneumatics.enable();//starts the compressor
    }//end testInit

    //Load PID Info has been integrated into UpdateWidgets
    //Code used for Debuging
    public void testIO() {
        // Don't do this every iteration because it's too expensive
        if (testIOTimer.get() < 0.5) {
            return;
        }
        testIOTimer.reset();
        NetworkTable table = NetworkTable.getTable("Status");
        table.putNumber("dioData", digitalModule.getAllDIO());
        for (int i = 1; i <= 10; i++) {
            table.putNumber("pwm" + i, digitalModule.getPWM(i));
        }

        for (int i = 1; i <= 8; i++) {
            table.putNumber("analog" + i, ((int) (analogModule.getVoltage(i) * 1000) / 1000.0));
        }
    }//end testIO

    private void initializeAll() {
        Preferences p = Preferences.getInstance();
        if (!p.containsKey("RobotSafetyEnabled")) {
            p.putBoolean("RobotSafetyEnabled", false);
        }
        if (p.getBoolean("RobotSafetyEnabled", false)) {
            CommandBase.safeAll();
        } else {
            CommandBase.unSafeAll();
        }
        CommandBase.pneumatics.enable();
        CommandBase.shifter.shiftHighGear();
    }//end initializeAll

    //Data used to debug
    private void printDataToDriverStation() {
        //pneumatics systems have no default command,
        //so need to account for null pointer case
        String spaces = "                 ";
        if (CommandBase.hanger.getCurrentCommand() == null) {
            dsLCD.println(DriverStationLCD.Line.kUser1, 1, "Hanger: None" + spaces);
        } else {
            dsLCD.println(DriverStationLCD.Line.kUser1, 1, "Hanger: " + CommandBase.hanger.getCurrentCommand().toString() + spaces);
        }
        if (CommandBase.shooter.getCurrentCommand().toString().equals("PrimeHighSpeed")) {
            dsLCD.println(DriverStationLCD.Line.kUser2, 1, "Shooter: HighSpeed" + spaces);
        } else if (CommandBase.shooter.getCurrentCommand().toString().equals("PrimeLowSpeed")) {
            dsLCD.println(DriverStationLCD.Line.kUser2, 1, "Shooter: LowSpeed" + spaces);
        } else {
            dsLCD.println(DriverStationLCD.Line.kUser2, 1, "Shooter: " + CommandBase.shooter.getCurrentCommand().toString() + spaces);
        }
        dsLCD.println(DriverStationLCD.Line.kUser3, 1, "DriveTrain: " + CommandBase.driveTrain.getCurrentCommand().toString() + spaces);
        if (CommandBase.feeder.getCurrentCommand() == null) {
            dsLCD.println(DriverStationLCD.Line.kUser4, 1, "Feeder: None" + spaces);
        } else {
            dsLCD.println(DriverStationLCD.Line.kUser4, 1, "Feeder: " + CommandBase.feeder.getCurrentCommand().toString() + spaces);
        }
        if (CommandBase.tilter.getCurrentCommand() == null) {
            dsLCD.println(DriverStationLCD.Line.kUser5, 1, "Tilter: None" + spaces);
        } else {
            dsLCD.println(DriverStationLCD.Line.kUser5, 1, "Tilter: " + CommandBase.tilter.getCurrentCommand().toString() + spaces);
        }
        dsLCD.println(DriverStationLCD.Line.kUser6, 1, "Period: " + periodTimer.get() + spaces);

//        //In case we want to see shifter/pneumatics instead on line 6
//        if (CommandBase.shifter.getCurrentCommand() == null){
//           dsLCD.println(DriverStationLCD.Line.kUser6, 1, "Shifter: None" + "        ");
//        } else  {
//           dsLCD.println(DriverStationLCD.Line.kUser6, 1, "Shifter: "+ CommandBase.shifter.getCurrentCommand().toString(); +"        ");
//        }
//        if (CommandBase.pneumatics.getCurrentCommand() == null){
//           dsLCD.println(DriverStationLCD.Line.kUser6, 1, "Compressor: None" + "        ");
//        } else  {
//           dsLCD.println(DriverStationLCD.Line.kUser6, 1, "Compressor: "+ CommandBase.pneumatics.getCurrentCommand().toString(); +"        ");
//        }

        dsLCD.updateLCD();
    }//end printDataToDriverStation
}//end FrisBeast
