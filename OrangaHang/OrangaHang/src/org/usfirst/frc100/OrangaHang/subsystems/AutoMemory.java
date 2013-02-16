/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import org.usfirst.frc100.OrangaHang.FileRW;

/**
 *
 * @author Sam
 */
public class AutoMemory extends Subsystem{
    
    
    Vector LeftMemory;
    Vector RightMemory;
    Vector ShootButton;
    Vector PrimeShootButton;

    public static String AutoList;
    String writeFile;
    
    public AutoMemory(){
        updateAuto();
        SmartDashboard.putString("Autonomous List", AutoList);
    }
    
    protected void initDefaultCommand() {
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private static void updateAuto(){
        //List :name,name,name,name
        //Names are not in path format
        Preferences pref = Preferences.getInstance();
        
        AutoList = "";
        if(pref.containsKey("autoList")){
            AutoList = pref.getString("autoList", "");    
        }else{
            pref.putString("autoList", "");
        }
        SmartDashboard.putString("Autonomous List", AutoList);
        pref.save();
    }
    
    /**
     * Adds name to the list located in the preferences file. The it calls updateAuto
     * @see updateAuto
     * @param name
     */
    public static void addAuto(String name){
        Preferences pref = Preferences.getInstance();
        String list = pref.getString("autoList", "");
        if("".equals(list)){
            list = name;
        }else{
            if(list.indexOf(name) > -1){
                list = list +","+ name;
            } 
        }
        pref.putString("autoList", list);
        updateAuto();
    }
    
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Gets the path from the smartdashboard and then resets the Vectors
     */
    public void beginCollection(){
        writeFile = "file:///autonomous/" + SmartDashboard.getString("Name Autonomous Procedure") + ".sam";
        System.out.println("AutoMemory,beginCollection" + writeFile);
        
        LeftMemory  = new Vector();
        RightMemory  = new Vector();
        ShootButton = new Vector();
        PrimeShootButton = new Vector();
    }
    
    public void collectString(double left, double right, boolean shootbutton, boolean primeshootbutton){
       LeftMemory.addElement(String.valueOf(left));
       RightMemory.addElement(String.valueOf(right));
       ShootButton.addElement(Boolean.valueOf(shootbutton));
       PrimeShootButton.addElement(Boolean.valueOf(primeshootbutton));
    }
    
    public void stopCollection() throws IOException{
        this.write(writeFile);
        addAuto(SmartDashboard.getString("Name Autonomous Procedure"));
        System.out.println("Saving on:" + writeFile);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    public void Reproduce(double leftTarget, double rightTarget, boolean shootbutton, boolean primeshootbutton){
        //DriveTrain.reproleft = leftTarget;
        //DriveTrain.reproright = rightTarget;
        
        SmartDashboard.putNumber("Autonomous Left Input", leftTarget);
        SmartDashboard.putNumber("Autonomous Right Input", rightTarget);
        SmartDashboard.putBoolean("Autonomous ShootButton", shootbutton);
        SmartDashboard.putBoolean("Autonomous PrimeShootButton", primeshootbutton);
    }   
    
    public Vector RequestLeft(){
        return LeftMemory;
    }
    
    public Vector RequestRight(){
        return RightMemory;
    }
    
    public Vector RequestShootButton(){
        return ShootButton;
    }
    
    public Vector RequestPrimeShootButton(){
        return PrimeShootButton;
    }
    
 ///////////////////////////////////////////////////////////////////////////////   
    
    /**
     * @param FILE_NAME
     * @throws IOException 
     */
    private void write(String FILE_NAME) throws IOException{

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        
        for(int i=0;i<LeftMemory.size();i++){
            String Left = (String) LeftMemory.elementAt(i);
            String Right = (String) RightMemory.elementAt(i);
            String Shoot;
            String PrimeShoot;
            
            if(ShootButton.elementAt(i).equals(Boolean.TRUE)){
                Shoot = "1";//True
            }else{
                Shoot = "0";//False
            }
            
            if(PrimeShootButton.elementAt(i).equals(Boolean.TRUE)){
                PrimeShoot = "1";//True
            }else{
                PrimeShoot = "0";//False
            }
            
            String point = Left + "," + Right + "[" + Shoot + "]" + PrimeShoot + ";";
            dos.writeUTF(point);
        }

        System.out.println(baos);
        dos.writeChars("\n");
        FileRW.getInstance().save(FILE_NAME, baos.toByteArray());
    }
    
    /**
     * @param path
     */
    public void read(String path){
        System.out.println("Beginning reading");
        //read from a file and set the vectors equal to the info
        String data;
        FileRW instance = FileRW.getInstance();
        data = instance.newRead(path);
        
        //Clear Vectors
        LeftMemory = new Vector();
        RightMemory = new Vector();
        ShootButton = new Vector();
        PrimeShootButton = new Vector();

        if(data != null){
            char[] dataArray = data.toCharArray();
            StringBuffer buffer = new StringBuffer("");
            String buff;
            for(int i = 0; i<dataArray.length;i++){
                char c = dataArray[i];
                if(c == ','){
                    //Has reached end of first value
                    buff = buffer.toString().substring(1);
                    System.out.println(buff);
                    LeftMemory.addElement(Double.valueOf(buff));
                    buffer.delete(0, buffer.length()-1);
                    
                }else if(c=='['){
                    //Has reached end of 2nd value
                    buff = buffer.toString().substring(1);
                    System.out.println(buff);
                    RightMemory.addElement(Double.valueOf(buff));
                    buffer.delete(0, buffer.length()-1);
                }else if(c==']'){
                    //Has reached end of 3rd value
                    buff = buffer.toString().substring(1);
                    System.out.println(buff);
                    if("1".equals(buff)){
                        ShootButton.addElement(Boolean.TRUE);
                    }else{
                        ShootButton.addElement(Boolean.FALSE);
                    }
                    buffer.delete(0, buffer.length()-1);
                }else if(c==';'){
                    //Has reached end of 4th value
                    buff = buffer.toString().substring(1);
                    System.out.println(buff);
                    if("1".equals(buff)){
                        PrimeShootButton.addElement(Boolean.TRUE);
                    }else{
                        PrimeShootButton.addElement(Boolean.FALSE);
                    }
                    buffer.delete(0, buffer.length()-1);
                }else if(c >= '0' && c <= '9'){
                    buffer.append(c);
                }else if(c=='.'){
                    buffer.append(c);
                } 
            }
        }
    }    
}