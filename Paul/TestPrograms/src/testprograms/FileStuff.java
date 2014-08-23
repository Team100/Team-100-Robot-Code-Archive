package testprograms;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author Me
 */
public class FileStuff {
    FileStuff(){
        try (PrintWriter writer = new PrintWriter("src/testprograms/stuff/qwertyuiopiuytrewq.txt", "UTF-8")) {
            writer.println("Hello,");
            writer.println("world");
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.out.println("File Writing Error");
        }
    }
}