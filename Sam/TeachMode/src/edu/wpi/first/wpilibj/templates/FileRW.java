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
        if(instance == null){
            instance = new FileRW();
        }
        return instance;
    }
    
    public synchronized static void newRead(String path){
        instance.read(path);
    }
    
    public synchronized static void newWrite(String path, byte[] bytes){
        instance.write(path, bytes);
    }
    
    private final Object FileLock = new Object();
    
    private FileRW(){
        synchronized (FileLock) {
            new Thread() {

                public void run() {
                    read(DEFAULT_FILE_PATH);
                }
                public void stop(){
                    this.interrupt();
                }
            }.start();
            try {
                FileLock.wait();
                
            } catch (InterruptedException ex) {
            }
        }
        
    }
    
    public void save() {
        synchronized (FileLock) {
            new Thread() {

                public void run() {
                    write(DEFAULT_FILE_PATH, null);
                }
            }.start();
            try {
                FileLock.wait();
            } catch (InterruptedException ex) {
            }
        }
    }

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
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (File != null) {
                try {
                    File.close();
                } catch (IOException ex) {
            }
        }
               
    }
        
        
        
        
        
        
        
        
        
    }
    
        private void read(String path){
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
            File = (FileConnection) Connector.open(path, Connector.READ);
            
            if(File.exists()){
                //Begin Readin Now
            }
                
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
  
    
}
