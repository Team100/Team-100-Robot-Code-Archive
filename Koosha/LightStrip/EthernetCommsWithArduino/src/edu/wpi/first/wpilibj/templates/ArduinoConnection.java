/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.util.StringTokenizer;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.Timer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/**
 *
 * @author Laura
 */
public class ArduinoConnection {
    /* Communication with an Arduino equipped with an Ethernet shield. The
     * Arduino controls the LED light strip display.
     * Code developed with help from code by Gustave Michel from FRC Team 3946,
     * which had code for communication between the cRIO and a 
     * Raspberry Pi using ethernet sockets 
     * https://github.com/frc3946/UltimateAscent/blob/master/src/org/usfirst/frc3946/UltimateAscent/ThreadedPi.java
     */

    private DigitalModule digital = DigitalModule.getInstance(1);
    
    private String url = "socket://10.1.0.100:10000";
    private int bufferSize = 64;
    private char delimiter = ',';
    
    private SocketConnection m_socket;
    private InputStream m_is;
    private OutputStream m_os;
    
    String m_rawData;
    byte[] m_receivedData;
    
    private boolean m_connected = false;
    
    Thread m_thread;
    private boolean m_enabled =false;
    private boolean m_run = true;

    public static class ArduinoMessage {
        private static double mTime;
        private static int mSeqID;
        private static int mLightCode;
        private static boolean mReport;

        public static synchronized int getLightCode() {
            return mLightCode;
        }

        public static synchronized int getSeqID() {
            return mSeqID;
        }

        public static synchronized double getTime() {
            return mTime;
        }
        
        public static synchronized boolean getReport() {
            return mReport;
        }       

        public static synchronized void setLightCode(int lightCode) {
            ArduinoMessage.mLightCode = lightCode;
        }

        public static synchronized void setReport(boolean report) {
            ArduinoMessage.mReport = report;
        }

        public static synchronized void setSeqID(int seqID) {
            ArduinoMessage.mSeqID = seqID;
        }

        public static synchronized void setTime(double time) {
            ArduinoMessage.mTime = time;
        }       
    }
    
    private class ArduinoCommsThread extends Thread {
        ArduinoConnection mArduinoConnection;
        private double time;
        public int seqID;
        public int lightCode;
        private boolean report;
    
        public ArduinoCommsThread(ArduinoConnection arduinoConnection) {
            super("ArduinoSocket");
            mArduinoConnection = arduinoConnection;
        }
        
        public void run() {
            while(m_run) {
                if(mArduinoConnection.isEnabled()) { //Checks for Thread to run
                    if(mArduinoConnection.isConnected()) {
                        report = true;
                        try {
                            String[] data = mArduinoConnection.tokenizeData(mArduinoConnection.getRawData()); //Get and examine Data
                            time = Timer.getFPGATimestamp();
                            if(data.length < 2) { //Error Check
                                report = false;
                            } else {
                                try {
                                    seqID = Integer.parseInt(data[1]); //Get data
                                    lightCode = Integer.parseInt(data[0]);
                                } catch(NumberFormatException ex) {
                                    report = false;
                                }
                            }
                        } catch (IOException ex) {
                            report = false;
                        }
                        ArduinoMessage.setReport(report);
                                
                        if(report) { //Store Data in DataKeeper
                            ArduinoMessage.setSeqID(seqID);
                            ArduinoMessage.setLightCode(lightCode);
                            ArduinoMessage.setTime(time);
                        }
                    } else {
                        try {
                            mArduinoConnection.connect();
                        } catch (IOException ex) {
                            ArduinoMessage.setReport(false);
                        }
                    }
                }
                try {
                    Thread.sleep(375); //Wait half second before getting Data again
                } catch(InterruptedException ex) {}
            }
        }
    }
    
    public ArduinoConnection() {
        m_enabled = false;
        System.out.println("Creating ArduinoCommsThread");
        m_thread = new ArduinoCommsThread(this);
        
        try{
            System.out.println("trying to connect with Arduino");
         connect();
        } catch (IOException ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        System.out.println("trying to connect with Arduino");
        m_thread.start();
    }
    
    public synchronized void connect() throws IOException {
        m_socket = (SocketConnection) Connector.open(url);//, Connector.READ_WRITE, true);
        m_is = m_socket.openInputStream();
        m_os = m_socket.openOutputStream();
        m_connected = true;
        
    }
    
    public synchronized void disconnect() throws IOException {
        m_socket.close();
        m_is.close();
        m_os.close();
        m_connected = false;
    }
    
    public synchronized boolean isConnected() {
        //need to actually test the connection
        //to figure out if we're connected or not
        try{
            m_os.write('\n'); //request Data
            m_connected = true;
        } catch(IOException ex){
            m_connected = false;
        } catch(Exception ex) {
            m_connected = false;
            
        }
        
        return m_connected;
    }
    
    public synchronized boolean isEnabled() {
        return m_enabled;
    }
    
    public int getLightCode() {
        return ArduinoMessage.getLightCode();
    }
    public int getSeqID() {
        return ArduinoMessage.getSeqID();
    }
    
    public double getTime() {
        return ArduinoMessage.getTime();
    }
    
    public boolean getReport() {
        return ArduinoMessage.getReport();
    }
    
    public synchronized void start() {
        m_enabled = true;
    }
    
    public synchronized void stop() {
        m_enabled = false;
    }
    
    /**
* Requests data from RaspberryPi
* @return String returned from RaspberryPi
* @throws IOException
*/
    public synchronized String getRawData() throws IOException {
        byte[] input;
        byte[] output = new byte[14];
        for(int i = 0; i < 14; i++) {
            output[i] = (byte) (digital.getDIO(i+1) ? 49:48);
        }

        if (m_connected) {
//            m_os.write(getSeqID() + 1); //request Data
//            m_os.write(100);
            m_os.write(output, 0, output.length);
            System.out.println("Sent request " + (getSeqID() + 1));
            
            if(m_is.available() <= bufferSize) {
                input = new byte[m_is.available()]; //storage space sized to fit!
                m_receivedData = new byte[m_is.available()];
                m_is.read(input);
                for(int i = 0; (i < input.length) && (input != null); i++) {
                    m_receivedData[i] = input[i]; //transfer input to full size storage
                }
            } else {
                System.out.println("ARDUINO OVERFLOW");
                m_is.skip(m_is.available()); //reset if more is stored than buffer
                return null;
            }
            
            m_rawData = ""; //String to transfer received data to
            System.out.println("Raw Data: "+m_receivedData.length);
            for (int i = 0; i < m_receivedData.length; i++) {
                m_rawData += (char) m_receivedData[i]; //Cast bytes to chars and concatinate them to the String
            }
            System.out.println(m_rawData);
            return m_rawData;
        } else {
            connect();
            return null;
        }
    }
    
    /**
* Separates input String into many Strings based on the delimiter given
* @param input String to be tokenized
* @return String Array of Tokenized Input String
*/
    public synchronized String[] tokenizeData(String input) {
        StringTokenizer tokenizer = new StringTokenizer(input, String.valueOf(delimiter));
        String output[] = new String[tokenizer.countTokens()];
        
        for(int i = 0; i < output.length; i++) {
            output[i] = tokenizer.nextToken();
        }
        return output;
    }

    
}
