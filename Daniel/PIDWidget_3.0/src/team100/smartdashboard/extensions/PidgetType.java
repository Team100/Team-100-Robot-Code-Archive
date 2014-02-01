/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team100.smartdashboard.extensions;

import edu.wpi.first.smartdashboard.types.NamedDataType;

/**
 *
 * @author Daniel
 */
public class PidgetType extends NamedDataType {
    
    public static final String LABEL = "Pidget";
    
    private PidgetType() {
        super(LABEL, PIDWidget_3.class);
    }
    
    public static NamedDataType get() {
        if (NamedDataType.get(LABEL) != null) {
            return NamedDataType.get(LABEL);
        } else {
            return new PidgetType();
        }
    }
}
