/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

/**
 *
 * @author Student
 */
public class ThreadCreator implements Runnable {
    double input;
    PIDBase m_PIDObject;
    
    ThreadCreator(double input, PIDBase m_PIDObject){
        this.input = input;
        this.m_PIDObject = m_PIDObject;
    }
    
    public void run(){
        ThreadCreator thread = new ThreadCreator(input, m_PIDObject);
        new Thread(thread).start();
    }
}//end ThreadCreator
