/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.SPIDevice;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotControlledLightStrip extends SimpleRobot {
    private static int NUM_LEDS = 89;
    
    private SPIDevice spiLightStrip;
    private DigitalOutput cs;
    private DigitalOutput mosi;
    private DigitalOutput clk;
    private DigitalInput miso;

    protected void robotInit() {
        super.robotInit();
        cs = new DigitalOutput(1, 7); 
        miso = null;
        mosi = new DigitalOutput(1,12);  
        clk = new DigitalOutput(1,13);
        
        spiLightStrip = new SPIDevice(clk, mosi, miso, cs);
        //spiLightStrip.setBitOrder(true);
        //spiLightStrip.setClockPolarity(true);
        spiLightStrip.setClockRate(spiLightStrip.MAX_CLOCK_FREQUENCY);
        
        //spiLightStrip.setFrameMode(SPIDevice.FRAME_MODE_PRE_LATCH);
        //spiLightStrip.setDataOnTrailing(true);
    }
    
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        long[] writeValues = new long[NUM_LEDS];
        int[] numBits = new int[NUM_LEDS];
        
        int color_idx = 0;
        int NUM_COLORS = 8;
        String colorName = "";
        while(isOperatorControl()) {
            long color;
            color_idx %= (NUM_COLORS * 2);

            if (spiLightStrip != null) {
                if ((color_idx % 2) == 0) {colorName = "Black"; color = Color.BLACK;}
                else if (color_idx == 1) {colorName = "Red"; color = Color.RED;}
                else if (color_idx == 3) {colorName = "Yellow"; color = Color.YELLOW;}
                else if (color_idx == 5) {colorName = "Green"; color = Color.GREEN;}
                else if (color_idx == 7) {colorName = "Blue"; color = Color.BLUE;}
                else if (color_idx == 9) {colorName = "White"; color = Color.WHITE;}
                else if (color_idx == 11) {colorName = "Violet"; color = Color.VIOLET;}
                else if (color_idx == 13) {colorName = "Aqua"; color = Color.AQUA;}
                else if (color_idx == 15) {colorName = "Orange"; color = Color.ORANGE;}
                else {colorName = "Black"; color = Color.BLACK;}
                for (int i = 0; i < NUM_LEDS; i ++) {

                    
                    writeValues[i] = color;
                    numBits[i] = 24;
                }
                System.out.print("Sending Color: "  + colorName + " 0x"  + Integer.toHexString((int)color));
                double start = Timer.getFPGATimestamp();
                spiLightStrip.transfer(writeValues, numBits);
                double end = Timer.getFPGATimestamp();
                System.out.println (" Start: " + String.valueOf(start) + 
                                    " End: " + String.valueOf(end) + 
                                    "Delta: " + String.valueOf(end - start));
                Timer.delay(2.0); 
                color_idx ++;
            } 
            
        }
    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
    
    private static class Color {
        private static long WHITE = 0xFFFFFF;
        private static long RED = 0xFF0000;
        private static long YELLOW = 0xFFFF00;
        private static long GREEN = 0x00FF00;
        private static long BLUE = 0x0000FF;
        private static long VIOLET = 0xFF00FF;
        private static long AQUA = 0x00FFFF;
        //private static long ORANGE = 0xff8c00;
        private static long ORANGE = 0xC92500;
        private static long BLACK = 0x000000;
    }
}
