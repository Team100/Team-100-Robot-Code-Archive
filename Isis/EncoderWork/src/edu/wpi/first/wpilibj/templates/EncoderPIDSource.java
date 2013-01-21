package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Isis
 */
public class EncoderPIDSource implements PIDSource{
    
    private Encoder encoder_;
    
    public EncoderPIDSource(Encoder encoder){
        encoder_ = encoder;
    }
    
    public double pidGet() {
        return encoder_.getRate();
    }
    
}
