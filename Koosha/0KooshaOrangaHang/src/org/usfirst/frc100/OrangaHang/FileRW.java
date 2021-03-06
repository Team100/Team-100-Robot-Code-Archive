/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang;

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
    
    private final Object FileLock = new Object();
    
    ////////////////////////////////////////////////////////////////////////////
    
    private FileRW(){
        synchronized (FileLock) {
            boolean created = false;
            new Thread() {
                

                public void run() {
                    System.out.println("FileRW Init Thread");
                }
            }.start();
            try {
                System.out.println("Beginning Wait");
                
                if(created){
                    FileLock.wait();
                }
                System.out.println("Completed Wait");
                created = false;
                
            } catch (InterruptedException ex) {
            }
        }
        
    }
    
    public String newRead(String path){
        System.out.println("FileRW/newRead");
        synchronized(FileLock){
            FileLock.notifyAll();
        }
        return read(path);
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

    ////////////////////////////////////////////////////////////////////////////
    
    //WRITE AS LITTLE AS POSSIBLE, CONSTANT WRITING(every tick) IS BAD FOR CRIO
    private void write(String path,byte[] data){
        if(path==null){path = DEFAULT_FILE_PATH;}
        
        synchronized (FileLock) {
            FileLock.notifyAll();
        }
        FileConnection File = null;
        try{
            
            File = (FileConnection) Connector.open(path, Connector.READ_WRITE);   
            
            if(File.exists()){
                File.delete();
            }
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
            
            public void closeStream(){
                try {
                    stream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            
            public char read() throws IOException, EndOfStreamException {
                int input = stream.read();
                if (input == -1) {
                    return " ".charAt(0);
                    //throw new EndOfStreamException();
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
            
        System.out.println("Searching on "+path);
        if(path==null){path = DEFAULT_FILE_PATH;System.out.println("Path is null");}
        
        synchronized (FileLock) {
            FileLock.notifyAll();
        }
        
        String result = null;
        
        FileConnection File;
        try{
            File = (FileConnection) Connector.open(path, Connector.READ);
            StringBuffer buffer = new StringBuffer();
           
            System.out.println("File Exists? " + File.exists());
            
            if(File.exists()){
                Reader reader = new Reader(File.openInputStream());
                while(true){
                    
                    char value = reader.readWithoutWhitespace();
                        
                    for (; value != '\n'; value = reader.read()) { 
                        if(value == ')' || value == ' '){
                            continue;
                        }
                        
                        buffer.append(value);
                        
                    }
                    result = buffer.toString();
                    reader.closeStream();
                    break;
                }   
            }
                
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (EndOfStreamException ex) {
            ex.printStackTrace();
            System.out.println("FINISHED READING");
        }
        
        return result;
        
    }  
}
