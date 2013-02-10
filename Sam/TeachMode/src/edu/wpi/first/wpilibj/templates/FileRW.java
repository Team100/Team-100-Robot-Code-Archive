/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.microedition.io.FileConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;

/**
 *
 * @author Sam
 */
public class FileRW {
    
    private static FileRW instance;
    
    private static final String DEFAULT_FILE_PATH = "file:///autonomous-default.txt";
    
    public synchronized static FileRW getInstance(){
        System.out.println("Got FileRW Instance");
        if(instance == null){
            instance = new FileRW();
        }
        
        return instance;
    }
    
    public synchronized static String newRead(String path){
        System.out.println("FileRW/newRead");
        FileRW inst = getInstance();
        return inst.read(path);
    }
    
    private final Object FileLock = new Object();
    
    private FileRW(){
        synchronized (FileLock) {
            new Thread() {

                public void run() {
                    System.out.println("FileRW Init Thread");
                    read(DEFAULT_FILE_PATH);
                }
            }.start();
            try {
                FileLock.wait();
                
            } catch (InterruptedException ex) {
            }
        }
        
    }
    
    
    
    public void save(final String path, final byte[] bytes) {
        synchronized (FileLock) {
            new Thread() {

                public void run() {
                    write(path, bytes);
                }
            }.start();
            try {
                FileLock.wait();
            } catch (InterruptedException ex) {
            }
        }
    }

    //WRITE AS LITTLE AS POSSIBLE, CONSTANT WRITING(every tick) IS BAD FOR CRIO
    private void write(String path,byte[] data){
        if(path==null){path = DEFAULT_FILE_PATH;}
        
        synchronized (FileLock) {
            FileLock.notifyAll();
        }
        FileConnection File = null;
        try{
            
            File = (FileConnection) Connector.open(path, Connector.WRITE);   
            
            File.create();
            OutputStream output = File.openOutputStream();

            if(data != null){
                output.write(data);
                System.out.println("WRITING");
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (File != null) {
                try {
                    File.close();
                    System.out.println("WRITING COMPLETED");
                } catch (IOException ex) {
            }
        }
               
    }
   
    }
    
        private String read(String path){
        class EndOfStreamException extends Exception {
        }

        class Reader {

            InputStream stream;

            Reader(InputStream stream) {
                this.stream = stream;
            }

            public char read() throws IOException, EndOfStreamException {
                int input = stream.read();
                if (input == -1) {
                    throw new EndOfStreamException();
                } else {
                    // Check for carriage returns
                    return input == '\r' ? '\n' : (char) input;
                }
            }

            char readWithoutWhitespace() throws IOException, EndOfStreamException {
                while (true) {
                    char value = read();
                    switch (value) {
                        case ' ':
                        case '\t':
                            continue;
                        default:
                            return value;
                    }
                }
            }
        }
            
        if(path==null){path = DEFAULT_FILE_PATH;}
        
        synchronized (FileLock) {
            FileLock.notifyAll();
        }
        
        FileConnection File;
        try{
            File = (FileConnection) Connector.open("file:///test.sam", Connector.READ);
            boolean shouldBreak = false;
            StringBuffer buffer = null;
           
            System.out.println("File Exists? " + File.exists());
            
            if(File.exists()){
                //Begin Readin Now
                Reader reader = new Reader(File.openInputStream());
                while(true){
                    
                    char value = reader.readWithoutWhitespace();
                    
                    if(value == ' '){
                        System.out.println("value empty");
                        continue;
                    }
                    
                    //value is zero find out why
                    for (; value != '\n'; value = reader.read()) {
                                    buffer.append(value);
                                }
                                buffer.append(';');
                    if (value == '"'){
                        try{
                            for (value = reader.read(); value != '"'; value = reader.read()) {
                                buffer.append(value);
                            }
                        }catch (EndOfStreamException ex) {
                            shouldBreak = true;
                        }
                    }
                    if(shouldBreak){break;}
                }
                
                String result = buffer.toString();
                //result is in format
                //String;String;String;String
                System.out.println("Sending Result_ FORMAT=String;String;String");
                return result;
                
            }
                
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (EndOfStreamException ex) {
            ex.printStackTrace();
            System.out.println("FINISHED READING");
        }
        System.out.println("RETURNING NULL");
        return null;
        
    }  
}
