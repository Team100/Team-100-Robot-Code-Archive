/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Koosha Jahani
 */
public class Multiplexer extends SensorBase
{
    private final int kDigitalModule = 1;
    private final int kAddress = 0x64; // address is 100
    private byte hiByte;
    private byte loByte;
    private boolean successful;
    
    private Timer timer;
    private I2C m_I2C;
    
    public Multiplexer()
    {
        m_I2C = new I2C(DigitalModule.getInstance(kDigitalModule), kAddress);
        timer = new Timer();
    }
     
/*
 * reads the arduino and recieves 2 bytes to represent the digital inputs attached to it
 * saves the 2 bytes to 'loByte' and 'hiByte'
 * return: true if and only if it's successful
 */
    public boolean readArduino()
    {
        byte[] buffer = {0,0};
        
        successful = !m_I2C.transaction(null, 0, buffer, 2); // doesn't send anything and recieves 2 bytes of data; successful==true if transaction is succesful
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "                     ");
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "                     ");
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "success=" + successful);
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "buffer=" + buffer[1] + ", " + buffer[0]);
        DriverStationLCD.getInstance().updateLCD();
        hiByte = buffer[1];
        loByte = buffer[0];
        
        return successful;
    }
    
    
/*
 * reads the value of the digital input of the specified channel
 * requires that the readArudiono() method has already been called
 * param 1: the channel to be read
 * return: value of the specified digital input
 */
    public boolean getInput(int channelID)
    {
        byte temp;
        
        if(timer.get()>2.0)
        {
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, "                     ");
            DriverStationLCD.getInstance().updateLCD();
            timer.stop();
            timer.reset();
        }
        
        if(Math.abs(channelID-(13/2))-(13/2)>0) // returns true if less than zero or greater than thirteen
        {
            timer.start();
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, "Invalid ID");
            DriverStationLCD.getInstance().updateLCD();
            return false;
        }
        else if(channelID < 8)
        {
            temp = (byte) (loByte & (1 << channelID));
            return temp!=0;
        }
        else
        {
            temp = (byte) (hiByte & (1 << channelID-8));
            return temp!=0;
        }
    }
}
