package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.microedition.io.FileConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.io.Connector;

/**
 * Stores all changeable or arbitrary values in a file on the cRIO. If code
 * contains any number besides 0 or 1, chances are it should be a preference.
 */
public class Preferences {

    private static String file = ""; // Duplicate of the entire preference file

////Preference Input/Output/////////////////////////////////////////////////////
    // <editor-fold>
    // Creates or sets a preference, but does not modify cRIO file
    public static void setPreference(String name, Object value) {
        if ("".equals(file)) {
            System.out.println("ERROR: File is empty");
            return;
        }
        int startIndex = file.indexOf("\n" + name + " ") + 1;
        int endIndex = file.indexOf("\n", startIndex) - 1;
        if (startIndex <= 0 || endIndex <= 0) {
            file = file + "\n" + name + " " + value;
            System.out.println("Preference " + name + " added");
        } else {
            file = file.substring(0, startIndex) + name + " " + value + file.substring(endIndex, file.length());
        }
    }

    // Casts double to an object for regular setPreference method
    public static void setPreference(String name, double value) {
        setPreference(name, Double.valueOf(value));
    }
    
    // Casts boolean to an object for regular setPreference method
    public static void setPreference(String name, boolean value) {
        setPreference(name, Boolean.valueOf(value));
    }

    // Returns value of the double preference with the given name
    public static double getDoubleValue(String name) {
        return Double.parseDouble(getStringValue(name));
    }

    // Returns value of the boolean preference with the given name
    public static boolean getBooleanValue(String name) {
        String value = getStringValue(name);
        if (!(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
            System.out.println("ERROR: no boolean found: " + name + " " + value);
        }
        return "true".equals(value);
    }

    // Returns value of the String preference with the given name
    public static String getStringValue(String name) {
        if ("".equals(file)) {
            System.out.println("ERROR: File is empty");
            return "0";
        }
        int startIndex = file.indexOf("\n" + name + " ") + 1;
        int endIndex = file.indexOf("\n", startIndex) - 1;
        if (startIndex <= 0 || endIndex <= 0) {
            System.out.println("Preference " + name + " not found");
            return "0";
        }
        return file.substring(startIndex + name.length() + 1, endIndex);
    }

    // Returns whether a preference is present
    public static boolean containsKey(String name) {
        if ("".equals(file)) {
            System.out.println("ERROR: File is empty");
            return false;
        }
        int startIndex = file.indexOf("\n" + name + " ", 0) + 1;
        int endIndex = file.indexOf("\n", startIndex) - 1;
        return !(startIndex <= 0 || endIndex <= 0);
    }
    // </editor-fold>

////File Read/Write/////////////////////////////////////////////////////////////
    // <editor-fold>
    // Pulls preferences from cRIO file, overwrites any existing preferences
    public static void readFromFile() {
        DataInputStream actualFile;
        FileConnection fc;
        file = "";
        try {
            fc = (FileConnection) Connector.open("file:///Preferences.txt", Connector.READ);
            actualFile = fc.openDataInputStream();
            //RobotMap.stopAllMotors(); // just a precaution...this takes a while
            for (int i = 0; i < 16384; i++) {
                int nextChar = actualFile.read();
                if (nextChar == -1) {
                    break;
                }
                file = file + (char) nextChar;
            }
        } catch (IOException e) {
            System.out.println("ERROR: File Reading Failed");
        }
    }

    // Overwrites cRIO file with current preferences
    public static void writeToFile() {
        DataOutputStream actualFile;
        FileConnection fc;
        if ("".equals(file)) {
            System.out.println("ERROR: File is empty");
            return;
        }
        try {
            fc = (FileConnection) Connector.open("file:///Preferences.txt", Connector.WRITE);
            fc.create();
            actualFile = fc.openDataOutputStream();
            for (int i = 0; i < file.length(); i++) {
                actualFile.write(file.charAt(i));
            }
        } catch (IOException e) {
            System.out.println("ERROR: File Writing Failed");
        }
    }
    // </editor-fold>
}
