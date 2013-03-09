/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import org.usfirst.frc100.OrangaHang.FileRW;

/**
 *
 * @author Sam
 */
public class AutoMemory extends Subsystem implements SubsystemControl{
    
    
    Vector LeftMemory;
    Vector RightMemory;
    Vector ShootButton;
    Vector PrimeShootButton;
    
    static SendableChooser chooser;

    public static String AutoList;
    String writeFile;
    
    public AutoMemory(){
        initSendableChooser();
        
    }
    
    protected void initDefaultCommand() {
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    private void initSendableChooser(){
        chooser = new SendableChooser();
        chooser.addDefault("Auto1", "Auto1");
        chooser.addObject("Auto2", "Auto2");
        chooser.addObject("Auto3", "Auto3");
        chooser.addObject("Auto4", "Auto4");
        chooser.addObject("Auto5", "Auto5");
        
        
        /*
        int i = 0;        
        while(AutoList.indexOf(',', i) != -1){
            String sub = AutoList.substring(i, AutoList.indexOf(',', i));
            i = AutoList.indexOf(',', i)+1;
            chooser.addObject(sub, sub);
        }*/
        SmartDashboard.putData("Sendable Chooser", chooser);
        
    }
    
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Adds name to the list located in the preferences file. The it calls updateAuto
     * @see updateAuto
     * @param name
     */
    
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Gets the path from the smartdashboard and then resets the Vectors - Replace w/ sendable chooser
     */
    public void beginCollection(){
        writeFile = "file:///autonomous/" + chooser.getSelected().toString() + ".sam";
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
        System.out.println(chooser.getSelected().toString() +": Saving on:" + writeFile);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    
    
    public String RequestName(){
        return chooser.getSelected().toString();
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
            boolean negative = false;
            for(int i = 0; i<dataArray.length;i++){
                char c = dataArray[i];
                if(c == ','){
                    //Has reached end of first value
                    buff = buffer.toString().substring(1);
                    System.out.println(buff);
                    try{buff.charAt(0);
                    }catch(StringIndexOutOfBoundsException ex){
                        continue;
                    }
                    
                     
                    Double value = Double.valueOf(buff);
                    if(negative){
                        value = Double.valueOf(-value.doubleValue());
                    }
                    System.out.println(value);
                    LeftMemory.addElement(value);
                    buffer.delete(0, buffer.length()-1);
                    negative = false;
                }else if(c=='['){
                    //Has reached end of 2nd value
                    buff = buffer.toString().substring(1);
                    Double value = Double.valueOf(buff);
                    if(negative){
                        value = Double.valueOf(-value.doubleValue());
                    }
                    System.out.println(value);
                    RightMemory.addElement(value);
                    buffer.delete(0, buffer.length()-1);
                    negative = false;
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
                }else if (c=='-'){
                    negative = true;
                }
            }
        }
    }    

    public void disable() {
    }

    public void enable() {
    }

    public void writePreferences() {
    }
}