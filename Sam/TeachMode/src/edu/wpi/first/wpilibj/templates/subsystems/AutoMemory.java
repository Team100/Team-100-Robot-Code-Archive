package edu.wpi.first.wpilibj.templates.subsystems;


import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.FileRW;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;


/**
 *
 * @author Sam
 */
public class AutoMemory extends Subsystem{
    
    Vector LeftMemory;
    Vector RightMemory;
    public static String AutoList;
    String writeFile;
    
    public AutoMemory(){
        updateAuto();
        SmartDashboard.putString("Autonomous List", AutoList);
    }
    
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

    
    /**
     * Gets the path from the smartdashboard and then resets the Vectors
     */
    public void beginCollection(){
        writeFile = "file:///autonomous/" + SmartDashboard.getString("Name Autonomous Procedure") + ".sam";
        System.out.println("AutoMemory,beginCollection" + writeFile);
        
        LeftMemory  = new Vector();
        RightMemory  = new Vector();
    }
    
    public void collectString(double left, double right){
       LeftMemory.addElement(String.valueOf(left));
       RightMemory.addElement(String.valueOf(right));
    }
    
    /**
     * @return LeftMemory
     */
    public Vector RequestLeft(){
        return LeftMemory;
    }
    
    /**
     * @return RightMemory
     */
    public Vector RequestRight(){
        return RightMemory;
    }
    
    public void Reproduce(double leftTarget, double rightTarget){
        DriveTrain.reproleft = leftTarget;
        DriveTrain.reproright = rightTarget;
        SmartDashboard.putNumber("Left Input", leftTarget);
        SmartDashboard.putNumber("Right Input", rightTarget);
    }
    
    protected void initDefaultCommand() {
    }
    
    /**
     * Writes the gathered info to a file and has addAuto update the preferences file.
     * @see addAuto()
     * @throws IOException
     */
    public void stopCollection() throws IOException{
        this.write(writeFile);
        addAuto(SmartDashboard.getString("Name Autonomous Procedure"));
        System.out.println("Saving on:" + writeFile);
    }
    /**
     * 
     * @param FILE_NAME
     * @throws IOException 
     */
    private void write(String FILE_NAME) throws IOException{
        System.out.println("Beginning writing");

        if(LeftMemory.size() != RightMemory.size()){System.out.println("VECTORS NOT EQUAL SIZE!!!!");}

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        
        for(int i=0;i<LeftMemory.size();i++){
            String Left = (String) LeftMemory.elementAt(i);
            String Right = (String) RightMemory.elementAt(i);
            
            String point = Left + "," + Right + ";";
            dos.writeUTF(point);
        }
        //baos.toByteArray();
        //FileRW.getInstance().save(FILE_NAME, bytes);
        
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
        //data = FileRW.newRead(path);//Read from path
        
        //Clear Vectors
        LeftMemory = new Vector();
        RightMemory = new Vector();
        
        System.out.println(data);

        if(data != null){
            char[] dataArray = data.toCharArray();
            StringBuffer buffer = new StringBuffer("");
            String buff;
            for(int i = 0; i<dataArray.length;i++){
                char c = dataArray[i];
                if(c == ','){
                    buff = buffer.toString().substring(1);
                    System.out.println(buff);
                    LeftMemory.addElement(Double.valueOf(buff));
                    buffer.delete(0, buffer.length()-1);
                }else if(c==';'){
                    buff = buffer.toString().substring(1);
                    RightMemory.addElement(Double.valueOf(buff));
                    buffer.delete(0, buffer.length()-1);
                }else if(c=='*'||c=='(' || c==')'|| c=="'".charAt(0) || c=='&' || c=='%'){
                }else{
                    buffer.append(c);
                }
                
            }
            
        }
    }    
}
